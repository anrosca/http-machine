package com.httpmachine.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.Socket;

public class IncomingRequestHandler implements Runnable {
    private static final Logger log = LoggerFactory.getLogger(IncomingRequestHandler.class);

    private final Socket socket;
    private final RequestParser requestParser;
    private final ResponseWriter requestHandler = new ResponseWriter();

    public IncomingRequestHandler(Socket socket, RequestParser requestParser) {
        this.socket = socket;
        this.requestParser = requestParser;
    }

    @Override
    public void run() {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
            try (PrintWriter writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()))) {
                executeRequest(reader, writer);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            closeSocket();
            log.debug("Socket closed");
        }
    }

    private void executeRequest(BufferedReader reader, PrintWriter writer) throws IOException {
        Request request = requestParser.parse(reader);
        Response response = new Response(writer);
        handleRequest(request, response);
        requestHandler.writeResponse(request, response);
    }

    private void handleRequest(Request request, Response response) {
        try {
            DummyResponseHandler handler = new DummyResponseHandler();
            handler.handleRequest(request, response);
        } catch (Throwable e) {
            log.error("Exception while handling the request", e);
            response.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
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
