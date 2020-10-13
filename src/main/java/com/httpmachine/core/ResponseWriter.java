package com.httpmachine.core;

import java.io.PrintWriter;
import java.util.Map;

public class ResponseWriter {

    public void handleRequest(Request request, Response response) {
        PrintWriter writer = response.getWriter();
        writeResponseLine(writer, response);
        writeHeaders(writer, response.getHttpHeaders());
        writeBody(writer, response.getRequestBody());
    }

    private void writeBody(PrintWriter writer, String requestBody) {
       writer.write(requestBody);
    }

    private void writeHeaders(PrintWriter writer, HttpHeaders httpHeaders) {
        for (Map.Entry<String, String> header : httpHeaders.getHeaders().entrySet()) {
            writer.println(header.getKey() + ": " + header.getValue());
        }
        writer.println();
    }

    private void writeResponseLine(PrintWriter writer, Response response) {
        writer.println(String.format("%s 200 OK", response.getHttpVersion().toString()));
    }
}
