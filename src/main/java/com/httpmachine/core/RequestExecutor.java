package com.httpmachine.core;

import com.httpmachine.core.config.ServerConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDateTime;

public class RequestExecutor {
    private static final Logger log = LoggerFactory.getLogger(RequestExecutor.class);

    private final RequestParser requestParser = new RequestParser();
    private final ServerConfig serverConfig;
    private final ResponseWriter requestHandler = new ResponseWriter();
    private final RequestPostProcessorRegistry requestPostProcessorRegistry = new RequestPostProcessorRegistry();

    public RequestExecutor(ServerConfig serverConfig) {
        this.serverConfig = serverConfig;
        requestPostProcessorRegistry.registerRequestPostProcessor(new EnrichResponseHeadersRequestPostProcessor(LocalDateTime::now));
    }

    public void executeRequest(BufferedReader reader, OutputStream responseStream) throws IOException {
        Request request = requestParser.parse(reader);
        Response response = new Response();
        handleRequest(request, response);
        requestHandler.writeResponse(response, responseStream);
    }

    private void handleRequest(Request request, Response response) {
        try {
            StaticResourceRequestHandler handler = new StaticResourceRequestHandler(serverConfig);
            handler.handleRequest(request, response);
        } catch (Throwable e) {
            log.error("Exception while handling the request", e);
            response.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        } finally {
            postProcessRequest(request, response);
        }
    }

    private void postProcessRequest(Request request, Response response) {
        try {
            requestPostProcessorRegistry.forEach(request, response);
        } catch (Exception e) {
            log.error("Error while postProcessing the request", e);
        }
    }
}
