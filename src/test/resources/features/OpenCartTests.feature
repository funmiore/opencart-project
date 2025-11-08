Feature: OpenCart Core Functionality Tests

  # --- Part 1: Registration Tests ---
  # As a potential shopper
  # I want to register an account
  # So that I can log in and place orders

  Scenario: Successful new user registration
    Given I initialize the browser and navigate to the base URL
    And I am on the OpenCart registration page
    When I fill in all required registration details
    And I accept the privacy policy
    And I click the Continue button
    Then I should see the account creation success message


  # --- Part 2 & 3: Login and Logout Tests ---
  # As a registered OpenCart customer
  # I want to log in and log out
  # So that I can access my account securely

  Scenario: Successful user login
    Given I initialize the browser and navigate to the base URL
    And I am on the OpenCart login page
    When I enter a valid email and password
    And I click the Login button
    Then I should be redirected to the My Account page
  