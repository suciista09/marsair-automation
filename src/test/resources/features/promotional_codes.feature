Feature: Promotional codes
  As a MarsAir Sales Director (Mark)
  I want to distribute promotional codes to customers
  So that they get discounts and are more tempted to purchase tickets

  # Assumptions (to discuss in the interview):
  # - First digit must be 1-9; 0 is invalid
  # - Uppercase only
  # - Valid only if it exactly matches XX9-XXX-999 with a correct check digit

  Scenario Outline: Search with a promotional code shows the matching result
    Given the user is on the search page
    When the user searches from "July" to "July (next year)" with promotional code "<code>"
    Then the result message contains "<message>"

    @MA-02-01 @high
    Examples: Valid code from the acceptance criteria
      | code        | message                                          |
      | AF3-FJK-418 | Promotional code AF3-FJK-418 used: 30% discount! |

    @MA-02-02 @high
    Examples: Valid code where the digit sum is 10 (check digit 0)
      | code        | message                                          |
      | JJ5-OPQ-320 | Promotional code JJ5-OPQ-320 used: 50% discount! |

    @MA-02-07 @high
    Examples: Invalid code with a wrong check digit
      | code        | message                              |
      | AF3-FJK-419 | Sorry, code AF3-FJK-419 is not valid |

    @MA-02-08 @high
    Examples: Invalid code where the sum is not taken modulo 10
      | code        | message                              |
      | JJ5-OPQ-321 | Sorry, code JJ5-OPQ-321 is not valid |

    @MA-02-15 @high
    Examples: Search without a promotional code
      | code | message         |
      |      | Seats available |

  # Dynamic data: codes are generated at runtime from the acceptance criteria rules.
  # Generated codes are logged in the Cucumber report (target/cucumber-report.html).

  @MA-02-dynamic-valid @high
  Scenario: Randomly generated valid promotional codes give the right discount
    Given the user is on the search page
    When the user searches from "July" to "July (next year)" with 3 generated valid promotional codes
    Then each result shows the promotional code used with its discount

  @MA-02-dynamic-invalid @high
  Scenario: Randomly generated invalid promotional codes are rejected
    Given the user is on the search page
    When the user searches from "July" to "July (next year)" with 3 generated invalid promotional codes
    Then each result shows the promotional code is not valid
