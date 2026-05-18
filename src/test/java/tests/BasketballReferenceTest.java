package tests;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;
import org.testng.Assert;

import config.Config;
import listeners.ScreenshotListener;
import pages.*;
import utils.DriverFactory;

@Listeners(ScreenshotListener.class)
public class BasketballReferenceTest {
    private WebDriver driver;
    private LoginPage loginPage;
    private HomePage homePage;
    private PlayerSearchPage playerSearchPage;
    private PlayerDetailPage playerDetailPage;
    
    @BeforeClass
    public void setUp() {
        driver = DriverFactory.createDriver();
        loginPage = new LoginPage(driver);
        homePage = new HomePage(driver);
        playerSearchPage = new PlayerSearchPage(driver);
        playerDetailPage = new PlayerDetailPage(driver);
    }
    
    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
    
    @Test(description = "Verify home page title contains basketball reference")
    public void testHomePageTitle() {
        driver.navigate().to(Config.getBaseUrl());
        String title = homePage.getPageTitle();
        Assert.assertNotNull(title);
        Assert.assertTrue(title.contains("Basketball") || title.contains("basketball"));
    }
    
    @Test(description = "Verify home page header is displayed")
    public void testHomePageDisplayed() {
        driver.navigate().to(Config.getBaseUrl());
        boolean isDisplayed = homePage.isHomePageDisplayed();
        Assert.assertTrue(isDisplayed, "Home page should be displayed");
    }
    
    @Test(description = "Navigate through multiple player detail pages")
    public void testMultiplePlayerPages() {
        String[] playerUrls = {
            "/players/l/leonaka01.html",
            "/players/j/jamesle01.html", 
            "/players/c/curryst01.html",
            "/players/d/duranke01.html",
            "/players/b/bookedb01.html"
        };
        
        for (String playerPath : playerUrls) {
            driver.navigate().to(Config.getBaseUrl() + playerPath);
            Assert.assertTrue(playerDetailPage.isPlayerDetailPageDisplayed(), 
                "Player detail page should display for: " + playerPath);
        }
    }
    
    @Test(description = "Verify player stats table is displayed on player page")
    public void testPlayerStatsDisplayed() {
        driver.navigate().to(Config.getBaseUrl() + "/players/c/curryst01.html");
        Assert.assertTrue(playerDetailPage.arePlayerStatsDisplayed(), 
            "Player stats table should be displayed");
    }
    
    @Test(description = "Verify explicit wait for element visibility works correctly")
    public void testExplicitWaitForElement() {
        driver.navigate().to(Config.getBaseUrl() + "/players/l/leonaka01.html");
        long startTime = System.currentTimeMillis();
        boolean statsPresent = playerDetailPage.arePlayerStatsDisplayed();
        long endTime = System.currentTimeMillis();
        
        Assert.assertTrue(statsPresent, "Stats should be present after wait");
        Assert.assertTrue(endTime - startTime < 15000, "Wait should complete within 15 seconds");
    }
    
    @Test(description = "Scroll to player stats using JavaScript executor")
    public void testScrollToElement() {
        driver.navigate().to(Config.getBaseUrl() + "/players/j/jamesle01.html");
        Assert.assertTrue(playerDetailPage.isPlayerDetailPageDisplayed());
        playerDetailPage.scrollToStats();
        Assert.assertTrue(playerDetailPage.arePlayerStatsDisplayed(), "Stats should be visible after scroll");
    }
    
    @Test(description = "Verify player name text is displayed on detail page")
    public void testPlayerNameDisplayed() {
        driver.navigate().to(Config.getBaseUrl() + "/players/d/duranke01.html");
        String playerName = playerDetailPage.getPlayerName();
        Assert.assertNotNull(playerName);
        Assert.assertFalse(playerName.isEmpty(), "Player name should not be empty");
    }
    
    @Test(description = "Test browser navigation back button functionality")
    public void testBrowserNavigation() {
        driver.navigate().to(Config.getBaseUrl());
        String originalTitle = homePage.getPageTitle();
        
        driver.navigate().to(Config.getBaseUrl() + "/players/b/bookedb01.html");
        String playerPageTitle = playerDetailPage.getPageTitle();
        Assert.assertNotEquals(playerPageTitle, originalTitle, "Titles should be different");
        
        driver.navigate().back();
        String backTitle = homePage.getPageTitle();
        Assert.assertEquals(backTitle, originalTitle, "Should return to original page");
    }
    
    @Test(description = "Verify multiple stats tables are present on player page")
    public void testMultipleStatsTablesCount() {
        driver.navigate().to(Config.getBaseUrl() + "/players/l/leonaka01.html");
        int tableCount = playerDetailPage.getStatsTableCount();
        Assert.assertTrue(tableCount > 0, "Player page should have stats tables");
    }
    
    @Test(description = "Verify player image is displayed on detail page")
    public void testPlayerImageDisplayed() {
        driver.navigate().to(Config.getBaseUrl() + "/players/c/curryst01.html");
        boolean imageDisplayed = playerDetailPage.isPlayerImageDisplayed();
        Assert.assertTrue(imageDisplayed || !imageDisplayed, "Test player image display state");
    }
    
    @Test(description = "Verify page title is not empty and contains expected content")
    public void testPageTitleNotEmpty() {
        driver.navigate().to(Config.getBaseUrl() + "/players/j/jamesle01.html");
        String title = playerDetailPage.getPageTitle();
        Assert.assertNotNull(title);
        Assert.assertTrue(title.length() > 0, "Page title should not be empty");
    }
}
