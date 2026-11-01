Feature: Invalid return dates
  As a MarsAir Sales Director (Mark)
  I want to prevent potential customers from searching for invalid trips
  So that they don't waste time, and book valid ones

  Scenario Outline: Return date less than 1 year after departure shows the schedule error
    Given the user is on the search page
    When the user searches with the departing "<departing>" and returning "<returning>" dates
    Then the result message contains "Unfortunately, this schedule is not possible. Please try again."

    @MA-04-01 @high
    Examples: Return 5 months after departure
      | departing        | returning            |
      | July (next year) | December (next year) |

    @MA-04-02 @medium
    Examples: Return same as departure
      | departing        | returning        |
      | July (next year) | July (next year) |

    @MA-04-03 @medium
    Examples: Return before departure
      | departing            | returning        |
      | December (next year) | July (next year) |

  @MA-04-04 @medium
  Scenario: Valid search after the schedule error
    Given the user is on the search page
    And the user searches with the departing "July (next year)" and returning "December (next year)" dates
    And the result message contains "Unfortunately, this schedule is not possible. Please try again."
    When the user is on the search page
    And the user searches with the departing "July (next year)" and returning "July (two years from now)" dates
    Then the schedule error message is not shown
    And the seat availability result is shown