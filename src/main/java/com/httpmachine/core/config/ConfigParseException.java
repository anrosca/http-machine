package com.httpmachine.core.config;

public class ConfigParseException extends RuntimeException {
    public ConfigParseException(Exception cause) {
        super(cause);
    }

    public ConfigParseException(String message) {
        super(message);
    }
}
