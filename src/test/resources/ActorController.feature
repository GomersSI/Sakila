Feature:
  Scenario: Actor fetched by id
    Given the actor with id 4 exists
    When get request is made for actor 4
    Then an actor is returned