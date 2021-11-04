package com.example.restapi.api;


import com.example.restapi.events.Event;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@RequestMapping("/resttemplate")
public class RestTemplateController {

    private String URL = "http://localhost:9099/api/events";
    private RestTemplate restTemplate = new RestTemplate();

    @GetMapping("/getAll")
    public void restTemplateTest1() throws IOException {

        ResponseEntity<Map> resultMap =
                restTemplate.exchange(URL, HttpMethod.GET,null, Map.class);
        ArrayList<Event> list = getEventList(resultMap);
        list.forEach(System.out::println);
    }

    @GetMapping("/getOne")
    public void restTemplateTest2() throws JsonProcessingException {

        RestTemplate restTemplate = new RestTemplate();
        String URL = "http://localhost:9099/api/events";

        ResponseEntity<Map> resultMap =
                restTemplate.exchange(URL+"/1", HttpMethod.GET,null, Map.class);
    }

    // 헤이티오스로 리턴된 REST-API 데이터 처리
    private static ArrayList<Map> getList(ResponseEntity<Map> resultMap){
        LinkedHashMap lm = (LinkedHashMap) resultMap.getBody().get("_embedded");
        ArrayList<Map> list = (ArrayList<Map>) lm.get("eventList");
        return list;
    }


    private static ArrayList<Event> getEventList(ResponseEntity<Map> resultMap){
        ArrayList<Event> returnList = new ArrayList<>();
        ModelMapper modelMapper = new ModelMapper();

        ArrayList<Map> list = getList(resultMap);
        for(int i=0; i<list.size(); i++){
            Event e = modelMapper.map(list.get(0), Event.class);
            returnList.add(e);
        }
        return returnList;
    }
}
