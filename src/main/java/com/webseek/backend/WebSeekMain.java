package com.webseek.backend;

import static spark.Spark.*;

import com.webseek.backend.controller.WebSeekController;

public class WebSeekMain {

    private static final int PORT = 4567;

    public static void main(String[] args) {
        config();
    }

    private static void config() {
        WebSeekController webSeekController = new WebSeekController();

        port(PORT);

        post("/crawl", webSeekController::initSearch);

        get("/crawl/:id", webSeekController::findSearch);
    }



}
