package com.example.restapi.events;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
public class EventTest {

    @Test
    public void builder(){
        Event event = new Event().builder()
                .name("sun")
                .description("test")
                .build();
        assertThat(event).isNotNull();
    }


    @Test
    public void builder2(){
        Event event = new Event();
        event.setName("test");
        event.setDescription("test");
        assertThat(event).isNotNull();
        assertThat(event.getDescription()).isEqualTo("test");
    }

}