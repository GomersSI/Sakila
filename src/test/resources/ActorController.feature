Feature:

  Scenario: List of actors fetched
    Given multiple actors exist
    When get request is made for all actors
    Then the list of actors is returned

  Scenario: Actor fetched by id
    Given the actor with id 4 exists
    When get request is made for actor 4
    Then an actor is returned

  Scenario: Actors films fetched by id
    Given the actor with id 4 exists
    When get request is made for actors with id 4 films
    Then a list of films is returned

  Scenario: Create an actor
    Given the actors information is known
    When the request is made create the actor
    Then created actor is returned

  Scenario:
    Given actor with id 4 exists and we want to edit their data
    When patch request is made for actor with id 4
    Then patched actor is returned