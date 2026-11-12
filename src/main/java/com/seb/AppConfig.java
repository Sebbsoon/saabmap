package com.seb;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

public class AppConfig {
    private static final String DEFAULT_HOST = "localhost";
    private static final String DEFAULT_PORT = "8080";
    private static final String DEFAULT_IMAGE_PATH = "/images";

    private String host;
    private int port;
    private String imagePath;

    private AppConfig(String host, int port, String imagePath) {
        this.host = host;
        this.port = port;
        this.imagePath = imagePath;
    }

    public static AppConfig load(Path path) {
        Properties properties = new Properties();
        properties.setProperty("host", DEFAULT_HOST);
        properties.setProperty("port", DEFAULT_PORT);
        properties.setProperty("image.path", DEFAULT_IMAGE_PATH);

        if (Files.exists(path)) {
            try (InputStream input = Files.newInputStream(path)) {
                properties.load(input);
            } catch (IOException e) {
                throw new IllegalStateException(
                        "Could not load configuration: " + path, e);
            }
        }

        return new AppConfig(
                properties.getProperty("host").trim(),
                parsePort(properties.getProperty("port")),
                properties.getProperty("image.path").trim());
    }

    private static int parsePort(String portStr) {
        try {
            return Integer.parseInt(portStr);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid port number: " + portStr, e);
        }
    }

    // Getters for host, port, and imagePath
    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    public String getImagePath() {
        return imagePath;
    }
}
