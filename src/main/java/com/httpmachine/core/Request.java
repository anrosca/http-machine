package com.httpmachine.core;

import java.io.InputStream;

public class Request {
    private final HttpMethod httpMethod;
    private final String contextPath;
    private final HttpVersion httpVersion;
    private HttpHeaders httpHeaders;
    private InputStream payload;

    private Request(HttpMethod httpMethod, String contextPath, HttpVersion httpVersion) {
        this.httpMethod = httpMethod;
        this.contextPath = contextPath;
        this.httpVersion = httpVersion;
    }

    public static RequestBuilder builder(String requestLine) {
        String[] parts = requestLine.split(" ");
        HttpMethod httpMethod = HttpMethod.valueOf(parts[0]);
        String contextPath = parts[1];
        HttpVersion httpVersion = HttpVersion.from(parts[2]);
        return new RequestBuilder(httpMethod, contextPath, httpVersion);
    }

    public HttpHeaders getHttpHeaders() {
        return httpHeaders;
    }

    public HttpMethod getHttpMethod() {
        return httpMethod;
    }

    public String getContextPath() {
        return contextPath;
    }

    public HttpVersion getHttpVersion() {
        return httpVersion;
    }

    public InputStream getPayload() {
        return payload;
    }

    public static class RequestBuilder {
        private final Request request;

        public RequestBuilder(HttpMethod httpMethod, String contextPath, HttpVersion httpVersion) {
            request = new Request(httpMethod, contextPath, httpVersion);
        }

        public RequestBuilder httpHeaders(HttpHeaders httpHeaders) {
            request.httpHeaders = httpHeaders;
            return this;
        }

        public RequestBuilder payload(InputStream payload) {
            request.payload = payload;
            return this;
        }

        public Request build() {
            return request;
        }
    }
}
