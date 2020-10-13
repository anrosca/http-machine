package com.httpmachine.core;

import java.io.IOException;

public class InvalidHttpRequestException extends RuntimeException {
    public InvalidHttpRequestException() {
    }

    public InvalidHttpRequestException(String message) {
        super(message);
    }

    public InvalidHttpRequestException(String message, IOException cause) {
        super(message, cause);
    }
}
