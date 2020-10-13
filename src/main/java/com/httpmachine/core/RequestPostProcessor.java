package com.httpmachine.core;

public interface RequestPostProcessor {
    void postProcess(Request request, Response response);
}
