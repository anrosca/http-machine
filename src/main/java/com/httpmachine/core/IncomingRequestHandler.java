package com.httpmachine.core;

import com.httpmachine.core.config.ServerConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.Socket;
import java.time.LocalDateTime;

public class IncomingRequestHandler implements Runnable {
    private static final Logger log = LoggerFactory.getLogger(IncomingRequestHandler.class);

    private final Socket socket;
    private final RequestParser requestParser;
    private final ServerConfig serverConfig;
    private final ResponseWriter requestHandler = new ResponseWriter();
    private final RequestPostProcessorRegistry requestPostProcessorRegistry = new RequestPostProcessorRegistry();

    public IncomingRequestHandler(Socket socket, RequestParser requestParser, ServerConfig serverConfig) {
        this.socket = socket;
        this.requestParser = requestParser;
        this.serverConfig = serverConfig;
        requestPostProcessorRegistry.registerRequestPostProcessor(new EnrichResponseHeadersRequestPostProcessor(LocalDateTime::now));
    }

    @Override
    public void run() {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
            try (BufferedOutputStream responseStream = new BufferedOutputStream(socket.getOutputStream())) {
                executeRequest(reader, responseStream);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            closeSocket();
            log.debug("Socket closed");
        }
    }

    private void executeRequest(BufferedReader reader, OutputStream responseStream) throws IOException {
        Request request = requestParser.parse(reader);
        Response response = new Response();
        handleRequest(request, response);
        requestHandler.writeResponse(response, responseStream);
    }

    private void handleRequest(Request request, Response response) {
        try {
            DummyResponseHandler handler = new DummyResponseHandler(serverConfig);
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

    private void closeSocket() {
        try {
            socket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
