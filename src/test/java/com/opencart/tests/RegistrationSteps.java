package com.opencart.tests;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.By;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.testng.Assert; // Used for simple verification

public class RegistrationSteps {

    // 1. Declare the WebDriver globally for all steps
    WebDriver driver;

    // 2. Setup method (using @Given for Cucumber setup)
    @Given("I initialize the browser and navigate to the base URL")
    public void i_initialize_the_browser_and_navigate_to_the_base_url() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    // 3. STEP DEFINITIONS (from your Gherkin file)

    @Given("I am on the OpenCart registration page")
    public void i_am_on_the_open_cart_registration_page() {
        System.out.println("Navigating to registration page...");
        driver.get("http://opencart.abstracta.us/index.php?route=account/register"); 
    }

    @When("I fill in all required registration details")
    public void i_fill_in_all_required_registration_details() {
        System.out.println("Filling form details...");
        
        // Generate a unique email every time
        String uniqueEmail = "testuser" + System.currentTimeMillis() + "@example.com";
        
        driver.findElement(By.id("input-firstname")).sendKeys("Cucumber");
        driver.findElement(By.id("input-lastname")).sendKeys("Test");
        driver.findElement(By.id("input-email")).sendKeys(uniqueEmail); 
        driver.findElement(By.id("input-telephone")).sendKeys("5551234567");
        driver.findElement(By.id("input-password")).sendKeys("SecureP@ss123");
        driver.findElement(By.id("input-confirm")).sendKeys("SecureP@ss123");
    }

    @When("I accept the privacy policy")
    public void i_accept_the_privacy_policy() {
        System.out.println("Accepting policy...");
        driver.findElement(By.name("agree")).click(); 
    }

    @When("I click the Continue button")
    public void i_click_the_continue_button() {
        System.out.println("Clicking Continue...");
        driver.findElement(By.cssSelector("input.btn-primary")).click();
    }

    @Then("I should see the account creation success message")
    public void i_should_see_the_account_creation_success_message() {
        String expectedTitle = "Your Account Has Been Created!";
        String actualTitle = driver.getTitle();
        
        // Use TestNG/JUnit Assert to verify the title
        Assert.assertTrue(actualTitle.contains(expectedTitle), 
            "Verification Failed: Expected title to contain '" + expectedTitle + "' but got '" + actualTitle + "'");

        System.out.println("TEST PASSED: Account successfully created.");
        
        // Close the driver after verification
        if (driver != null) {
            driver.quit();
        }
    }
}