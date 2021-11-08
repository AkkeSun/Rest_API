package com.example.restapi.api;

import com.example.restapi.events.Event;
import com.example.restapi.events.EventDto;
import com.example.restapi.events.EventValidator;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.*;

@RestController // 이걸로 하지말자!!!!!
@RequestMapping("/webclient")
public class WebClientController {

    @Autowired
    WebClient webClient;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    EventValidator validator;

    private String accessToken;


    /**
     * CREATE
     */
    @PostMapping("/events")
    public Event createEvent(@RequestBody EventDto eventDto, Errors errors){

        // 유효성 검사
        validator.validate(eventDto, errors);
        if(errors.hasErrors()){

            errors.getAllErrors().forEach( e -> {
                System.out.println("===Error code===");
                System.out.println(e.getCode());
                System.out.println(e.getDefaultMessage());
            });
            return null;

        } else {
            Event event = webClient
                    .post()
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
             //     .body(BodyInserters.fromFormData("data1", "aa").with("data2", "bb"))
                    .body(Mono.just(eventDto), EventDto.class)
                    .retrieve()
                    .bodyToMono(Event.class)
                    .block();
            return event;
        }
    }

    /**
     * READ
     */
    @GetMapping("/events")
    public Map<String, Object> selectAll() {

        Mono<Map> mono = webClient
                .get() // method type
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken) // header에 access token을 실어보낼 수 있음
                .retrieve()
                .bodyToMono(Map.class); // return type

        /* 비동기 데이터 처리가 필요한 경우
        mono.subscribe( s -> {System.out.println(s); } );
         */

        // 동기처리 (blocking)
        Map<String, Object> resultMap = mono.block();
        List<Event> objectList = getObjectList(resultMap);
        PageDto page = getPage(resultMap);

        return Map.of("objectList", objectList,"page", page);
    }

    @GetMapping("/events/{id}")
    public Event selectOne(@PathVariable String id) {

       Event event = webClient
                        .get()
                        .uri("/{id}", id)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                        .retrieve()
                        .bodyToMono(Event.class)
                        .block();
        return event;
    }


    /**
     * UPDATE
     */
    @PutMapping("/events/{id}")
    public Event upateEvent(@PathVariable String id, @RequestBody EventDto eventDto, Errors errors){

        // 유효성 검사
        validator.validate(eventDto, errors);
        if(errors.hasErrors()){

            errors.getAllErrors().forEach( e -> {
                System.out.println("===Error code===");
                System.out.println(e.getCode());
                System.out.println(e.getDefaultMessage());
            });
            return null;

        } else {
            Event event = webClient
                    .put()
                    .uri("/{id}", id)
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                    .body(Mono.just(eventDto), EventDto.class)
                    .retrieve()
                    .bodyToMono(Event.class)
                    .block();
            return event;
        }
    }






    // Object List 구하기
    public List<Event> getObjectList(Map resultMap){

        ArrayList<Event> returnList = new ArrayList<>();
        LinkedHashMap lm = (LinkedHashMap) resultMap.get("_embedded");
        ArrayList<Map> list = (ArrayList<Map>) lm.get("eventList");

        for (int i = 0; i < list.size(); i++) {
            Event e = modelMapper.map(list.get(i), Event.class); // ModelMapper 사용
            returnList.add(e);
        }
        return returnList;
    }

    // 페이징 정보
    public PageDto getPage(Map resultMap){
        LinkedHashMap lm = (LinkedHashMap) resultMap.get("page");
        return modelMapper.map(lm, PageDto.class);
    }
}
