package com.icommerce.product.domain.repository;

import com.icommerce.product.domain.entity.User;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface UserRepository {

    String USERS_BY_LOGIN_CACHE = "usersByLogin";

    String USERS_BY_EMAIL_CACHE = "usersByEmail";

    Optional<User> findOneByLogin(String login);

    List<User> findAllByLoginNot(Pageable pageable, String login);
}
