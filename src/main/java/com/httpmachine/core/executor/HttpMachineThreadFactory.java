package com.httpmachine.core.executor;

import java.util.concurrent.ThreadFactory;

public class HttpMachineThreadFactory implements ThreadFactory {
    private final ThreadNameGenerator threadNameGenerator;

    public HttpMachineThreadFactory(ThreadNameGenerator threadNameGenerator) {
        this.threadNameGenerator = threadNameGenerator;
    }

    @Override
    public Thread newThread(Runnable r) {
        Thread thread = new Thread(r);
        thread.setName(threadNameGenerator.nextThreadName());
        return thread;
    }
}
