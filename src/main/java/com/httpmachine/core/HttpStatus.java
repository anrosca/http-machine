package com.httpmachine.core;

public enum HttpStatus {
    OK(200, "OK"),
    NOT_FOUND(404, "Not Found"),
    BAD_REQUEST(400, "Bad Request"),
    INTERNAL_SERVER_ERROR(500, "Internal Server Error");

    private final int statusCode;
    private final String reasonPhrase;

    HttpStatus(int statusCode, String reasonPhrase) {
        this.statusCode = statusCode;
        this.reasonPhrase = reasonPhrase;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getReasonPhrase() {
        return reasonPhrase;
    }

    public static HttpStatus fromStatusCode(int statusCode) {
        for (HttpStatus status : values()) {
            if (status.getStatusCode() == statusCode)
                return status;
        }
        throw new IllegalArgumentException(statusCode + " is an invalid HTTP status code.");
    }
}
