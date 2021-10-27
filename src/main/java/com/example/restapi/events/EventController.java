package com.example.restapi.events;

import com.example.restapi.index.ErrorsResource;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import java.net.URI;
import java.net.URISyntaxException;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@Controller
@RequestMapping(value = "/api/events", produces = MediaTypes.HAL_JSON_UTF8_VALUE)
public class EventController {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    EventValidator eventValidator;


    /***
     * Create API
     ***/

    @PostMapping
    public ResponseEntity createEvent(@RequestBody EventDto eventDto, Errors errors) {

        eventValidator.validate(eventDto, errors);
        if (errors.hasErrors())
            return ResponseEntity.badRequest().body(new ErrorsResource(errors));

        Event event = modelMapper.map(eventDto, Event.class); // Mapper 사용해서 데이터입력
        event.update();
        Event newEvent = eventRepository.save(event);

        URI createdUri = linkTo(EventController.class).slash(newEvent.getId()).toUri();

        EventResource resource = new EventResource(newEvent); // event Data -> EventResource
        return ResponseEntity.created(createdUri).body(resource);
    }

    /***
     * Select API
     ***/
    @GetMapping
    public ResponseEntity eventList(Pageable pageable, PagedResourcesAssembler<Event> assembler) throws URISyntaxException {
        Page<Event> page = eventRepository.findAll(pageable);
        // 데이터와 페이징 정보가 자동으로 들어간다
        var pagedResources = assembler.toResource(page, e -> new EventResource(e));
        return ResponseEntity.ok().body(pagedResources);
    }
}