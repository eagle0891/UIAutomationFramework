package utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {
    private static Properties properties;

    static {
        try {
            // Get environment from Maven command line, default to 'qa'
            String env = System.getProperty("env", "qa");
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

    public static String getProperty(String key) {
        return properties.getProperty(key);
    }
}
