package com.httpmachine.core;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class RequestPostProcessorRegistry {
    private static final List<RequestPostProcessor> requestRequestPostProcessors = new CopyOnWriteArrayList<>();

    public void registerRequestPostProcessor(RequestPostProcessor postProcessor) {
        requestRequestPostProcessors.add(postProcessor);
    }

    public void deregisterRequestPostProcessor(RequestPostProcessor postProcessor) {
        requestRequestPostProcessors.remove(postProcessor);
    }

    public void forEach(Request request, Response response) {
        requestRequestPostProcessors.forEach(postProcessor -> postProcessor.postProcess(request, response));
    }
}
