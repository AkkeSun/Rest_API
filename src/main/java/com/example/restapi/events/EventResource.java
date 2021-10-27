package com.example.restapi.events;
import com.example.restapi.events.Event;
import com.example.restapi.events.EventController;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

public class EventResource extends Resource<Event> {

    public EventResource(Event event, Link... links) {
        super(event, links);

        // http://localhost:8090/api/event/1
        add(linkTo(EventController.class).slash(event.getId()).withSelfRel()); // _self
        // new Link("http://localhost:8090/api/event/test")
        add(linkTo(EventController.class).slash("test").withRel("query-events"));
        add(linkTo(EventController.class).slash(event.getId()).withRel("update-event"));
    }
}