package com.example.restapi.REST_API.events;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

public class EventResource extends Resource<Event> {

    public EventResource(Event event, Link... links) {
        super(event, links);

        // new Link("http://localhost:8090/api/event/1")
        add(linkTo(EventController.class).slash(event.getId()).withSelfRel()); // _self
    }
}