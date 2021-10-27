package com.example.restapi.test;

import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.net.URISyntaxException;

@RestController
@RequestMapping(value = "/api/test", produces = MediaTypes.HAL_JSON_VALUE)
public class TestController {

    @GetMapping
    public ResponseEntity createEvent() throws URISyntaxException {
        HttpHeaders headers = new HttpHeaders();
        URI myUri = new URI("/test");
        headers.setLocation(myUri);
        return new ResponseEntity(headers, HttpStatus.FOUND);
    }
}