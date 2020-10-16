package com.httpmachine.core;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.PrintWriter;
import java.io.StringWriter;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ResponseWriterTest {

    private final ResponseWriter responseWriter = new ResponseWriter();
    private final StringWriter targetWriter = new StringWriter();
    private final Request request = Request.builder("GET /index.html HTTP/1.1").build();
    private Response response;

    @BeforeEach
    public void setUp() {
        response = new Response(new PrintWriter(targetWriter));
        response.addHeader("Connection", "close");
        response.addHeader("Server", "HttpMachine v0.0.0-SNAPSHOT");
    }

    @Test
    public void shouldBeAbleToWriteResponseWithBody() {
        response.getBodyWriter().print("Hello, world!\n");

        responseWriter.writeResponse(request, response);

        String expectedHttpResponse = """
                HTTP/1.1 200 OK
                Connection: close
                Server: HttpMachine v0.0.0-SNAPSHOT
                                
                Hello, world!
                """;
        assertEquals(expectedHttpResponse, targetWriter.toString());
    }

    @Test
    public void shouldBeAbleToWriteResponseWithoutBody() {
        responseWriter.writeResponse(request, response);

        String expectedHttpResponse = """
                HTTP/1.1 200 OK
                Connection: close
                Server: HttpMachine v0.0.0-SNAPSHOT
                                
                """;
        assertEquals(expectedHttpResponse, targetWriter.toString());
    }

    @Test
    public void shouldBeAbleToWriteResponseWithoutBodyAndHeaders() {
        responseWriter.writeResponse(request, new Response(new PrintWriter(targetWriter)));

        String expectedHttpResponse = """
                HTTP/1.1 200 OK
                                
                """;
        assertEquals(expectedHttpResponse, targetWriter.toString());
    }
}
