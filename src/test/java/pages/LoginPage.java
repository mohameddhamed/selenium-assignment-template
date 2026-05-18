package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class LoginPage extends BasePage {
    private By usernameField = By.id("username");
    private By passwordField = By.id("password");
    private By loginButton = By.xpath("//button[@type='submit' or contains(text(), 'Login') or contains(text(), 'Sign In')]");
    private By pageHeading = By.xpath("//h1[contains(text(), 'Login') or contains(text(), 'Sign')]");
    
    public LoginPage(WebDriver driver) {
        super(driver);
    }
    
    public void enterUsername(String username) {
        WebElement usernameElement = waitForElementToBeVisible(usernameField);
        usernameElement.clear();
        usernameElement.sendKeys(username);
    }
    
    public void enterPassword(String password) {
        WebElement passwordElement = driver.findElement(passwordField);
        passwordElement.clear();
        passwordElement.sendKeys(password);
    }
    
    public void clickLogin() {
        WebElement loginBtn = waitForElementToBeClickable(loginButton);
        loginBtn.click();
    }
    
    public void login(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        clickLogin();
    }
    
    public boolean isLoginPageDisplayed() {
        return waitForElementToBePresent(pageHeading);
    }
    
    public String getPageTitle() {
        return super.getPageTitle();
    }
}
