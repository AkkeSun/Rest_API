package com.example.restapi.security.config;

import com.example.restapi.security.owner.OwnerProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private OwnerProvider ownerProvider;

    @Override
    //하위 정적자원들에 대해 보안구성에서 제외한다.
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().mvcMatchers("/favicon.ico");
        web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations()); //기본 설정된 모든 정적파일들
    }

    @Override
    //요청의 대한 설정
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/oauth/**", "/oauth2/callback", "/oauth/authorize").permitAll()
          //      .mvcMatchers(HttpMethod.GET, "/api/**").permitAll()
          //      .mvcMatchers(HttpMethod.POST, "/api/**").hasAnyRole("ADMIN")
          //      .mvcMatchers(HttpMethod.PUT, "/api/**").hasAnyRole("ADMIN")
                .mvcMatchers(HttpMethod.POST, "/api/**").permitAll()
                .anyRequest().authenticated()
                .and().formLogin().permitAll();

        http.csrf().disable();
    }


    @Override
    // Owner에 대한 정보를 DB를 통해 관리
    public void configure(AuthenticationManagerBuilder authenticationMgr) throws Exception {
        authenticationMgr.authenticationProvider(ownerProvider);
    }
}