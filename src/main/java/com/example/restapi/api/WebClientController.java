package com.example.restapi.api;

import com.example.restapi.events.Event;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.*;

@RestController
@RequestMapping("/webclient")
public class WebClientController {

    // filter token 확인
    @Autowired
    WebClient webClient;

    @Autowired
    ModelMapper modelMapper;

    private String accessToken;

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


    public PageDto getPage(Map resultMap){
        LinkedHashMap lm = (LinkedHashMap) resultMap.get("page");
        return modelMapper.map(lm, PageDto.class);
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

}
