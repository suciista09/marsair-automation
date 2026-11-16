Feature: Link to home page
  As a MarsAir Sales Director (Mark)
  I want potential customers to be able to go back to the flight search from anywhere on the site
  So that they are guided towards booking trips

  # TC-H02 | Story #3 / AC2
  Scenario: Clicking "Book a ticket to the red planet now!" takes the user to the home page
    Given the user is on the MarsAir home page
    When the user clicks "Book a ticket to the red planet now!"
    Then the home page with flight search is shown
