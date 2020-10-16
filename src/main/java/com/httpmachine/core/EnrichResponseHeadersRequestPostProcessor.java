package com.httpmachine.core;

import java.time.LocalDateTime;
import java.util.function.Supplier;

public class EnrichResponseHeadersRequestPostProcessor implements RequestPostProcessor {

    public static final String SERVER_NAME_HEADER_VALUE = "HttpMachine v0.0.0-SNAPSHOT";

    private final Supplier<LocalDateTime> dateTimeSupplier;

    public EnrichResponseHeadersRequestPostProcessor(Supplier<LocalDateTime> dateTimeSupplier) {
        this.dateTimeSupplier = dateTimeSupplier;
    }

    @Override
    public void postProcess(Request request, Response response) {
        response.addHeader("Date", dateTimeSupplier.get().toString());
        response.addHeader("Server", SERVER_NAME_HEADER_VALUE);
    }
}
