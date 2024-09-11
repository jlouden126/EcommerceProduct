package com.jacob.steps;

import net.serenitybdd.annotations.Step;
import org.junit.Assert;

import io.restassured.response.ValidatableResponse;
import net.serenitybdd.rest.SerenityRest;
import static net.serenitybdd.rest.SerenityRest.given;


public class AmazonSteps {

    @Step 
    public ValidatableResponse searchAmazon() {
        return given()
        .relaxedHTTPSValidation()
        .when()
        .get("https://www.amazon.com/s?k=basketball")
        .then();
    }

    @Step 
    public void verifyStatusCode( int statusCode ) {
        Assert.assertEquals( "Status Code does not match", statusCode, SerenityRest.lastResponse().statusCode());
    }
}
