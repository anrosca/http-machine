package com.httpmachine.core;

import com.httpmachine.core.config.ServerConfig;
import com.httpmachine.core.resource.Resource;
import com.httpmachine.core.resource.StaticResourceResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;

public class StaticResourceRequestHandler implements RequestHandler {
    private static final Logger log = LoggerFactory.getLogger(StaticResourceRequestHandler.class);

    private final StaticResourceResolver resourceResolver;

    public StaticResourceRequestHandler(ServerConfig serverConfig) {
        this.resourceResolver = new StaticResourceResolver(serverConfig);
    }

    @Override
    public void handleRequest(Request request, Response response) {
        resourceResolver.resolve(request.getContextPath())
                .ifPresentOrElse(resource -> sendStaticResource(response, resource), () -> sendNotFoundStatus(response));
    }

    private void sendNotFoundStatus(Response response) {
        response.setHttpStatus(HttpStatus.NOT_FOUND);
    }

    private void sendStaticResource(Response response, Resource resource) {
        try (InputStream inputStream = resource.getInputStream()) {
            inputStream.transferTo(response.getOutputStream());
        } catch (IOException e) {
            log.error("I/O error while reading resource " + resource, e);
            throw new RuntimeException(e);
        }
        response.addHeader("Content-Type", "text/html");
    }
}
