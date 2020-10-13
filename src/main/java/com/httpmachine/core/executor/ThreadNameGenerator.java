package com.httpmachine.core.executor;

import com.httpmachine.core.config.ExecutorConfig;

import java.util.concurrent.atomic.AtomicInteger;

public class ThreadNameGenerator {
    private static final AtomicInteger threadIdGenerator = new AtomicInteger();

    private final ExecutorConfig executorConfig;

    public ThreadNameGenerator(ExecutorConfig executorConfig) {
        this.executorConfig = executorConfig;
    }

    public String nextThreadName() {
        return String.format("%s%d", executorConfig.namePrefix(), threadIdGenerator.getAndIncrement());
    }
}
