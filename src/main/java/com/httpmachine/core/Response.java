package com.httpmachine.core;

import java.io.PrintWriter;
import java.io.StringWriter;

public class Response {
    private final HttpVersion httpVersion = HttpVersion.HTTP_1_1;
    private final HttpHeaders httpHeaders = HttpHeaders.builder().build();
    private final PrintWriter writer;
    private final StringWriter requestBody = new StringWriter();
    private final PrintWriter bodyWriter = new PrintWriter(requestBody);

    public Response(PrintWriter writer) {
        this.writer = writer;
    }

    public HttpVersion getHttpVersion() {
        return httpVersion;
    }

    public HttpHeaders getHttpHeaders() {
        return httpHeaders;
    }

    public PrintWriter getWriter() {
        return writer;
    }

    public PrintWriter getBodyWriter() {
        return bodyWriter;
    }

    public String getRequestBody() {
        return requestBody.toString();
    }

    public void addHeader(String headerName, String headerValue) {
        httpHeaders.addHeader(headerName, headerValue);
    }
}
