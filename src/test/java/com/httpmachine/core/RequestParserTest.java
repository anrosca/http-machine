package com.httpmachine.core;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class RequestParserTest {

    private final RequestParser requestParser = new RequestParser();

    @Test
    public void shouldThrowInvalidHttpRequestException_whenReceivingEmptyRequest() {
        BufferedReader reader = new BufferedReader(new StringReader(""));

        assertThrows(InvalidHttpRequestException.class, () -> requestParser.parse(reader));
    }

    @ParameterizedTest(name = "Bad HTTP request line {0}")
    @CsvSource({"GET_FUNKY", "GET FUNKY", "GET FUNKY LIKE ME"})
    public void shouldThrowInvalidHttpRequestException_whenReceivingInvalidRequestLine(String badRequestLine) {
        BufferedReader reader = new BufferedReader(new StringReader(badRequestLine));

        assertThrows(InvalidHttpRequestException.class, () -> requestParser.parse(reader));
    }

    @Test
    public void shouldParseRequestWithOneHeader() throws IOException {
        String http = """
                GET / HTTP/1.1
                Host: localhost:8080
                """;
        BufferedReader reader = new BufferedReader(new StringReader(http));

        Request request = requestParser.parse(reader);

        assertEquals(HttpMethod.GET, request.getHttpMethod());
        assertEquals("/", request.getContextPath());
        assertEquals(HttpVersion.HTTP_1_1, request.getHttpVersion());
        assertEquals(HttpHeaders.builder()
                        .withHeader("Host", "localhost:8080")
                        .build(),
                request.getHttpHeaders());
    }

    @Test
    public void shouldParsePostRequestWithPayload() throws IOException {
        String http = """
                POST /test HTTP/1.1
                Host: foo.example
                Content-Type: application/x-www-form-urlencoded
                Content-Length: 27

                field1=value1&field2=value2
                        """;

        BufferedReader reader = new BufferedReader(new StringReader(http));

        Request request = requestParser.parse(reader);

        assertEquals(HttpMethod.POST, request.getHttpMethod());
        assertEquals("/test", request.getContextPath());
        assertEquals(HttpVersion.HTTP_1_1, request.getHttpVersion());
        assertEquals(HttpHeaders.builder()
                        .withHeader("Host", "foo.example")
                        .withHeader("Content-Type", "application/x-www-form-urlencoded")
                        .withHeader("Content-Length", "27")
                        .build(),
                request.getHttpHeaders());
        assertEquals("field1=value1&field2=value2", TestUtils.readAsString(request.getPayload().get()));
    }

    @Test
    public void shouldThrowInvalidHttpRequestException_whenCantReadRequestBody() throws IOException {
        BufferedReader reader = mock(BufferedReader.class);
        when(reader.readLine())
                .thenReturn("POST /test HTTP/1.1")
                .thenReturn("Host: foo.example")
                .thenReturn("");
        when(reader.read(any(char[].class), anyInt(), anyInt())).thenThrow(IOException.class);

        assertThrows(InvalidHttpRequestException.class, () -> requestParser.parse(reader));
    }
}
