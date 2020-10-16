package com.httpmachine.core;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Map;

public class ResponseWriter {

    public void writeResponse(Response response, OutputStream destinationStream) {
        PrintWriter writer = new PrintWriter(destinationStream);
        writeResponseLine(writer, response);
        writeHeaders(writer, response.getHttpHeaders());
        writeBody(response.getBody(), destinationStream);
    }

    private void writeBody(InputStream body, OutputStream destinationStream) {
        try {
            body.transferTo(destinationStream);
            destinationStream.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void writeHeaders(PrintWriter writer, HttpHeaders httpHeaders) {
        for (Map.Entry<String, String> header : httpHeaders.getHeaders().entrySet()) {
            writer.print(header.getKey());
            writer.print(": ");
            writer.print(header.getValue());
            writer.print("\n");
        }
        writer.print("\n");
        writer.flush();
    }

    private void writeResponseLine(PrintWriter writer, Response response) {
        HttpStatus statusCode = response.getStatusCode();
        writer.print(String.format("%s %d %s", response.getHttpVersion().toString(),
                statusCode.getStatusCode(), statusCode.getReasonPhrase()));
        writer.print("\n");
        writer.flush();
    }
}
