package com.httpmachine.core;

import java.io.PrintWriter;

public class Response {
    private final PrintWriter writer;

    public Response(PrintWriter writer) {
        this.writer = writer;
    }

    public PrintWriter getWriter() {
        return writer;
    }
}
