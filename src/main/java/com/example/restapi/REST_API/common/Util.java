package com.example.restapi.REST_API.common;

import com.example.restapi.security.owner.Owner;
import com.example.restapi.security.owner.OwnerService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class Util {

    private static OwnerService ownerService;

    // 로그인 데이터 가져오기
    public static Owner LoginData(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getPrincipal().toString();
        return ownerService.findByUsername(username).get();
    }

}
