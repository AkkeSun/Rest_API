package com.example.restapi.REST_API.events;

import com.example.restapi.REST_API.common.BaseControllerTest;
import org.junit.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class EventTest extends BaseControllerTest {

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