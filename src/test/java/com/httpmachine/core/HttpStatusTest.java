package com.httpmachine.core;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class HttpStatusTest {

    @Test
    public void shouldBeAbleToReturnStatusCodeAndReasonPhrase() {
        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;

        assertEquals(500, httpStatus.getStatusCode());
        assertEquals("Internal Server Error", httpStatus.getReasonPhrase());
    }

    @Test
    public void shouldBeAbleToGetHttpStatusInstanceFromStatusCode() {
        HttpStatus httpStatus = HttpStatus.fromStatusCode(400);

        assertEquals(HttpStatus.BAD_REQUEST, httpStatus);
    }
}
