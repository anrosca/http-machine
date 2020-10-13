package com.httpmachine.core;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class HttpVersionTest {

    @Test
    public void shouldReturnHttpVersionAccordingToSpec() {
        assertEquals("HTTP/1.1", HttpVersion.HTTP_1_1.toString());
    }

    @Test
    public void shouldBeAbleToParseHttpVersionFromString() {
        assertEquals(HttpVersion.HTTP_1_1, HttpVersion.from("HTTP/1.1"));
    }

    @Test
    public void shouldThrowIllegalArgumentException_whenParsingAnIllegalString() {
        assertThrows(IllegalArgumentException.class, () -> HttpVersion.from("What is dis??"));
    }
}
