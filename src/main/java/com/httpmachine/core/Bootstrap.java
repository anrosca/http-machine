package com.httpmachine.core;

import com.httpmachine.core.config.ServerConfig;
import com.httpmachine.core.config.ServerConfigParser;

public class Bootstrap {
    public static void main(String[] args) {
        ServerConfigParser serverConfigParser = new ServerConfigParser();
        ServerConfig serverConfig = serverConfigParser.parse();
        HttpMachine machine = new HttpMachine(serverConfig);
        machine.start();
    }
}
