package com.httpmachine.core;

public enum HttpVersion {
    HTTP_1_1("HTTP/1.1");

    private final String alias;

    HttpVersion(String alias) {
        this.alias = alias;
    }

    public static HttpVersion from(String alias) {
        for (HttpVersion version : values()) {
            if (version.alias.equals(alias))
                return version;
        }
        throw new IllegalArgumentException(alias + " is not a valid HttpVersion alias.");
    }
}
