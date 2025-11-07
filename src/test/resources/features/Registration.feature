Feature: Customer Registration on OpenCart
  As a potential shopper
  I want to register an account
  So that I can log in and place orders

  Scenario: Successful new user registration
    Given I initialize the browser and navigate to the base URL
    And I am on the OpenCart registration page
    When I fill in all required registration details
    And I accept the privacy policy
    And I click the Continue button
    Then I should see the account creation success message