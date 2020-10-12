package com.httpmachine.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class HttpMachine {
    private static final Logger log = LoggerFactory.getLogger(HttpMachine.class);

    private final ExecutorService executorService = Executors.newFixedThreadPool(10);
    private volatile boolean stopRequested = false;

    public void start() {
        log.debug("Running HttpMachine");
        try {
            ServerSocket serverSocket = new ServerSocket(8080);
            while (!stopRequested) {
                Socket socket = serverSocket.accept();
                executorService.submit(new IncomingRequestHandler(socket, new RequestParser()));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            cleanup();
        }
    }

    public void stop() {
        log.debug("Stopping the HttpMachine");
        stopRequested = true;
    }

    private void cleanup() {
        try {
            executorService.shutdown();
            if (!executorService.awaitTermination(1L, TimeUnit.MINUTES)) {
                executorService.shutdownNow();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public static void main(String[] args) {
        HttpMachine machine = new HttpMachine();
        machine.start();
    }
}
