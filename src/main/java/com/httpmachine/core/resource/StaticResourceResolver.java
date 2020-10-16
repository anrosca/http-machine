package com.httpmachine.core.resource;

import com.httpmachine.core.config.ServerConfig;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

public class StaticResourceResolver {

    private final ServerConfig serverConfig;

    public StaticResourceResolver(ServerConfig serverConfig) {
        this.serverConfig = serverConfig;
    }

    public Optional<Resource> resolve(String contextPath) {
        String resource = serverConfig.getAppsDirectory() + contextPath;
        Path resourcePath = Paths.get(resource);
        if (Files.exists(resourcePath) && Files.isReadable(resourcePath)) {
            return Optional.of(new FileSystemResource(resourcePath));
        }
        return Optional.empty();
    }
}
