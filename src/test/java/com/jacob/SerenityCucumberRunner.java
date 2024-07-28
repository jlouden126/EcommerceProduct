package com.jacob;

import org.junit.runner.RunWith;
import net.serenitybdd.cucumber.CucumberWithSerenity;
import io.cucumber.junit.CucumberOptions;
import org.junit.Test;

@RunWith( CucumberWithSerenity.class )
@CucumberOptions(
    features = { "src/test/resources/features" },
    glue = "com.jacob.features",
    plugin = { "pretty" }
)

public class SerenityCucumberRunner {
}
