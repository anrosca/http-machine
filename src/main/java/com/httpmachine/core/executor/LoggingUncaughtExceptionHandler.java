package com.httpmachine.core.executor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggingUncaughtExceptionHandler implements Thread.UncaughtExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(LoggingUncaughtExceptionHandler.class);

    @Override
    public void uncaughtException(Thread thread, Throwable e) {
        log.error("Uncaught exception in thread " + thread.getName(), e);
    }
}
