package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.JavascriptExecutor;
import java.time.Duration;

public class BasePage {
    protected WebDriver driver;
    protected WebDriverWait wait;
    
    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }
    
    public String getPageTitle() {
        return driver.getTitle();
    }
    
    public void scrollToElement(WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView(true);", element);
    }
    
    public WebElement waitForElementToBeVisible(org.openqa.selenium.By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }
    
    public WebElement waitForElementToBeClickable(org.openqa.selenium.By locator) {
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }
    
    public boolean waitForElementToBePresent(org.openqa.selenium.By locator) {
        try {
            wait.until(ExpectedConditions.presenceOfElementLocated(locator));
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
    public void navigateTo(String url) {
        driver.navigate().to(url);
    }
    
    public void navigateBack() {
        driver.navigate().back();
    }
    
    public void navigateForward() {
        driver.navigate().forward();
    }
}
