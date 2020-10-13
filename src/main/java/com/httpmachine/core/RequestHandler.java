package com.httpmachine.core;

public interface RequestHandler {
    void handleRequest(Request request, Response response);
}
