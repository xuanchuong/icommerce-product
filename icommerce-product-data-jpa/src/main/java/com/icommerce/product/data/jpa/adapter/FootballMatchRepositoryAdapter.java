package com.icommerce.product.data.jpa.adapter;

import com.icommerce.product.data.jpa.entity.FootballMatchJpa;
import com.icommerce.product.data.jpa.mapper.FootballMatchMapper;
import com.icommerce.product.data.jpa.repository.FootballMatchJpaRepository;
import com.icommerce.product.domain.entity.FootballMatch;
import com.icommerce.product.domain.repository.FootballMatchRepository;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
public class FootballMatchRepositoryAdapter implements FootballMatchRepository {

    private final FootballMatchJpaRepository footballMatchJpaRepository;
    private final FootballMatchMapper footballMatchMapper;

    @Override
    public FootballMatch save(FootballMatch footballMatch) {
        FootballMatchJpa jpaFootballMatchJpa = footballMatchMapper.map(footballMatch);
        return footballMatchMapper.map(footballMatchJpaRepository.save(jpaFootballMatchJpa));
    }

    @Override
    public void deleteById(Long id) {
        footballMatchJpaRepository.deleteById(id);
    }

    @Override
    public Optional<FootballMatch> findById(Long id) {
        return footballMatchJpaRepository.findById(id).map(footballMatchMapper::map);
    }

    @Override
    public List<FootballMatch> findAll() {
        return footballMatchJpaRepository.findAll().stream().map(footballMatchMapper::map)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteAll() {
        footballMatchJpaRepository.deleteAll();
    }
}
