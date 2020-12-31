package com.icommerce.product.data.jpa.repository;

import com.icommerce.product.data.jpa.entity.FootballMatchJpa;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FootballMatchJpaRepository extends MongoRepository<FootballMatchJpa, Long> {
}
