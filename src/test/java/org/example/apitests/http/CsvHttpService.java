package org.example.apitests.http;

import io.restassured.RestAssured;
import io.restassured.mapper.ObjectMapperType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.example.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

public class CsvHttpService {

    final RequestSpecification requestSpecification;
    @Value("${api.endpoints.contacts}") String contactsEndpoint;

    @Autowired
    public CsvHttpService(RequestSpecification requestSpecification){
        this.requestSpecification = requestSpecification;
    }

    public Response postContacts(Person person){
        RequestSpecification request = RestAssured.given(requestSpecification);
        request.body(person, ObjectMapperType.GSON);
        return request.post(contactsEndpoint);
    }
}
