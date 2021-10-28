package com.example.restapi.configs;

import com.example.restapi.accounts.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;

/**
 *  WebSecurityConfigurerAdapter 클래스를 상속 받는 순간
 *  스프링부트가 제공하는 시큐리티 설정은 사용하지 않고
 *  모든 요청은 인증을 필요로 하게됨
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    AccountService accountService;

    @Autowired
    PasswordEncoder passwordEncoder;

    /***
     * OAuth 토큰 저장 (InMemoryTokenStore를 사용)
     */
    @Bean
    public TokenStore tokenStore(){
        return new InMemoryTokenStore();
    }

    /***
     * 다른 AuthorizationServer나 ResourceServer가 참조할 수 있도록
     * 오버라이딩 해서 빈으로 등록
     */
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    /***
     *  AuthenticationManager 재정의
     *  내가 만든 accountService, passwordEncorder를 사용하겠다
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(accountService)
                .passwordEncoder(passwordEncoder);
    }

    /***
     * HTTP를 적용하기 전에 시큐리티 필터를 적용할지 말지를 먼저 결정
     * 서버가 일을 조금이라도 일을 덜하게 하기 위해 정적인 리소스들은 웹 필터로 걸러주는 것을 권장
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().mvcMatchers("/docs/**");
        web.ignoring().mvcMatchers("/favicon.ico");
        // 스프링부트가 제공하는 기본 static 리소스
        web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }


    /***
     * 허용되는 HTTP Port 설정
     * anonymous() = 익명 사용자 허용
     * authenticated() = 인증 필요
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .anonymous() //익명사용자 허용
                .and()
                .formLogin() //폼 인증 사용
                .and()
                .authorizeRequests() //허용할 요청
                .mvcMatchers(HttpMethod.GET, "/api/**").anonymous() // /api/** 경로는 익명사용자에게 허용
                .anyRequest().authenticated(); // 나머지는 인증이 필요
    }
}