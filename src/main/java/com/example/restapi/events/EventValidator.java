package com.example.restapi.events;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class EventValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return EventDto.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        EventDto eventDto = (EventDto)target;

            if (eventDto.getName() == null )
                errors.rejectValue("name", "wrongName", "이름은 비워둘 수 없습니다");
        }
 }