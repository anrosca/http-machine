package com.httpmachine.core.config;

public class ServerConfig {
    private int serverPort;
    private ExecutorConfig executorConfig;
    private HostConfig hostConfig;

    private ServerConfig() {
    }

    public int getServerPort() {
        return serverPort;
    }

    public ExecutorConfig getExecutorConfig() {
        return executorConfig;
    }

    public HostConfig getHostConfig() {
        return hostConfig;
    }

    public static ServerConfigBuilder builder() {
        return new ServerConfigBuilder();
    }

    public static class ServerConfigBuilder {
        private final ServerConfig serverConfig = new ServerConfig();

        public ServerConfigBuilder serverPort(int serverPort) {
            serverConfig.serverPort = serverPort;
            return this;
        }

        public ServerConfigBuilder executorConfig(ExecutorConfig executorConfig) {
            serverConfig.executorConfig = executorConfig;
            return this;
        }

        public ServerConfigBuilder hostConfig(HostConfig hostConfig) {
            serverConfig.hostConfig = hostConfig;
            return this;
        }

        public ServerConfig build() {
            return serverConfig;
        }
    }
}
