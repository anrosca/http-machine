package com.httpmachine.core.config;

import com.httpmachine.core.config.ServerConfig.ServerConfigBuilder;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;

public class ServerConfigParser {

    public ServerConfig parse() {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("conf/server.xml");
        return doParse(inputStream);
    }

    public ServerConfig parse(InputStream inputStream) {
        return doParse(inputStream);
    }

    private ServerConfig doParse(InputStream inputStream) {
        try {
            return tryParseServerConfig(inputStream);
        } catch (ParserConfigurationException | SAXException | IOException e) {
            throw new ConfigParseException(e);
        }
    }

    private ServerConfig tryParseServerConfig(InputStream inputStream) throws ParserConfigurationException, SAXException, IOException {
        DocumentBuilder documentBuilder = DocumentBuilderFactory.newDefaultInstance().newDocumentBuilder();
        Document document = documentBuilder.parse(inputStream);
        Element rootElement = document.getDocumentElement();
        ServerConfigBuilder serverConfigBuilder = ServerConfig.builder();
        String port = rootElement.getAttribute("port");
        serverConfigBuilder.serverPort(Integer.parseInt(port));
        return serverConfigBuilder.executorConfig(parseExecutorConfig(rootElement)).build();
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
        return null;
    }
}
