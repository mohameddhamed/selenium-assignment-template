package utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import config.Config;
import io.github.bonigarcia.wdm.WebDriverManager;

public class DriverFactory {
    public static WebDriver createDriver() {
        WebDriverManager.chromedriver().setup();
        
        ChromeOptions options = new ChromeOptions();
        
        if (Config.isHeadless()) {
            options.addArguments("--headless=new");
        }
        
        options.addArguments("--start-maximized");
        options.addArguments("--disable-blink-features=AutomationControlled");
        options.addArguments("user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36");
        options.addArguments(String.format("--window-size=%d,%d", Config.getWindowWidth(), Config.getWindowHeight()));
        
        return new ChromeDriver(options);
    }
}

