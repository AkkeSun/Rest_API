package com.example.restapi.events;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.net.URI;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Controller
@RequestMapping(value = "/api/events", produces = MediaTypes.HAL_JSON_VALUE)
public class EventController {

    @Autowired
    private EventRepository eventRepository;

    @PostMapping
    public ResponseEntity createEvent(@RequestBody Event event) {

        Event newEvent = eventRepository.save(event);

        // EventController의 id에 해당하는 링크를 만들고 링크를 URI로 변환
        URI createdUri = linkTo(EventController.class).slash(newEvent.getId()).toUri();

        // createdUri 헤더를 가지고 201 응답을 만듬
        return ResponseEntity.created(createdUri).body(event);
    }

}