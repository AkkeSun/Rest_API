package com.example.restapi.accounts;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class AccountRunner implements ApplicationRunner {

    @Autowired
    AccountRepository repository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Account admin = Account.builder()
                .email("akkessun@gmail.com")
                .password("1234")
                .roles(Set.of(AccountRole.ADMIN, AccountRole.USER))
                .build();

        Account user = Account.builder()
                .email("ism2259@naver.com")
                .password("1234")
                .roles(Set.of(AccountRole.USER))
                .build();

        repository.save(admin);
        repository.save(user);
    }
}
