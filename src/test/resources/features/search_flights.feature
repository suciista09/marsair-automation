Feature: Search for flights to Mars
  As a MarsAir Sales Director (Mark)
  I want potential customers to be able to search for flights to Mars
  So that they see what trips are available

  # MA-01-01 (seats available) and MA-01-02 (no seats available)
  Scenario Outline: Search shows seat availability for the chosen trip
    Given the user is on the search page
    When the user searches with the departing "<departing>" and returning "<returning>" dates
    Then the availability message "<message>" is shown

    @MA-01-01 @high
    Examples: Seats available
      | departing | returning        | message                                     |
      | July      | July (next year) | Seats available! Call 0800 MARSAIR to book! |

    @MA-01-02 @high
    Examples: No seats available
      | departing | returning            | message                                   |
      | December  | December (next year) | Sorry, there are no more seats available. |
