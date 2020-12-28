package com.icommerce.product.bootstrap.security.oauth2;

import com.icommerce.product.application.service.UserService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.Collection;

public class JwtGrantedAuthorityConverter implements Converter<Jwt, Collection<GrantedAuthority>> {

    private final UserService userService;

    public JwtGrantedAuthorityConverter(UserService userService) {
        this.userService = userService;
    }

    @Override
    public Collection<GrantedAuthority> convert(Jwt jwt) {
        return userService.extractAuthorityFromClaims(jwt.getClaims());
    }
}
