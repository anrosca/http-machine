package com.httpmachine.core;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class HttpHeaders {
    private final Map<String, String> headers = new HashMap<>();

    private HttpHeaders() {
    }

    public Map<String, String> getHeaders() {
        return Collections.unmodifiableMap(headers);
    }

    private void addHeader(String headerName, String value) {
        headers.put(headerName, value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        HttpHeaders that = (HttpHeaders) o;

        return headers.equals(that.headers);
    }

    @Override
    public int hashCode() {
        return headers.hashCode();
    }

    @Override
    public String toString() {
        return "HttpHeaders{" +
                "headers=" + headers +
                '}';
    }

    public static HttpHeadersBuilder builder() {
        return new HttpHeadersBuilder();
    }

    public static class HttpHeadersBuilder {
        private final HttpHeaders httpHeaders = new HttpHeaders();

        public HttpHeadersBuilder withHeader(String headerName, String value) {
            httpHeaders.addHeader(headerName, value);
            return this;
        }

        public HttpHeaders build() {
            return httpHeaders;
        }
    }
}
