package com.httpmachine.core;

import java.io.*;

public class Response {
    private final HttpVersion httpVersion = HttpVersion.HTTP_1_1;
    private final HttpHeaders httpHeaders = HttpHeaders.builder().build();
    private final ByteArrayOutputStream responsePayloadStream = new ByteArrayOutputStream();
    private final PrintWriter bodyWriter = new PrintWriter(responsePayloadStream);
    private HttpStatus httpStatus = HttpStatus.OK;

    public Response() {
    }

    public HttpVersion getHttpVersion() {
        return httpVersion;
    }

    public HttpHeaders getHttpHeaders() {
        return httpHeaders;
    }

    public PrintWriter getWriter() {
        return bodyWriter;
    }

    public OutputStream getOutputStream() {
        return responsePayloadStream;
    }

    public void addHeader(String headerName, String headerValue) {
        httpHeaders.addHeader(headerName, headerValue);
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    public HttpStatus getStatusCode() {
        return httpStatus;
    }

    public InputStream getBody() {
        return new ByteArrayInputStream(responsePayloadStream.toByteArray());
    }
}
