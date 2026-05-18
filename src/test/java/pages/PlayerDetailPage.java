package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class PlayerDetailPage extends BasePage {
    private By playerName = By.xpath("//h1 | //h2[@itemprop='name'] | //div[@class='player-name']");
    private By playerStats = By.xpath("//table[@class='stats']");
    private By statsTable = By.xpath("//table");
    private By playerImage = By.xpath("//img[@itemprop='image']");
    private By birthDate = By.xpath("//*[contains(text(), 'Born:') or contains(@data-label, 'Birth')]");
    
    public PlayerDetailPage(WebDriver driver) {
        super(driver);
    }
    
    public boolean isPlayerDetailPageDisplayed() {
        return waitForElementToBePresent(playerName);
    }
    
    public String getPlayerName() {
        try {
            return driver.findElement(playerName).getText();
        } catch (Exception e) {
            return "";
        }
    }
    
    public boolean arePlayerStatsDisplayed() {
        return waitForElementToBePresent(statsTable);
    }
    
    public int getStatsTableCount() {
        return driver.findElements(statsTable).size();
    }
    
    public String getPageTitle() {
        return super.getPageTitle();
    }
    
    public void scrollToStats() {
        WebElement stats = driver.findElement(playerStats);
        scrollToElement(stats);
    }
    
    public boolean isPlayerImageDisplayed() {
        try {
            return driver.findElement(playerImage).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}
