package org.example.apitests.context;

import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;
import org.example.apitests.http.CsvHttpService;
import org.example.models.Person;
import org.example.csv.CsvWriter;
import org.example.csv.CsvReader;
import org.example.apitests.stepdefimpl.CsvStepdefImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Scope;

@Configuration
@PropertySource("classpath:application.properties")
public class Config {

    @Bean
    public CsvWriter<Person> csvWriter(){
        return new CsvWriter<>();
    }

    @Bean
    public CsvReader<Person> csvPersonReader(){
        return new CsvReader<>(Person.class);
    }

    @Value("${api.base_url}")
    String basePath;

    @Bean
    @Scope("cucumber-glue")
    public ScenarioInfo scenarioInfo(){
        return new ScenarioInfo();
    }

    @Bean
    @Scope("cucumber-glue")
    public CsvStepdefImpl csvStepdef(){
        return new CsvStepdefImpl(csvPersonReader(), csvWriter(), csvHttpService(), scenarioInfo());
    }

    @Bean
    @Scope("cucumber-glue")
    public CsvHttpService csvHttpService(){
        return new CsvHttpService(requestSpecification());
    }

    @Bean
    @Scope("cucumber-glue")
    public RequestSpecification requestSpecification(){
        RequestSpecification requestSpecification = RestAssured.given();
        requestSpecification.baseUri(basePath);
        return requestSpecification;
    }

}
