package com.httpmachine.core;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ResponseWriterTest {

    private final ResponseWriter responseWriter = new ResponseWriter();
    private final ByteArrayOutputStream targetStream = new ByteArrayOutputStream();
    private Response response;

    @BeforeEach
    public void setUp() {
        response = new Response();
        response.addHeader("Connection", "close");
        response.addHeader("Server", "HttpMachine v0.0.0-SNAPSHOT");
    }

    @Test
    public void shouldBeAbleToWriteResponseWithBody() {
        PrintWriter writer = response.getWriter();
        writer.print("Hello, world!\n");
        writer.flush();

        responseWriter.writeResponse(response, targetStream);

        String expectedHttpResponse = """
                HTTP/1.1 200 OK
                Connection: close
                Server: HttpMachine v0.0.0-SNAPSHOT
                                
                Hello, world!
                """;
        assertEquals(expectedHttpResponse, getWrittenPayloadAsString());
    }

    @Test
    public void shouldBeAbleToWriteResponseWithoutBody() {
        responseWriter.writeResponse(response, targetStream);

        String expectedHttpResponse = """
                HTTP/1.1 200 OK
                Connection: close
                Server: HttpMachine v0.0.0-SNAPSHOT
                                
                """;
        assertEquals(expectedHttpResponse, getWrittenPayloadAsString());
    }

    @Test
    public void shouldBeAbleToWriteResponseWithoutBodyAndHeaders() {
        responseWriter.writeResponse(new Response(), targetStream);

        String expectedHttpResponse = """
                HTTP/1.1 200 OK
                                
                """;
        assertEquals(expectedHttpResponse, getWrittenPayloadAsString());
    }

    private String getWrittenPayloadAsString() {
        return targetStream.toString(StandardCharsets.UTF_8);
    }
}
