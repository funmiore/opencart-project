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

    # INVALID REGISTRATION
  Scenario: Invalid user registration with existing account
    Given I initialize the browser and navigate to the base URL
    And I am on the OpenCart registration page
    When I fill in registration details with an existing email
    And I accept the privacy policy
    And I click the Continue button
    Then I should see the warning message "Warning: E-Mail Address is already registered!"

    # INVALID REGISTRATION SCENARIO (No Agreement)
  Scenario: Invalid user registration without accepting privacy policy
    Given I initialize the browser and navigate to the base URL
    And I am on the OpenCart registration page
    When I fill in all required registration details
    # We intentionally skip the step: And I accept the privacy policy
    And I click the Continue button
    Then I should see the warning message "Warning: You must agree to the Privacy Policy!"

    # INVALID REGISTRATION SCENARIO (Missing Email)
  Scenario: Invalid user registration with missing mandatory email
    Given I initialize the browser and navigate to the base URL
    And I am on the OpenCart registration page
    When I fill in registration details with a missing email
    And I accept the privacy policy
    And I click the Continue button
    Then I should see the email field validation message "E-Mail Address does not appear to be valid!"


  # --- Part 2 & 3: Login and Logout Tests ---
  # As a registered OpenCart customer
  # I want to log in and log out
  # So that I can access my account securely

  Scenario: Successful user login and logout
    Given I initialize the browser and navigate to the base URL
    And I am on the OpenCart login page
    When I enter a valid email and password
    And I click the Login button
    Then I should be redirected to the My Account page
    
    # LOGOUT STEPS
    When I click the My Account link to open the dropdown
    And I click the Logout link in the dropdown
    Then I should be redirected to the Account Logout success page

    # INVALID LOGIN WITH INCORRECT PASSWORD 
  Scenario: Invalid user login with incorrect password
    Given I initialize the browser and navigate to the base URL
    And I am on the OpenCart login page
    When I enter a valid email and an incorrect password
    And I click the Login button
    Then I should see the warning message "Warning: No match for E-Mail Address and/or Password."
  