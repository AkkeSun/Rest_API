package com.example.restapi.REST_API.events;

import lombok.*;

import java.time.LocalDateTime;

@Builder @Data @NoArgsConstructor @AllArgsConstructor
public class EventDto {
    private String name; //이벤트 네임
    private String description; // 설명
    private LocalDateTime beginEnrollmentDateTime; //등록 시작일시
    private LocalDateTime closeEnrollmentDateTime; //종료일시
    private LocalDateTime beginEventDateTime; //이벤트 시작일시
    private LocalDateTime endEventDateTime;   //이벤트 종료일시
    private String location; // (optional) 이벤트 위치 이게 없으면 온라인 모임
    private int basePrice; // (optional)
    private int maxPrice; // (optional)
    private int limitOfEnrollment; //등록한도
}
