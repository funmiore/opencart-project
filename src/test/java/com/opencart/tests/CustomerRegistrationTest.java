package com.opencart.tests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.By;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.AfterTest;

import io.github.bonigarcia.wdm.WebDriverManager;

public class CustomerRegistrationTest {

    WebDriver driver;

    @BeforeTest
    public void setup() {
        // WebDriverManager automatically sets up the Chrome driver
        WebDriverManager.chromedriver().setup();
        
        // Initialize the Chrome browser
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    @Test
    public void testValidRegistration() throws InterruptedException {
        System.out.println("Starting Valid Registration Test...");

        // Use System.currentTimeMillis() to ensure the email is unique every time
        String uniqueEmail = "testuser" + System.currentTimeMillis() + "@example.com";
        
        // 1. Navigate to the OpenCart Registration Page
        driver.get("http://opencart.abstracta.us/index.php?route=account/register"); 
        
        // 2. Enter Test Data
        driver.findElement(By.id("input-firstname")).sendKeys("Automated");
        driver.findElement(By.id("input-lastname")).sendKeys("User");
        driver.findElement(By.id("input-email")).sendKeys(uniqueEmail); 
        driver.findElement(By.id("input-telephone")).sendKeys("5551234567");
        driver.findElement(By.id("input-password")).sendKeys("SecureP@ss123");
        driver.findElement(By.id("input-confirm")).sendKeys("SecureP@ss123");
        
        // 3. Agree to Privacy Policy and Click Continue
        driver.findElement(By.name("agree")).click(); 
        driver.findElement(By.cssSelector("input.btn-primary")).click();

        // 4. Verification
        String expectedTitle = "Your Account Has Been Created!";
        String actualTitle = driver.getTitle();
        
        if (actualTitle.contains(expectedTitle)) {
            System.out.println("TEST PASSED: Account successfully created. Title: " + actualTitle);
        } else {
            // Print error to the console
            System.err.println("TEST FAILED: Expected title '" + expectedTitle + "' but got '" + actualTitle + "'");
        }
    }

    @AfterTest
    public void tearDown() {
        // 5. Wait a moment and close the browser after the test runs
        try {
            Thread.sleep(3000); // Wait 3 seconds to visually confirm the result
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        if (driver != null) {
            driver.quit();
        }
    }
}