package com.icommerce.product.application.service;

import org.springframework.security.core.GrantedAuthority;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface UserService {

    Optional<String> getCurrentUserLogin();

    boolean isAuthenticated();

    boolean isCurrentUserInRole(String authority);

    List<GrantedAuthority> extractAuthorityFromClaims(Map<String, Object> claims);
}
