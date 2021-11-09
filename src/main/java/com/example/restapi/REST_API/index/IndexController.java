package com.example.restapi.REST_API.index;

import com.example.restapi.REST_API.events.EventController;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@RestController
public class IndexController {

    @GetMapping("/api")
    public ResourceSupport index(){

        // href 처리하기
        var index = new ResourceSupport();
        index.add(linkTo(EventController.class).withRel("event"));
        return index;
    }
}
