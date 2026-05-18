package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import java.util.List;

public class PlayerSearchPage extends BasePage {
    private By searchResults = By.xpath("//div[@class='search-results'] | //table[@class='stats'] | //div[contains(@class, 'result')]");
    private By playerLinks = By.xpath("//a[contains(@href, '/players/')]");
    private By resultCount = By.xpath("//*[contains(text(), 'results') or contains(text(), 'found')]");
    private By filterForm = By.xpath("//form | //div[@class='filters']");
    
    public PlayerSearchPage(WebDriver driver) {
        super(driver);
    }
    
    public boolean areSearchResultsDisplayed() {
        return waitForElementToBePresent(searchResults);
    }
    
    public int getPlayerLinkCount() {
        List<WebElement> links = driver.findElements(playerLinks);
        return links.size();
    }
    
    public void clickFirstPlayerResult() {
        WebElement firstPlayer = waitForElementToBeClickable(playerLinks);
        firstPlayer.click();
    }
    
    public List<WebElement> getAllPlayerLinks() {
        return driver.findElements(playerLinks);
    }
    
    public String getResultCountText() {
        try {
            return driver.findElement(resultCount).getText();
        } catch (Exception e) {
            return "No count found";
        }
    }
    
    public boolean isFilterFormPresent() {
        return waitForElementToBePresent(filterForm);
    }
}
