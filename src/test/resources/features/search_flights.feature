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

    @MA-01-09 @high
    Examples: Seats available longest
      | departing | returning        | message                                     |
      | July      | December (two years from now) | Seats available! Call 0800 MARSAIR to book! |

    @MA-01-02 @high
    Examples: No seats available
      | departing | returning            | message                                   |
      | December  | December (next year) | Sorry, there are no more seats available. |

  # Flights leave every six months, in July and December, both ways.
  # Trips for the next two years should be searchable.

  Scenario Outline: The <dropdown> dropdown only shows July and December
    Given the user is on the search page
    When the user opens the "<dropdown>" dropdown
    Then it only shows July and December options

    @MA-01-03 @medium
    Examples: Departing
      | dropdown  |
      | departing |

    @MA-01-04 @medium
    Examples: Returning
      | dropdown  |
      | returning |

  Scenario Outline: The <dropdown> dropdown covers the next two years
    Given the user is on the search page
    When the user opens the "<dropdown>" dropdown
    Then it covers the next two years:
      | July (next year)              |
      | December (next year)          |
      | July (two years from now)     |
      | December (two years from now) |

    @MA-01-05 @medium
    Examples: Departing
      | dropdown  |
      | departing |

    @MA-01-06 @medium
    Examples: Returning
      | dropdown  |
      | returning |

  Scenario Outline: The <dropdown> dropdown options are in chronological order and clearly labelled
    Given the user is on the search page
    When the user opens the "<dropdown>" dropdown
    Then the options are in this order:
      | July                          |
      | December                      |
      | July (next year)              |
      | December (next year)          |
      | July (two years from now)     |
      | December (two years from now) |

    @MA-01-07 @low
    Examples: Departing
      | dropdown  |
      | departing |

    @MA-01-08 @low
    Examples: Returning
      | dropdown  |
      | returning |
