package com.httpmachine.core.executor;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class HttpMachineThreadFactoryTest {

    @Test
    public void shouldCreateThreadsWithNamesFromThreadNameGenerator() {
        ThreadNameGenerator threadNameGenerator = mock(ThreadNameGenerator.class);
        when(threadNameGenerator.nextThreadName()).thenReturn("webMachine-exec-0");
        HttpMachineThreadFactory threadFactory = new HttpMachineThreadFactory(threadNameGenerator);

        Thread createdThread = threadFactory.newThread(() -> {
        });

        assertEquals("webMachine-exec-0", createdThread.getName());
        verify(threadNameGenerator).nextThreadName();
    }
}
