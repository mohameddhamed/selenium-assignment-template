package config;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Config {
    private static Properties properties = new Properties();
    
    static {
        try {
            String resourcePath = "src/test/resources/test.properties";
            FileInputStream fis = new FileInputStream(resourcePath);
            properties.load(fis);
            fis.close();
        } catch (IOException e) {
            System.err.println("Could not load test.properties: " + e.getMessage());
        }
    }
    
    public static String getBaseUrl() {
        return properties.getProperty("base_url", "https://www.basketball-reference.com");
    }
    
    public static String getUsername() {
        return properties.getProperty("username");
    }
    
    public static String getPassword() {
        return properties.getProperty("password");
    }
    
    public static boolean isHeadless() {
        return Boolean.parseBoolean(properties.getProperty("headless", "false"));
    }
    
    public static int getWindowWidth() {
        return Integer.parseInt(properties.getProperty("window_width", "1920"));
    }
    
    public static int getWindowHeight() {
        return Integer.parseInt(properties.getProperty("window_height", "1080"));
    }
}
