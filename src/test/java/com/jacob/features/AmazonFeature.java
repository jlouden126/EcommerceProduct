package com.jacob.features;

import com.jacob.steps.AmazonSteps;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import net.serenitybdd.annotations.Steps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class AmazonFeature {
    private static final Logger LOGGER = LoggerFactory.getLogger(AmazonFeature.class);

    @Steps 
    AmazonSteps amazonSteps;

    @When( "user navigates to amazon site with product")
    public void searchAmazon() {
        System.out.println("WORKING ");
        LOGGER.info("AmazonSteps instance: " + amazonSteps);

        amazonSteps.searchAmazon(  );
    }

    @Then( "status code is verified to be {int}")
    public void verifyStatusCode( int statusCode ) {
        amazonSteps.verifyStatusCode( statusCode );
    }
}
