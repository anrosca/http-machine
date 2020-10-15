package com.httpmachine.core.config;

import com.httpmachine.core.config.ServerConfig.ServerConfigBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;

public class ServerConfigParser {
    private static final Logger log = LoggerFactory.getLogger(ServerConfigParser.class);
    private static final String SERVER_CONFIGURATION_FILE = "conf/server.xml";

    public ServerConfig parse() {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(SERVER_CONFIGURATION_FILE);
        return doParse(inputStream);
    }

    public ServerConfig parse(InputStream inputStream) {
        return doParse(inputStream);
    }

    private ServerConfig doParse(InputStream inputStream) {
        try {
            return tryParseServerConfig(inputStream);
        } catch (ParserConfigurationException | SAXException | IOException e) {
            log.error("Error while parsing the {} configuration file", SERVER_CONFIGURATION_FILE, e);
            throw new ConfigParseException(e);
        }
    }

    private ServerConfig tryParseServerConfig(InputStream inputStream) throws ParserConfigurationException, SAXException, IOException {
        DocumentBuilder documentBuilder = DocumentBuilderFactory.newDefaultInstance().newDocumentBuilder();
        Document document = documentBuilder.parse(inputStream);
        Element rootElement = document.getDocumentElement();
        ServerConfigBuilder serverConfigBuilder = ServerConfig.builder();
        String port = rootElement.getAttribute("port");
        return serverConfigBuilder.serverPort(Integer.parseInt(port))
                .executorConfig(parseExecutorConfig(rootElement))
                .hostConfig(parseHostConfig(rootElement))
                .build();
    }

    private HostConfig parseHostConfig(Element serverElement) {
        NodeList hostNodes = serverElement.getElementsByTagName("Host");
        if (hostNodes.getLength() > 0) {
            Node node = hostNodes.item(0);
            NamedNodeMap attributes = node.getAttributes();
            String name = attributes.getNamedItem("name").getTextContent();
            String appBase = attributes.getNamedItem("appBase").getTextContent();
            boolean unpackWars = Boolean.parseBoolean(attributes.getNamedItem("unpackWars").getTextContent());
            boolean autoDeploy = Boolean.parseBoolean(attributes.getNamedItem("autoDeploy").getTextContent());
            return new HostConfig(name, appBase, unpackWars, autoDeploy);
        }
        throw new ConfigParseException("/Server/Host element is not present in " + SERVER_CONFIGURATION_FILE);
    }

    private ExecutorConfig parseExecutorConfig(Element serverElement) {
        NodeList executorNodes = serverElement.getElementsByTagName("Executor");
        if (executorNodes.getLength() > 0) {
            Node node = executorNodes.item(0);
            NamedNodeMap attributes = node.getAttributes();
            String name = attributes.getNamedItem("name").getTextContent();
            String namePrefix = attributes.getNamedItem("namePrefix").getTextContent();
            int maxThreads = Integer.parseInt(attributes.getNamedItem("maxThreads").getTextContent());
            int minSpareThreads = Integer.parseInt(attributes.getNamedItem("minSpareThreads").getTextContent());
            return new ExecutorConfig(name, namePrefix, maxThreads, minSpareThreads);
        }
        throw new ConfigParseException("/Server/Executor element is not present in " + SERVER_CONFIGURATION_FILE);
    }
}
