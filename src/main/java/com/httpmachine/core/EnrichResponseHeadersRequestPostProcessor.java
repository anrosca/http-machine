package com.httpmachine.core;

import java.time.LocalDateTime;

public class EnrichResponseHeadersRequestPostProcessor implements RequestPostProcessor {
    @Override
    public void postProcess(Request request, Response response) {
        response.addHeader("Date", LocalDateTime.now().toString());
        response.addHeader("Server", "HttpMachine v0.0.0-SNAPSHOT");
    }
}
