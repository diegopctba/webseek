package com.webseek.backend.vo;

import java.util.ArrayList;
import java.util.List;

public class SearchResult {

    private final String id;
    private String status;
    private final List<String> urls;

    public SearchResult(String id) {
        this.id = id;
        this.status = "active";
        this.urls = new ArrayList<>();
    }

    public void addUrl(String url) {
        this.urls.add(url);
    }

    public String getId() {
        return id;
    }

    public List<String> getUrls() {
        return urls;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
