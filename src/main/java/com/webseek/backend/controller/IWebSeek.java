package com.webseek.backend.controller;

import spark.Request;
import spark.Response;

public interface IWebSeek {
    String initSearch(Request req, Response res);
    String findSearch(Request req, Response res);
}
