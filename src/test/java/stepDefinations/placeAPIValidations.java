package stepDefinations;

import TestBase.TestBase;
import TestDataHelper.TestDataHelper;
import enums.APIResources;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.junit.Assert;

import static io.restassured.RestAssured.given;

public class placeAPIValidations extends TestBase {
    TestDataHelper testDataHelper = new TestDataHelper();
    RequestSpecification request;
    Response response;
    APIResources apiResources;
    static String place_Id;

    @Given("Add place payload with {string} {string} {string}")
    public void add_place_payload_with(String name, String language, String address) {
       // responseSpecification();
        request = given().spec(requestSpecification()).
                body(testDataHelper.addPlacePayLoad(name, language, address));

    }

    @When("user calls {string} with {string} http request")
    public void user_calls_with_http_request(String resource, String httpMethodType) {
        apiResources = APIResources.valueOf(resource);

        if (httpMethodType.equalsIgnoreCase("post")) {
            response = given().log().all().spec(request).when().post(apiResources.getResources());
        } else if (httpMethodType.equalsIgnoreCase("delete")) {
            response = given().log().all().spec(request).when().delete(apiResources.getResources());
        } else if (httpMethodType.equalsIgnoreCase("get")) {
            response = given().log().all().spec(request).when().get(apiResources.getResources());
        }

    }

    @Then("the API call is success with status code {int}")
    public void the_api_call_is_success_with_status_code(int statusCode) {
        Assert.assertEquals(response.getStatusCode(), statusCode);
    }

    @Then("{string} in response body is {string}")
    public void in_response_body_is(String key, String value) {
        Assert.assertEquals(getJSONPath(response.asString(), key), value);
    }

    @Then("verify the place_Id created maps to {string} using {string}")
    public void verify_the_place_id_created_maps_to_using(String expectedName, String resource) {

        place_Id=getJSONPath(response.asString(),"place_id");
        request=given().spec(requestSpecification()).queryParam("place_id",place_Id);
        user_calls_with_http_request(resource,"GET");
        Assert.assertEquals(getJSONPath(response.asString(),"name"),expectedName);
    }

    @Given("DeletePlace payload")
    public void delete_place_payload() {
     request=given().spec(requestSpecification()).body(testDataHelper.deletePlacePayLoad(place_Id));
    }

}
