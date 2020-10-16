package com.httpmachine.core;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static com.httpmachine.core.EnrichResponseHeadersRequestPostProcessor.SERVER_NAME_HEADER_VALUE;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class EnrichResponseHeadersRequestPostProcessorTest {
    private static final LocalDateTime EXPECTED_SERVER_DATE = LocalDateTime.parse("2020-10-16T11:24:58.272676800");

    private final Request request = Request.builder("GET / HTTP/1.1").build();
    private final Response response = new Response();
    private EnrichResponseHeadersRequestPostProcessor requestPostProcessor;

    @BeforeEach
    public void setUp() {
        requestPostProcessor = new EnrichResponseHeadersRequestPostProcessor(() -> EXPECTED_SERVER_DATE);
    }

    @Test
    public void shouldWriteStaticHeaders() {
        requestPostProcessor.postProcess(request, response);

        HttpHeaders expectedHeaders = HttpHeaders.builder()
                .withHeader("Date", EXPECTED_SERVER_DATE.toString())
                .withHeader("Server", SERVER_NAME_HEADER_VALUE)
                .withHeader("Connection", "close")
                .build();
        assertEquals(expectedHeaders, response.getHttpHeaders());
    }
}
