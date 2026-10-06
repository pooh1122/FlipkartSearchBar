package com.qa.flipkart;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class FlipkartSearch {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		String searchText="iphone15";
		// 1. Launch Chrome
		ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized", "--disable-notifications");
        
       
        WebDriver driver= new ChromeDriver(options);
        WebDriverWait wait= new WebDriverWait(driver,Duration.ofSeconds(15));
        try {
        driver.get("https://www.flipkart.com/");
        
     // 3. Close the login popup if it appears
        try{
        	new WebDriverWait(driver, Duration.ofSeconds(3)).until(ExpectedConditions.elementToBeClickable(By.xpath("//span[@class='b3wTlE']")))
        .click();
	}
        catch (TimeoutException e) {
            System.out.println("Login popup not shown - continuing");
        }
        
      
        // 4. Find the search bar, type the text and press Enter
        WebElement searchBox= wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("q")));
        searchBox.click();
        searchBox.sendKeys(searchText);
        searchBox.sendKeys(Keys.ENTER);
        
     // 5. Wait for the results page and product cards
        wait.until(ExpectedConditions.urlContains("search"));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("div[data-id]")));

        // 6. Verify the results
        List<WebElement> products = driver.findElements(By.cssSelector("div[data-id]"));
        String title = driver.getTitle();

        System.out.println("Page title      : " + title);
        System.out.println("Products found  : " + products.size());

        if (title.toLowerCase().contains("iphone") && !products.isEmpty()) {
            System.out.println("RESULT: PASS - search results displayed for '" + searchText + "'");
        } else {
            System.out.println("RESULT: FAIL - results not as expected for '" + searchText + "'");
        }

    } catch(Exception e) {
        System.out.println("RESULT: FAIL - " + e.getMessage());
    } finally {
        // 7. Close the browser
        driver.quit();
    }
        
	}

}
