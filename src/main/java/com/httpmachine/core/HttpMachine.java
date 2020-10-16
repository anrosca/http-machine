package com.httpmachine.core;

import com.httpmachine.core.config.ServerConfig;
import com.httpmachine.core.executor.HttpMachineThreadFactory;
import com.httpmachine.core.executor.ThreadNameGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class HttpMachine implements Runnable {
    private static final Logger log = LoggerFactory.getLogger(HttpMachine.class);
    private static final ExecutorService requestListenerService = Executors.newSingleThreadExecutor();

    private final ExecutorService executorService;
    private final ServerConfig serverConfig;
    private volatile boolean stopRequested = false;
    private volatile ServerSocket serverSocket;

    public HttpMachine(ServerConfig serverConfig) {
        this.serverConfig = serverConfig;
        HttpMachineThreadFactory threadFactory = new HttpMachineThreadFactory(new ThreadNameGenerator(serverConfig.getExecutorConfig()));
        this.executorService = Executors.newFixedThreadPool(serverConfig.getExecutorConfig().maxThreads(), threadFactory);
    }

    public void start() {
        log.debug("Running HttpMachine");
        requestListenerService.submit(this);
    }

    public void stop() {
        log.debug("Stopping the HttpMachine");
        stopRequested = true;
        requestListenerService.shutdown();
        closeServerSocket();
    }

    private void closeServerSocket() {
        try {
            serverSocket.close();
            log.debug("Server socket closed");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void cleanup() {
        try {
            executorService.shutdown();
            if (!executorService.awaitTermination(1L, TimeUnit.SECONDS)) {
                executorService.shutdownNow();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    @Override
    public void run() {
        try {
            serverSocket = new ServerSocket(serverConfig.getServerPort());
            while (!stopRequested) {
                Socket socket = serverSocket.accept();
                executorService.submit(new IncomingConnectionHandler(socket, serverConfig));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            cleanup();
        }
    }
}
