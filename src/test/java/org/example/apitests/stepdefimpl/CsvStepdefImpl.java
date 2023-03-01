package org.example.apitests.stepdefimpl;

import io.restassured.RestAssured;
import io.restassured.specification.ResponseSpecification;
import org.example.apitests.context.ScenarioInfo;
import org.example.apitests.http.CsvHttpService;
import org.example.models.Person;
import org.example.csv.CsvWriter;
import org.example.csv.CsvReader;
import org.example.csv.DataGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.io.IOException;
import java.util.List;

public class CsvStepdefImpl {

    private final CsvReader<Person> csvReader;
    private final CsvWriter<Person> csvWriter;
    private final CsvHttpService csvHttpService;
    private final ScenarioInfo scenarioInfo;

    @Value("${csv_filename}")
    private String csvFilename;

    @Autowired
    public CsvStepdefImpl(CsvReader<Person> csvReader,
                          CsvWriter<Person> csvWriter,
                          CsvHttpService csvHttpService, ScenarioInfo scenarioInfo) {
        this.csvReader = csvReader;
        this.csvWriter = csvWriter;
        this.csvHttpService = csvHttpService;
        this.scenarioInfo = scenarioInfo;
    }

    public void csvFileWithPersonsIsCreated(int count) throws IOException {
        List<Person> personList = DataGenerator.generatePersons(count);
        String csv = csvWriter.beansToCsv(personList, Person.class);
        csvWriter.writeToFile(csvFilename, csv);
    }

    public void eachCsvRowIsSentToEndpoint(){
        List<Person> personList = csvReader.csvToBean(csvFilename);
        personList.forEach(person -> scenarioInfo.responses.add(csvHttpService.postContacts(person)));
    }

    public void eachResponseCodeIs(int expectedStatusCode){
        ResponseSpecification responseSpecification = RestAssured.expect()
                .statusCode(expectedStatusCode);
        scenarioInfo.responses.forEach(response -> {
            response.then().spec(responseSpecification);
        });
    }

}
