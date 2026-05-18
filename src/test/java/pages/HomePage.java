package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

public class HomePage extends BasePage {
    private By searchBox = By.xpath("//input[@placeholder='Search for a team or player...']");
    private By yearDropdown = By.xpath("//select[@name='year' or @id='year']");
    private By logoutLink = By.xpath("//a[contains(text(), 'Log Out') or contains(text(), 'Logout')]");
    private By siteHeader = By.xpath("//header | //div[@id='header'] | //nav");
    
    public HomePage(WebDriver driver) {
        super(driver);
    }
    
    public void searchForPlayer(String searchTerm) {
        WebElement search = waitForElementToBeVisible(searchBox);
        search.clear();
        search.sendKeys(searchTerm);
        search.submit();
    }
    
    public void selectYear(String year) {
        try {
            WebElement yearSelect = driver.findElement(yearDropdown);
            Select select = new Select(yearSelect);
            select.selectByValue(year);
        } catch (Exception e) {
            System.out.println("Year dropdown not found: " + e.getMessage());
        }
    }
    
    public void logout() {
        WebElement logoutBtn = waitForElementToBeClickable(logoutLink);
        logoutBtn.click();
    }
    
    public boolean isHomePageDisplayed() {
        return waitForElementToBePresent(siteHeader);
    }
    
    public String getPageTitle() {
        return super.getPageTitle();
    }
}
