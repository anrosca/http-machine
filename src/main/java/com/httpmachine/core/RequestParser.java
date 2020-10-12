package com.httpmachine.core;

import com.httpmachine.core.HttpHeaders.HttpHeadersBuilder;
import com.httpmachine.core.Request.RequestBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;

public class RequestParser {
    private static final Logger log = LoggerFactory.getLogger(RequestParser.class);

    public Request parse(BufferedReader reader) throws IOException {
        String requestLine = reader.readLine();
        RequestBuilder requestBuilder = Request.builder(requestLine);
        HttpHeaders httpHeaders = parseHeaders(reader);
        log.info("Incoming request headers: {}", httpHeaders);
        InputStream payload = readPayload(reader);
        return requestBuilder.httpHeaders(httpHeaders)
                .payload(payload)
                .build();
    }

    private HttpHeaders parseHeaders(BufferedReader reader) throws IOException {
        HttpHeadersBuilder httpHeadersBuilder = HttpHeaders.builder();
        String line;
        while (!((line = reader.readLine()).equals(""))) {
            addHeader(httpHeadersBuilder, line);
        }
        return httpHeadersBuilder.build();
    }

    private InputStream readPayload(BufferedReader reader) {
        return null;
    }

    private void addHeader(HttpHeadersBuilder httpHeadersBuilder, String line) {
        String[] parts = line.split(": ");
        String headerName = parts[0];
        String headerValue = parts[1];
        httpHeadersBuilder.withHeader(headerName, headerValue);
    }
}
