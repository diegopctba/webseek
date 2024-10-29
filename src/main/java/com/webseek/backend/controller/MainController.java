package com.webseek.backend.controller;

import com.webseek.backend.vo.SearchResult;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class MainController {

    private final Pattern patternCompile;
    private static final String BASE_URL = System.getenv("BASE_URL");
    protected static final Gson gson = new Gson();
    protected static Map<String, SearchResult> searches;

    MainController(String regex, int patternType) {
        patternCompile = Pattern.compile(regex, patternType);
        if (searches == null) {
            searches = new ConcurrentHashMap<>();
        }
    }

    protected static String generateId() {
        return UUID.randomUUID().toString().replaceAll("-", "").substring(0, 8);
    }

    protected void performSearch(SearchResult searchResult, final String keyword) {
        Queue<String> queue = new LinkedList<>();
        Set<String> visited = new HashSet<>();
        queue.offer(BASE_URL);

        while (!queue.isEmpty()) {
            String url = queue.poll();
            if (!visited.add(url)) continue;

            try {
                StringBuilder content = getContent(url);

                findKeyword(searchResult, keyword, content, url);

                findUrl(content, queue);
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }
        }
        searchResult.setStatus("done");
    }

    private void findUrl(StringBuilder content, Queue<String> queue) {
        Matcher matcher = patternCompile.matcher(content);
        while (matcher.find()) {
            String foundUrl = matcher.group(1);
            if (!foundUrl.startsWith("http")) {
                foundUrl = BASE_URL + foundUrl;
            }
            if (foundUrl.startsWith(BASE_URL)) {
                queue.offer(foundUrl);
            }
        }
    }

    private void findKeyword(SearchResult searchResult, String keyword, StringBuilder content, String url) {
        Matcher keywordMatcher = Pattern.compile(keyword, Pattern.CASE_INSENSITIVE).matcher(content);
        if (keywordMatcher.find()) {
            searchResult.addUrl(url);
        }
    }

    private StringBuilder getContent(String url) throws IOException {
        URLConnection connection = new URL(url).openConnection();
        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuilder content = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        in.close();
        return content;
    }
}
