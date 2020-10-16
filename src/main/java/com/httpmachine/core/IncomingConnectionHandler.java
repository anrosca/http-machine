package com.httpmachine.core;

import com.httpmachine.core.config.ServerConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class IncomingConnectionHandler implements Runnable {
    private static final Logger log = LoggerFactory.getLogger(IncomingConnectionHandler.class);

    private final Socket socket;
    private final RequestExecutor requestExecutor;

    public IncomingConnectionHandler(Socket socket, ServerConfig serverConfig) {
        this.socket = socket;
        this.requestExecutor = new RequestExecutor(serverConfig);
    }

    @Override
    public void run() {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
            try (BufferedOutputStream responseStream = new BufferedOutputStream(socket.getOutputStream())) {
                requestExecutor.executeRequest(reader, responseStream);
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
            throw new RuntimeException(e);
        }
    }
}
