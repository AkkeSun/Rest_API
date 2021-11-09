package com.example.restapi.REST_API.userAPI;

import lombok.Data;

@Data
public class PageDto {

    private int size;
    private int totalElements;
    private int totalPages;
    private int number;
    private String sort;

}
