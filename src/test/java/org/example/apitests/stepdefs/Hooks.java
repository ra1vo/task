package org.example.apitests.stepdefs;

import io.cucumber.java.*;
import io.restassured.RestAssured;

public class Hooks {

    @Before
    public void before(Scenario scenario){
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }
}
