package com.opencart.tests;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.DataProvider;

@CucumberOptions(
    // 💡 Point 1: Location of your Gherkin files
    features = "src/test/resources/features/Registration.feature", 
    
    // 💡 Point 2: Location of your Step Definition Java files
    glue = "com.opencart.tests" 
)
public class TestRunner extends AbstractTestNGCucumberTests {

    // This data provider ensures parallel test execution (optional, but good practice)
    @Override
    @DataProvider(parallel = true)
    public Object[][] scenarios() {
        return super.scenarios();
    }
}