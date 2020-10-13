package com.httpmachine.core.config;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;

public class ServerConfigParserTest {
    private static final String VALID_CONFIG = """
            <?xml version="1.0" encoding="UTF-8" ?>
            <Server port="8080">
                <Host name="localhost" appBase="webapps" unpackWars="true" autoDeploy="true" />
                <Executor name="webMachineThreadPool" namePrefix="webMachine-exec-"
                          maxThreads="10" minSpareThreads="4"/>
            </Server>
            """;

    @Test
    public void testParse() {
        ServerConfigParser configParser = new ServerConfigParser();

        ServerConfig serverConfig = configParser.parse(new ByteArrayInputStream(VALID_CONFIG.getBytes(StandardCharsets.UTF_8)));

        assertEquals(8080, serverConfig.getServerPort());
        ExecutorConfig expectedExecutorConfig =
                new ExecutorConfig("webMachineThreadPool", "webMachine-exec-", 10, 4);
        assertEquals(expectedExecutorConfig, serverConfig.getExecutorConfig());
    }
}
