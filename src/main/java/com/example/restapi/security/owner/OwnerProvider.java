package com.example.restapi.security.owner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * Owner 정보를 조회한 후 맞다면 회원정보 리턴
 */
@Component
public class OwnerProvider implements AuthenticationProvider {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private OwnerService ownerService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        String name = authentication.getName();
        String password = authentication.getCredentials().toString();

        Owner owner = ownerService.findByUsername(name)
                .orElseThrow(() -> new UsernameNotFoundException("USER IS NOT EXISTS"));

        if(!passwordEncoder.matches(password, owner.getPassword()))
            throw new BadCredentialsException("PASSWORD IS NOT VAILD");

        return new UsernamePasswordAuthenticationToken(name, password, owner.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}

