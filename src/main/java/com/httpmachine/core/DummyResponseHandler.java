package com.httpmachine.core;

public class DummyResponseHandler implements RequestHandler {
    @Override
    public void handleRequest(Request request, Response response) {
        response.addHeader("Content-Type", "text/plain");
        response.addHeader("Connection", "close");
        response.getBodyWriter().println("Hello, world!");
    }
}
