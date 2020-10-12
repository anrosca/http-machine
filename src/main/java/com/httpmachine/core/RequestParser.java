package com.httpmachine.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

public class RequestParser {
    private static final Logger log = LoggerFactory.getLogger(RequestParser.class);

    public void service(BufferedReader reader, PrintWriter writer) throws IOException {
        String requestLine = reader.readLine();
        HttpHeaders.HttpHeadersBuilder httpHeadersBuilder = HttpHeaders.builder(requestLine);
        String line;
        while (!((line = reader.readLine()).equals(""))) {
            addHeader(httpHeadersBuilder, line);
        }
        HttpHeaders httpHeaders = httpHeadersBuilder.build();
        log.info("Incoming request headers: {}", httpHeaders);
        writeResponse(writer);
    }

    private void addHeader(HttpHeaders.HttpHeadersBuilder httpHeadersBuilder, String line) {
        String[] parts = line.split(": ");
        String headerName = parts[0];
        String headerValue = parts[1];
        httpHeadersBuilder.withHeader(headerName, headerValue);
    }

    private void writeResponse(PrintWriter writer) {
        writer.println("HTTP/1.1 200 OK");
        writer.println("Content-Type: text/plain");
        writer.println("Connection: close");
        writer.println();
        writer.println("Hello, world!");
    }
}
