package com.httpmachine.core.executor;

import com.httpmachine.core.config.ExecutorConfig;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ThreadNameGeneratorTest {

    @Test
    public void shouldGenerateThreadUniqueNames() {
        ExecutorConfig executorConfig = new ExecutorConfig("webMachineThreadPool", "webMachine-exec-", 4, 4);
        ThreadNameGenerator nameGenerator = new ThreadNameGenerator(executorConfig);

        assertEquals("webMachine-exec-0", nameGenerator.nextThreadName());
        assertEquals("webMachine-exec-1", nameGenerator.nextThreadName());
        assertEquals("webMachine-exec-2", nameGenerator.nextThreadName());
    }
}
