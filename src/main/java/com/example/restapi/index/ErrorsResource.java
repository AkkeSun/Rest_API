package com.example.restapi.index;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.validation.Errors;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

public class ErrorsResource extends Resource<Errors> {
    public ErrorsResource(Errors content, Link... links) {
        super(content, links);

        //method의 path로 이동하는 경우 이렇게 처리
        add(linkTo(methodOn(IndexController.class).index()).withRel("index"));
    }
}
