package com.example.restapi.test;

import com.example.restapi.events.Event;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.net.URISyntaxException;

@RestController
@RequestMapping(value = "/api/test", produces = MediaTypes.HAL_JSON_VALUE)
public class TestController {

    @GetMapping
    public ResponseEntity createEvent() throws URISyntaxException {

        URI myUri = new URI("http://localhost:9091/test");
        return ResponseEntity.ok(myUri);
    }
}