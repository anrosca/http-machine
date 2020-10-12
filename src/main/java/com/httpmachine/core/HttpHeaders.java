package com.httpmachine.core;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class HttpHeaders {
    private final Map<String, String> headers = new HashMap<>();
    private final HttpMethod httpMethod;
    private final HttpVersion httpVersion;
    private final String contextPath;

    private HttpHeaders(HttpMethod httpMethod, String contextPath, HttpVersion httpVersion) {
        this.httpMethod = httpMethod;
        this.contextPath = contextPath;
        this.httpVersion = httpVersion;
    }

    public Map<String, String> getHeaders() {
        return Collections.unmodifiableMap(headers);
    }

    private void addHeader(String headerName, String value) {
        headers.put(headerName, value);
    }

    @Override
    public String toString() {
        return "HttpHeaders{" +
                "headers=" + headers +
                ", httpMethod=" + httpMethod +
                ", httpVersion=" + httpVersion +
                ", contextPath='" + contextPath + '\'' +
                '}';
    }

    public static HttpHeadersBuilder builder(String requestLine) {
        String[] parts = requestLine.split(" ");
        HttpMethod httpMethod = HttpMethod.valueOf(parts[0]);
        String contextPath = parts[1];
        HttpVersion httpVersion = HttpVersion.from(parts[2]);
        return new HttpHeadersBuilder(httpMethod, contextPath, httpVersion);
    }

    public static class HttpHeadersBuilder {
        private HttpHeaders httpHeaders;

        public HttpHeadersBuilder(HttpMethod httpMethod, String contextPath, HttpVersion httpVersion) {
            this.httpHeaders = new HttpHeaders(httpMethod, contextPath, httpVersion);
        }

        public HttpHeadersBuilder withHeader(String headerName, String value) {
            httpHeaders.addHeader(headerName, value);
            return this;
        }

        public HttpHeaders build() {
            return httpHeaders;
        }
    }
}
