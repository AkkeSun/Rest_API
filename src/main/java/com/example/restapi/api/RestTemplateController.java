package com.example.restapi.api;

import com.example.restapi.events.Event;
import com.example.restapi.events.EventDto;
import com.example.restapi.events.EventValidator;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@RestController // 이걸로 하지말자!!!!!
@RequestMapping("/restTemplate")
public class RestTemplateController {

    @Autowired
    EventValidator validator;

    private String URL = "http://localhost:9099/api/events";
    private RestTemplate restTemplate = new RestTemplate();

    /**
     * CREATE
     */
    @PostMapping("/events")
    public Event createEvent(@RequestBody EventDto eventdto, Errors errors) {

        // 유효성 검사
        validator.validate(eventdto, errors);
        if(errors.hasErrors()){

            errors.getAllErrors().forEach( e -> {
                System.out.println("===Error code===");
                System.out.println(e.getCode());
                System.out.println(e.getDefaultMessage());
            });
            return null;

        } else {

            // 데이터 저장
            HttpEntity<EventDto> entity = new HttpEntity<>(eventdto);
            ResponseEntity<Map> resultMap = restTemplate.exchange(URL, HttpMethod.POST, entity, Map.class);

            // self Link로 저장한 데이터 가져와서 리턴
            String selfLink = getSelfUrl(resultMap);
            // 객채만 리턴할거면 리턴타입을 ResponseEntity<Map>이 아닌 ResponseEntity<Object>로 주고 getBody()
            return restTemplate.exchange(selfLink, HttpMethod.GET, null, Event.class).getBody();
        }
    }



    /**
     * READ
     */
    @GetMapping("/events")
    public Map<String, Object> selectAll() throws IOException {
        ResponseEntity<Map> resultMap =
                restTemplate.exchange(URL, HttpMethod.GET,null, Map.class);

        HttpStatus status = resultMap.getStatusCode();
        System.out.println(status);

        ArrayList<Event> objectlist = getObjectList(resultMap);
        PageDto page = getPage(resultMap);
        return Map.of("objectlist", objectlist, "page", page);
    }


    @GetMapping("/events/{id}")
    public Event selectOne(@PathVariable String id) throws JsonProcessingException {
        return restTemplate.exchange(URL+"/"+id, HttpMethod.GET, null, Event.class).getBody();
    }



    /**
     * UPDATE
     */
    @PutMapping("/events/{id}")
    public Event updateEvent(@PathVariable String id, @RequestBody EventDto eventdto, Errors errors) {

        // 유효성 검사
        validator.validate(eventdto, errors);
        if(errors.hasErrors()){

            errors.getAllErrors().forEach( e -> {
                System.out.println("===Error code===");
                System.out.println(e.getCode());
                System.out.println(e.getDefaultMessage());
            });
            return null;

        } else {

            HttpEntity <?> entity = new HttpEntity<>(eventdto);
            ResponseEntity<Map> resultMap =
                    restTemplate.exchange(URL+"/"+id, HttpMethod.PUT, entity, Map.class);

            // self Link로 저장한 데이터 가져와서 리턴
            String selfLink = getSelfUrl(resultMap);
            return restTemplate.exchange(selfLink, HttpMethod.GET, null, Event.class).getBody();
        }
    }




    // ObjectList 가져오기
    private static ArrayList<Event> getObjectList(ResponseEntity<Map> resultMap){

        LinkedHashMap lm = (LinkedHashMap) resultMap.getBody().get("_embedded");
        ArrayList<Map> list = (ArrayList<Map>) lm.get("eventList");

        ArrayList<Event> returnList = new ArrayList<>();
        ModelMapper modelMapper = new ModelMapper();

        for(int i=0; i<list.size(); i++){
            Event e = modelMapper.map(list.get(0), Event.class); // ModelMapper 사용
            returnList.add(e);
        }
        return returnList;
    }

    // Page Object 가져오기
    private static PageDto getPage(ResponseEntity<Map> resultMap){

        LinkedHashMap lm = (LinkedHashMap) resultMap.getBody().get("page");
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(lm, PageDto.class);
    }


    // self Link 가져오기
    private static String getSelfUrl(ResponseEntity<Map> resultMap){
        LinkedHashMap lm = (LinkedHashMap) resultMap.getBody().get("_links");
        HashMap map = (HashMap) lm.get("self");
        return (String) map.get("href");
    }
}
