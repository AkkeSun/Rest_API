package com.example.restapi.events;

import com.example.restapi.accounts.Account;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Builder
@Getter @Setter @ToString @EqualsAndHashCode(of="id")
@NoArgsConstructor @AllArgsConstructor
@Entity
public class Event {

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

    @ManyToOne
    private Account manager;


    // 기본값을 가지거나 계산이 필요한 컬럼들
    @Id @GeneratedValue
    private Integer id; // 추가 식별자
    private boolean offline; // 오프라인 여부
    private boolean free; //유료 여부
    @Enumerated(EnumType.STRING)
    private EventStatus eventStatus = EventStatus.DRAFT; // 이벤트 상태


    public void update(){
        // Update free
        this.free = this.basePrice == 0 && this.maxPrice == 0 ? true : false;
        // Update offline
        this.offline = this.location == null || this.location.isBlank() ? false : true;
    }
}
