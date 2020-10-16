package com.httpmachine.core;

import java.io.PrintWriter;
import java.util.Map;

public class ResponseWriter {

    public void writeResponse(Request request, Response response) {
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
            writer.print(header.getKey());
            writer.print(": ");
            writer.print(header.getValue());
            writer.print("\n");
        }
        writer.print("\n");
    }

    private void writeResponseLine(PrintWriter writer, Response response) {
        HttpStatus statusCode = response.getStatusCode();
        writer.print(String.format("%s %d %s", response.getHttpVersion().toString(),
                statusCode.getStatusCode(), statusCode.getReasonPhrase()));
        writer.print("\n");
    }
}
