package com.webseek.backend.controller;

import com.webseek.backend.enums.Error;
import com.webseek.backend.vo.SearchResult;
import org.eclipse.jetty.http.HttpStatus;
import spark.Request;
import spark.Response;

import java.util.Map;
import java.util.regex.Pattern;

public class WebSeekController extends MainController implements IWebSeek {

    private static final String APPLICATION_JSON = "application/json";

    public WebSeekController() {
        super("href=\"(.*?)\"", Pattern.CASE_INSENSITIVE);
    }

    public String initSearch(Request req, Response res) {
        final String keyword = gson.fromJson(req.body(), Map.class).get("keyword").toString();
        if (isNotValidKeyword(keyword)) {
            res.status(HttpStatus.BAD_REQUEST_400);
            return gson.toJson(Map.of("error", Error.KEYWORD_NOT_VALID.getMessage()));
        }
        SearchResult searchResult = new SearchResult(generateId());
        searches.put(searchResult.getId(), searchResult);
        new Thread(() -> performSearch(searchResult, keyword)).start();
        res.type(APPLICATION_JSON);
        return gson.toJson(Map.of("id", searchResult.getId()));
    }

    public String findSearch(Request req, Response res) {
        String id = req.params(":id");
        SearchResult searchResult = searches.get(id);
        if (searchResult == null) {
            res.status(HttpStatus.NO_CONTENT_204);
            return gson.toJson(Map.of("error", Error.SEARCH_NOT_FOUND.getMessage()));
        }
        res.type(APPLICATION_JSON);
        return gson.toJson(searchResult);
    }

    private static boolean isNotValidKeyword(String keyword) {
        return keyword.length() < 4 || keyword.length() > 32;
    }

}
