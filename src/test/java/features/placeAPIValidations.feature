Feature: Validating place APIs

  @AddPlace @Regression
  Scenario Outline: Verify if place is being successfully added using payload
    Given Add place payload with "<name>" "<language>" "<address>"
    When  user calls "AddPlaceAPI" with "Post" http request
    Then the API call is success with status code 200
    And "status" in response body is "OK"
    And "scope" in response body is "APP"
    And verify the place_Id created maps to "<name>" using "getPlaceAPI"

    Examples:
      | name      | language | address  |  |
      | reddyhome | English  | Kondapur |  |

@DeletePlace @Regression
  Scenario: Verify if delete place functionality is working
    Given DeletePlace payload
    When user calls "deletePlaceAPI" with "post" http request
    Then the API call is success with status code 200
    And "status" in response body is "OK"