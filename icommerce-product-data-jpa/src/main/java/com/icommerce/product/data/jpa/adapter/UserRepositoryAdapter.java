package com.icommerce.product.data.jpa.adapter;


import com.icommerce.product.data.jpa.mapper.UserJpaMapper;
import com.icommerce.product.data.jpa.repository.UserJpaRepository;
import com.icommerce.product.domain.entity.User;
import com.icommerce.product.domain.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
public class UserRepositoryAdapter implements UserRepository {

    private final UserJpaRepository userJpaRepository;
    private final UserJpaMapper userJpaMapper;
    @Override
    public Optional<User> findOneByLogin(String login) {
        return userJpaRepository.findOneByLogin(login).map(userJpaMapper::map);
    }

    public List<User> findAllByLoginNot(Pageable pageable, String login) {
        return userJpaRepository.findAllByLoginNot(pageable,login)
            .stream().map(userJpaMapper::map)
            .collect(Collectors.toList());

    }
}
