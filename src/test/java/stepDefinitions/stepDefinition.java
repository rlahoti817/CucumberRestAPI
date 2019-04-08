package stepDefinitions;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.And;
import cucumber.api.junit.Cucumber;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import java.util.ArrayList;

import org.junit.runner.RunWith;

import utility.*;

@RunWith(Cucumber.class)
public class stepDefinition {
	
	String hostURL = "";
	String resources = "";
	final String postBodyContent = "{}";

    @Given("^Application calls the RestFul API URL$")
    public void application_calls_the_restful_api_url() throws Throwable {
    	hostURL = "http://hateos-test-hateos-test.7e14.starter-us-west-2.openshiftapps.com";
    }

    @When("^Application sends the resource with API call$")
    public void application_sends_the_resource_with_api_call() throws Throwable {
    	resources = "/offers";
    }

    @Then("^JSON Response is tested for the given validation$")
    public void json_response_is_tested_for_the_given_validation() throws Throwable {
    	
    	RestAssured.baseURI=hostURL;
    	
		Response res = given().
	
		body(postBodyContent).
		header("accept","application/json").
		header("Content-Type","application/json").
	
		when().
	
		post(resources).
	
		then().assertThat().statusCode(200).and().contentType(ContentType.JSON).
		
		extract().response();
		
		String reponseString = res.asString();
		
		System.out.println(" Response is : " + reponseString);
		
		System.out.println("*************************************");
		
		JsonPath js = new JsonPath(reponseString);
		
		//Getting the JSON Object Size
		int arraySize = js.get("$.size()");
		
		System.out.println("Validation 1 :  Rest API  Response have  : " + arraySize + "  JSON Objects");
		
		//Getting the list of Offer ID's available in the JSON object
		System.out.println("*************************************");
		
		System.out.println("Validation 2 : Getting all the OfferId's ");
		for(int i = 0;i<arraySize;i++) {
			//String name = js.get("offersList[" + i + "].["+ i + "].offerId");
		String name = js.getString("[" + i + "].offersList[0].offerId");
				System.out.println(name);
		}
		
		
		//Getting the email Address from the Customer Object
		System.out.println("*************************************");
		
		System.out.println("Validation 3 : Getting the email address and checking for valid");
		
		for(int j = 0;j<arraySize;j++) {
		String emailAddress = js.getString("[" + j + "].dataList.customer[0].emailAdr");
		System.out.print(emailAddress);
		System.out.print("  :  " );
		
		boolean valid = checkValidity.isEmailValid(emailAddress);
		
		if(valid) {
			System.out.println("VALID");
		}else
		{
			System.out.println("NOT VALID");
			
		}
		
	}
		
    }

    @And("^validation is done on the response object$")
    public void validation_is_done_on_the_response_object() throws Throwable {
    	//System.out.println("Fourth");
    }
}
