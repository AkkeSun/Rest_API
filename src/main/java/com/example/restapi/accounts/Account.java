package com.example.restapi.accounts;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@Builder
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
@EqualsAndHashCode(of="id")
public class Account {

    @Id @GeneratedValue
    private Integer id;
    private String email;
    private String password;

    // 값을 하나 이상 저장할 때 사용
    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    private Set<AccountRole> roles;
}
