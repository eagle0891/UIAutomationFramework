package utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {
    private static Properties properties;

    static {
        try {
            // Get environment from Maven command line, default to 'qa'
            String env = System.getProperty("env", "QA");
            String path = "src/test/resources/config/" + env + ".properties";

            FileInputStream input = new FileInputStream(path);
            properties = new Properties();
            properties.load(input);
            input.close();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Could not load properties file.");
        }
    }

    /**
     * This is the updated method.
     * It prioritizes Command Line (-D) values over File values.
     */
    public static String getProperty(String key) {
        // 1. Check System Properties first (e.g., from GitHub Secrets or Maven CLI)
        String value = System.getProperty(key);

        // 2. If it's not provided in the CLI, fetch it from the .properties file
        if (value == null || value.trim().isEmpty()) {
            value = properties.getProperty(key);
        }

        return value;
    }
}
