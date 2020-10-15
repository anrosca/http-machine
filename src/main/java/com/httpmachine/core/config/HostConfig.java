package com.httpmachine.core.config;

public record HostConfig(String name, String appBase, boolean unpackWars, boolean autoDeploy) {
}
