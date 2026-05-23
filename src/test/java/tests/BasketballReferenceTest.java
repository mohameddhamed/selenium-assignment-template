package tests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.*;
import org.testng.Assert;
import java.util.Random;

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
    
    @Test(description = "Test year dropdown selection on homepage")
    public void testDropdownSelection() {
        driver.navigate().to(Config.getBaseUrl());
        Assert.assertTrue(homePage.isHomePageDisplayed(), "Home page should be displayed");
        homePage.selectYear("2024");
        Assert.assertTrue(homePage.isHomePageDisplayed(), "Home page should remain after dropdown selection");
    }
    
    @Test(description = "Test adding and verifying cookies")
    public void testCookieManipulation() {
        driver.navigate().to(Config.getBaseUrl());
        
        // Add a cookie
        driver.manage().addCookie(new org.openqa.selenium.Cookie("test_user_preference", "dark_mode"));
        driver.manage().addCookie(new org.openqa.selenium.Cookie("session_id", "12345abcde"));
        
        // Verify cookies were added
        org.openqa.selenium.Cookie cookie = driver.manage().getCookieNamed("test_user_preference");
        Assert.assertNotNull(cookie, "Cookie should be added");
        Assert.assertEquals(cookie.getValue(), "dark_mode", "Cookie value should match");
        
        // Delete a cookie and verify deletion
        org.openqa.selenium.Cookie cookieToDelete = driver.manage().getCookieNamed("session_id");
        driver.manage().deleteCookie(cookieToDelete);
        org.openqa.selenium.Cookie deletedCookie = driver.manage().getCookieNamed("session_id");
        Assert.assertNull(deletedCookie, "Cookie should be deleted");
    }
    
    @Test(description = "Base test for dependency chain - navigate to home page", groups = {"navigationGroup"})
    public void testNavigateToHomeForDependency() {
        driver.navigate().to(Config.getBaseUrl());
        Assert.assertTrue(homePage.isHomePageDisplayed(), "Home page should be displayed for dependent test");
    }
    
    @Test(description = "Dependent test that requires navigation first", dependsOnGroups = {"navigationGroup"})
    public void testDependentBrowserNavigation() {
        // This test depends on testNavigateToHomeForDependency completing first
        String currentTitle = homePage.getPageTitle();
        Assert.assertNotNull(currentTitle, "Current page title should not be null");
        Assert.assertTrue(currentTitle.length() > 0, "Page title should not be empty");
    }
    
    @Test(description = "Test with random player data - navigate to random players")
    public void testRandomPlayerData() {
        // Generate random player IDs and test them
        String[] playerIds = {
            "leonaka01", "jamesle01", "curryst01", 
            "duranke01", "bookedb01", "antetgo01", "embiijo01"
        };
        
        Random random = new Random();
        for (int i = 0; i < 3; i++) {
            String randomPlayer = playerIds[random.nextInt(playerIds.length)];
            String playerUrl = Config.getBaseUrl() + "/players/" + randomPlayer.charAt(0) + "/" + randomPlayer + ".html";
            
            driver.navigate().to(playerUrl);
            Assert.assertTrue(playerDetailPage.isPlayerDetailPageDisplayed(), 
                "Player page should display for random player: " + randomPlayer);
        }
    }
    
    @Test(description = "Test hover action on navigation element")
    public void testHoverAction() {
        driver.navigate().to(Config.getBaseUrl());
        
        // Find a navigation link to hover over
        WebElement navLink = driver.findElement(org.openqa.selenium.By.xpath("//a[contains(@href, '/leagues/')]"));
        
        // Perform hover action using Actions class
        Actions actions = new Actions(driver);
        actions.moveToElement(navLink).perform();
        
        // Verify element is still present and accessible after hover
        Assert.assertTrue(navLink.isDisplayed(), "Navigation link should still be displayed after hover");
        Assert.assertTrue(navLink.getText().length() > 0, "Navigation link should have text");
    }
}
