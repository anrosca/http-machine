package com.httpmachine.core;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class HttpHeadersTest {

    @Test
    public void newlyCreatedHttpHeaders_shouldBeEmpty() {
        HttpHeaders httpHeaders = HttpHeaders.builder().build();

        assertEquals(Map.of(), httpHeaders.getHeaders());
    }

    @Test
    public void shouldMemorizeAddedHeaders() {
        HttpHeaders httpHeaders = HttpHeaders.builder().build();

        httpHeaders.addHeader("Content-Type", "text/plain");

        assertEquals(Map.of("Content-Type", "text/plain"), httpHeaders.getHeaders());
    }

    @Test
    public void shouldMemorizeAddedHeaders_whenInstantiatingThroughBuilder() {
        HttpHeaders httpHeaders = HttpHeaders.builder()
                .withHeader("Content-Type", "text/plain")
                .build();

        assertEquals(Map.of("Content-Type", "text/plain"), httpHeaders.getHeaders());
    }
}
