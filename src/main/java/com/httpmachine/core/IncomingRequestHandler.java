package com.httpmachine.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.Socket;

public class IncomingRequestHandler implements Runnable {
    private static final Logger log = LoggerFactory.getLogger(IncomingRequestHandler.class);

    private final Socket socket;
    private final RequestParser requestParser;

    public IncomingRequestHandler(Socket socket, RequestParser requestParser) {
        this.socket = socket;
        this.requestParser = requestParser;
    }

    @Override
    public void run() {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
            try (PrintWriter writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()))) {
                requestParser.service(reader, writer);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            closeSocket();
            log.debug("Socket closed");
        }
    }

    private void closeSocket() {
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
