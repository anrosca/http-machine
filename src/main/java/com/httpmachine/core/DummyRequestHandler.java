package com.httpmachine.core;

import java.io.PrintWriter;

public class DummyRequestHandler {

    public void handleRequest(Request request, Response response) {
        writeResponse(response.getWriter());
    }

    private void writeResponse(PrintWriter writer) {
        writer.println("HTTP/1.1 200 OK");
        writer.println("Content-Type: text/plain");
        writer.println("Connection: close");
        writer.println();
        writer.println("Hello, world!");
    }
}
