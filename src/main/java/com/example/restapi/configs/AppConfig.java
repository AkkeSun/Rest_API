package com.example.restapi.configs;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class AppConfig {

    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }

    /***
     * 스프링 시큐리티 최신 버전에 추가된 PasswordEncoder
     * 다양한 인코딩 타입을 지원하며 인코딩된 어떠한 방식으로 인코딩된 건지 알 수 있도록
     * 패스워드 앞에 prefix를 붙여줌
     */

    @Bean
    public PasswordEncoder passwodEncorder(){
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

}