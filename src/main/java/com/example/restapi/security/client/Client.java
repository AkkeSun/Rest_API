package com.example.restapi.security.client;

import com.example.restapi.security.oauth2.Oauth2Properties;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Builder @Getter @Setter @NoArgsConstructor @AllArgsConstructor
@Table(name = "oauth_client_details")
public class Client {

    @Id
    private String client_id;
    private String client_secret;
    private String scope = "read,write";
    private String authorized_grant_types = "authorization_code,refresh_token,password";
    private String web_server_redirect_uri = "http://localhost:9099/oauth2/callback";
    private String authorities = "ROLE_ADMIN,ROLE_USER";
    private Integer access_token_validity = 30000;
    private Integer refresh_token_validity = 50000;
    private String resource_ids = null;
    private String additional_information = null;
    private String autoapprove = "true"; // 로그인 후 승인여부 묻지 않기

}