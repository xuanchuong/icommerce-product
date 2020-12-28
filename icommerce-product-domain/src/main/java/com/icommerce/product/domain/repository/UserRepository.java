package com.icommerce.product.domain.repository;

import com.icommerce.product.domain.entity.User;

import java.util.Optional;

public interface UserRepository {

    String USERS_BY_LOGIN_CACHE = "usersByLogin";

    String USERS_BY_EMAIL_CACHE = "usersByEmail";

    Optional<User> findOneByLogin(String login);
}
