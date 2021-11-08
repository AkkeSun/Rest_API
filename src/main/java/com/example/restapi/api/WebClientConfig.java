package com.example.restapi.api;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Bean
    public WebClient webClient(){
        String BASE_URL = "http://localhost:9099/api/events";
        return WebClient.builder()
                .baseUrl(BASE_URL)
                .build();
    }

}
