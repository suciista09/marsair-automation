Feature: Search date dropdowns
  As a MarsAir Sales Director (Mark)
  I want potential customers to be able to search for flights to Mars
  So that they see what trips are available

  # Flights leave every six months, in July and December, both ways.
  # Trips for the next two years should be searchable.

  Scenario Outline: The <dropdown> dropdown only shows July and December
    Given the user is on the search page
    When the user opens the "<dropdown>" dropdown
    Then it only shows July and December options

    @MA-01-03 @medium @ready
    Examples: Departing
      | dropdown  |
      | departing |

    @MA-01-04 @medium @ready
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

    @MA-01-05 @medium @ready
    Examples: Departing
      | dropdown  |
      | departing |

    @MA-01-06 @medium @ready
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

    @MA-01-07 @low @ready
    Examples: Departing
      | dropdown  |
      | departing |

    @MA-01-08 @low @ready
    Examples: Returning
      | dropdown  |
      | returning |
