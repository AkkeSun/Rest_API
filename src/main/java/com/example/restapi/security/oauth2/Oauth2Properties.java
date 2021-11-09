package com.example.restapi.security.oauth2;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


/**
 * properties로 변경이 잦은 데이터를 모아놓았다
 */
@ConfigurationProperties("oauth2")
@Component
@Data
public class Oauth2Properties {

    private String redirect_uri;
    private String credentials;
    private String tokenresponse;

}
