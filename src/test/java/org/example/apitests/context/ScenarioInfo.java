package org.example.apitests.context;

import io.cucumber.java.Scenario;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.List;
public class ScenarioInfo {

    public List<Response> responses = new ArrayList<>();

}
