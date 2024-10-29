package com.webseek.backend.enums;

public enum Error {

    KEYWORD_NOT_VALID("Keyword must be between 4 and 32 characters"), SEARCH_NOT_FOUND("Search not found");

    private final String message;

    Error(String s) {
        this.message = s;
    }

    public String getMessage() {
        return this.message;
    }
}
