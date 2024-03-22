package ru.playtox.config;

import org.apache.log4j.Logger;
import ru.playtox.MultiThreadedAccountTransfer;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigReader {

    private Properties properties = new Properties();
    private static final Logger logger = Logger.getLogger(MultiThreadedAccountTransfer.class);

    public ConfigReader() {
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream("application.properties")) {
            if (inputStream != null) {
                properties.load(inputStream);
            } else {
                logger.error("application.properties not found in classpath");
                throw new RuntimeException("application.properties not found in classpath");
            }
        } catch (IOException e) {
            logger.error("Error loading application.properties", e);
            throw new RuntimeException("Error loading application.properties");
        }
    }

    public String getProperty(String key) {
        return properties.getProperty(key);
    }
}

