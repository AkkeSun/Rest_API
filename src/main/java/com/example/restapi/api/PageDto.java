package com.example.restapi.api;

import lombok.Data;

@Data
public class PageDto {

    private int size;
    private int totalElements;
    private int totalPages;
    private int number;
    private String sort;

}
