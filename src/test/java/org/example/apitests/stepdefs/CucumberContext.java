package org.example.apitests.stepdefs;

import io.cucumber.spring.CucumberContextConfiguration;
import org.example.apitests.context.Config;
import org.springframework.test.context.ContextConfiguration;

@CucumberContextConfiguration
@ContextConfiguration(classes = Config.class)
public class CucumberContext {
}
