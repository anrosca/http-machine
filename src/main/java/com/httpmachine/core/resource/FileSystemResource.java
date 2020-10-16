package com.httpmachine.core.resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileSystemResource implements Resource {
    private static final Logger log = LoggerFactory.getLogger(FileSystemResource.class);

    private final Path resourcePath;

    public FileSystemResource(Path resourcePath) {
        this.resourcePath = resourcePath;
    }

    @Override
    public InputStream getInputStream() {
        try {
            return Files.newInputStream(resourcePath);
        } catch (IOException e) {
            log.error("I/O error while opening stream for resource " + resourcePath, e);
            return null;
        }
    }

    @Override
    public String toString() {
        return "FileSystemResource{" +
                "resourcePath=" + resourcePath +
                '}';
    }
}
