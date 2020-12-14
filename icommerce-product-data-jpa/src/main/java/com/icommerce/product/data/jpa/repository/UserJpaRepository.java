package com.icommerce.product.data.jpa.repository;

import com.icommerce.product.data.jpa.entity.UserJpa;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserJpaRepository extends MongoRepository<UserJpa, String> {

    String USERS_BY_LOGIN_CACHE = "usersByLogin";

    @Cacheable(cacheNames = USERS_BY_LOGIN_CACHE)
    Optional<UserJpa> findOneByLogin(String login);

    Page<UserJpa> findAllByLoginNot(Pageable pageable, String login);
}
