package stepDefinitions;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import resources.APIResources;
import resources.TestDataBuild;
import resources.Utils;

import java.io.IOException;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;

public class placeValidation extends Utils {
    RequestSpecification res;
    ResponseSpecification resSpec;
    Response response;
    TestDataBuild data =new TestDataBuild();
    static String place_id;

    @When("the API call is success with status code {int}")
    public void the_api_call_is_success_with_status_code(Integer int1) {
        assertEquals(response.getStatusCode(),200);
    }

    @Then("{string} in response body is {string}")
    public void in_response_body_is(String keyValue, String ExpectedValue) {
        assertEquals( getJsonPath(response,keyValue),ExpectedValue);
    }

    @Given("DeletePlace Payload")
    public void deleteplace_Payload() throws IOException {
        // Write code here that turns the phrase above into concrete actions

        res =given().spec(requestSpecification()).body(data.deletePlacePayload(place_id));
    }

    @And("verify place_Id created maps to {string} using {string}")
    public void verifyPlace_IdCreatedMapsToUsing(String expectedName, String resource) throws IOException {
        // requestSpec
        place_id=getJsonPath(response,"place_id");
        res=given().spec(requestSpecification()).queryParam("place_id",place_id);
        userCallsWithHttpRequest(resource,"GET");
        String actualName=getJsonPath(response,"name");
        assertEquals(actualName,expectedName);
    }

    @Given("Add Place Payload with {string}  {string} {string}")
    public void addPlacePayloadWith(String name, String language, String address    ) throws IOException {
        res=given().spec(requestSpecification())
                .body(data.addPlacePayLoad(name,language,address));
    }

    @When("user calls {string} with {string} http request")
    public void userCallsWithHttpRequest(String resource, String method) {
        //constructor will be called with value of resource which you pass
        APIResources resourceAPI = APIResources.valueOf(resource);
        System.out.println(resourceAPI.getResource());

        resSpec =new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();

        if(method.equalsIgnoreCase("POST"))
            response =res.when().post(resourceAPI.getResource());
        else if(method.equalsIgnoreCase("GET"))
            response =res.when().get(resourceAPI.getResource());
    }
}
