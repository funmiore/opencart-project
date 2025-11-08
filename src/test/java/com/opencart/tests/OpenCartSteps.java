package com.opencart.tests;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.By;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.testng.Assert; 
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration; 
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.WebElement; // Added for clarity

public class OpenCartSteps {

    // 1. Declare the WebDriver globally
    WebDriver driver;

    // --- SETUP STEP (Used by all scenarios) ---
    @Given("I initialize the browser and navigate to the base URL")
    public void i_initialize_the_browser_and_navigate_to_the_base_url() {
        WebDriverManager.chromedriver().setup();

        // Set Page Load Strategy to 'normal' to ensure full DOM loading
        ChromeOptions options = new ChromeOptions();
        options.setPageLoadStrategy(org.openqa.selenium.PageLoadStrategy.NORMAL); 
        
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        System.out.println("Browser initialized.");
    }

    // --- REGISTRATION STEPS ---

@Given("I am on the OpenCart registration page")
public void i_am_on_the_open_cart_registration_page() {
    System.out.println("Navigating to registration page...");
    driver.get("http://opencart.abstracta.us/index.php?route=account/register"); 
    
    // 💡 CRITICAL STABILITY ADDITION: Wait for the URL to contain 'account/register'
    WebDriverWait urlWait = new WebDriverWait(driver, Duration.ofSeconds(10));
    urlWait.until(ExpectedConditions.urlContains("account/register"));
}

@When("I fill in all required registration details")
public void i_fill_in_all_required_registration_details() {
    System.out.println("Filling registration form details...");

    // Wait for the first input field to be visible before proceeding to fill the form
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("input-firstname")));

    // Generate a unique email every time
    String uniqueEmail = "testuser" + System.currentTimeMillis() + "@example.com";
    
    driver.findElement(By.id("input-firstname")).sendKeys("Elo");
    driver.findElement(By.id("input-lastname")).sendKeys("yut");
    driver.findElement(By.id("input-email")).sendKeys("thef@opencart.com"); 
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
    System.out.println("Clicking Registration Continue...");
    
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    
    // Wait for a stable element (First Name input) to be present before finding the button
    // This is a safety measure repeated from the 'fill details' step
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("input-firstname")));
    
    // This XPath should work: //form[@id='form-register']//button[@type='submit']
    driver.findElement(By.xpath("//*[@id=\"content\"]/form/div/div/input[2]")).click();
}

@Then("I should see the account creation success message")
public void i_should_see_the_account_creation_success_message() {
    System.out.println("Verifying registration success...");
    
    String successXPath = "//*[@id=\"content\"]/h1";
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

    try {
        boolean successMessageDisplayed = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(successXPath))).isDisplayed();
        Assert.assertTrue(successMessageDisplayed, "Verification Failed: Registration failed.");
        System.out.println("TEST PASSED: Account successfully created.");
    } catch (org.openqa.selenium.TimeoutException e) {
        Assert.fail("Registration failed: Timeout waiting for success message.");
    } finally {
        // Close browser after successful registration test
        if (driver != null) {
            driver.quit();
        }
    }
}
    // --- LOGIN STEPS ---

    @Given("I am on the OpenCart login page")
    public void i_am_on_the_open_cart_login_page() {
        System.out.println("Navigating to login page...");
        driver.get("http://opencart.abstracta.us/index.php?route=account/login");
    }

    @When("I enter a valid email and password")
    public void i_enter_a_valid_email_and_password() {
        System.out.println("Entering login credentials...");
        // 💡 Use your actual working credentials here!
        driver.findElement(By.id("input-email")).sendKeys("richard@opencart.com"); 
        driver.findElement(By.id("input-password")).sendKeys("Opencart@1"); 
    }

    @When("I click the Login button")
public void i_click_the_login_button() {
    System.out.println("Submitting Login form directly using XPath...");

    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    
    // 💡 Using XPath to find the FORM element with id='form-login'
    By formLocator = By.xpath("//*[@id=\"form-login\"]/div[3]/button");
    
    // Explicitly wait for the FORM element to be visible before finding it.
    WebElement loginForm = wait.until(ExpectedConditions.visibilityOfElementLocated(formLocator));

    // Submit the form directly, bypassing issues with clicking the button locator.
    loginForm.submit();
    
    // Check for an immediate failure message (Red Alert Box) after click
    try {
        WebDriverWait failureWait = new WebDriverWait(driver, Duration.ofSeconds(2));
        failureWait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.alert-danger")));
        
        String errorMessage = driver.findElement(By.cssSelector("div.alert-danger")).getText();
        System.err.println("CRITICAL FAILURE: Login failed. Error message found: " + errorMessage);
        Assert.fail("Login failed: Credentials did not work. Please check the email and password.");
        
    } catch (org.openqa.selenium.TimeoutException ignored) {
        System.out.println("No immediate login error detected. Proceeding to success page verification.");
    }
}

    @Then("I should be redirected to the My Account page")
    public void i_should_be_redirected_to_the_my_account_page() {
        System.out.println("Verifying successful login...");
        
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        By myAccountHeaderLocator = By.xpath("//h1[text()='My Account']");

        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(myAccountHeaderLocator));
            System.out.println("TEST PASSED: User successfully logged in.");
        } catch (org.openqa.selenium.TimeoutException e) {
            Assert.fail("Verification Failed: Timed out waiting for 'My Account' page. Login was likely unsuccessful.");
        } 
        // ⚠️ NO driver.quit() here! We need the browser session for the Logout test.
    }
    
}