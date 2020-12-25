package com.icommerce.product.bootstrap.security;

import com.icommerce.product.application.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class SpringSecurityAuditorAware implements AuditorAware<String> {

    private static final String SYSTEM_ACCOUNT = "system";
    @Autowired
    private UserService userService;

    @Override
    public Optional<String> getCurrentAuditor() {
        return Optional.of(userService.getCurrentUserLogin().orElse(SYSTEM_ACCOUNT));
    }
}
