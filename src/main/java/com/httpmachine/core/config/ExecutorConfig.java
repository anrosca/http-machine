package com.httpmachine.core.config;

public record ExecutorConfig(String name, String namePrefix, int maxThreads, int minSpareThreads) {
}
