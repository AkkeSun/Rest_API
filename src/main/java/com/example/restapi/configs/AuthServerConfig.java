package com.example.restapi.configs;

import com.example.restapi.accounts.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;

/**
 * OAuth2 인증서버 설정
 */
@Configuration
@EnableAuthorizationServer
public class AuthServerConfig extends AuthorizationServerConfigurerAdapter {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    AccountService accountService;

    @Autowired
    TokenStore tokenStore;

    /**
     * OAuth2 인증서버 자체의 보안정보를 설정
     * client_secret를 확인할 때 사용 client_secret도 전부 Password를 Encoding해서 관리
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security.passwordEncoder(passwordEncoder);
    }


    /**
     * 클라이언트에 대한 정보를 설정 1 (직접입력 방식)
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
                //클라이언트 아이디
                .withClient("myApp")
                // 클라이언트 시크릿
                .secret(this.passwordEncoder.encode("pass"))
                // 지원하는 grant_Type
                .authorizedGrantTypes("password", "refresh_token")
                // API 접근 시 접근범위 제한
                .scopes("read", "write")
                // 엑세스 토큰의 유효시간 10분
                .accessTokenValiditySeconds(10 * 60)
                // refresh_token의 유효시간
                .refreshTokenValiditySeconds(6 * 10 * 60);
    }


    /**
     * OAuth2 서버가 작동하기 위한 Endpoint에 대한 정보 설정
     * 등록된 유저정보를 확인
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.authenticationManager(authenticationManager)
                .userDetailsService(accountService)
                .tokenStore(tokenStore);
    }
}