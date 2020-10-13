package com.httpmachine.core;

import com.httpmachine.core.HttpHeaders.HttpHeadersBuilder;
import com.httpmachine.core.Request.RequestBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class RequestParser {
    private static final Logger log = LoggerFactory.getLogger(RequestParser.class);

    public Request parse(BufferedReader reader) throws IOException {
        String requestLine = reader.readLine();
        if (requestLine == null)
            throw new InvalidHttpRequestException();
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
        while (!("".equals(line = reader.readLine())) && line != null) {
            addHeader(httpHeadersBuilder, line);
        }
        return httpHeadersBuilder.build();
    }

    private InputStream readPayload(BufferedReader reader) {
        try {
            StringWriter out = new StringWriter();
            char[] buffer = new char[4096];
            int charsRead;
            while ((charsRead = reader.read(buffer, 0, buffer.length)) > 0) {
                out.write(buffer, 0, charsRead);
            }
            return new ByteArrayInputStream(out.toString().getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            throw new InvalidHttpRequestException("Could not read HTTP request body", e);
        }
    }

    private void addHeader(HttpHeadersBuilder httpHeadersBuilder, String line) {
        String[] parts = line.split(": ");
        String headerName = parts[0];
        String headerValue = parts[1];
        httpHeadersBuilder.withHeader(headerName, headerValue);
    }
}
