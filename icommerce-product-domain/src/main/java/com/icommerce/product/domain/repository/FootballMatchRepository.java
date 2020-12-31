package com.icommerce.product.domain.repository;

import com.icommerce.product.domain.entity.FootballMatch;

import java.util.List;
import java.util.Optional;

public interface FootballMatchRepository {

    FootballMatch save(FootballMatch footballMatch);

    void deleteById(Long id);

    Optional<FootballMatch> findById(Long id);

    List<FootballMatch> findAll();

    void deleteAll();
}
