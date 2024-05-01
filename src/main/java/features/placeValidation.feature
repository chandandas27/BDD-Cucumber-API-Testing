Feature: Validate Place API Responses

  @AddPlace @Regression
  Scenario Outline: Verify if place is being successfully added using addPlaceAPI
    Given Add Place Payload with "<name>"  "<language>" "<address>"
    When user calls "AddPlaceAPI" with "POST" http request
    When the API call is success with status code 200
    Then "status" in response body is "OK"
    Then "scope" in response body is "APP"
    And verify place_Id created maps to "<name>" using "getPlaceAPI"

    Examples:
      | name    | language | address            |
      | AAhouse | English  | World cross center |
#	|BBhouse | Spanish  |Sea cross center  |

  @DeletePlace @Regression
  Scenario: Verify if Delete Place functionality is working

    Given DeletePlace Payload
    When user calls "deletePlaceAPI" with "POST" http request
    When the API call is success with status code 200
    And "status" in response body is "OK"
