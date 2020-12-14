package com.icommerce.product.application.service;

import com.icommerce.product.application.dto.ProductDTO;
import com.icommerce.product.application.dto.ProductSearchCriteria;
import com.icommerce.product.application.dto.SearchCriteria;
import com.icommerce.product.application.vo.AuthoritiesConstants;
import com.icommerce.product.domain.entity.ActionId;
import com.icommerce.product.domain.entity.Product;
import com.icommerce.product.domain.event.*;
import com.icommerce.product.domain.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final List<ProductSearchCriteria> criteriaList;
    private final MongoTemplate mongoTemplate;
    private final UserActivitiesHistoricalEventPublisher userActivitiesHistoricalEventPublisher;
    private final ProductChangelogHistoricalEventPublisher productChangelogHistoricalEventPublisher;

    @Transactional
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    public Product save(ProductDTO productDTO) {
        Product product = Product.builder()
            .brand(productDTO.getBrand())
            .title(productDTO.getTitle())
            .price(productDTO.getPrice())
            .build();
        product = productRepository.save(product);
        String userId = SecurityUtils.getCurrentUserLogin().orElse("");
        ProductChangelogHistoricalEvent productChangelogHistoricalEvent = ProductChangelogHistoricalEvent.build(
                product, userId
        );
        productChangelogHistoricalEventPublisher.publish(productChangelogHistoricalEvent);
        UserActivitiesHistoricalEvent userActivitiesHistoricalEvent = UserActivitiesHistoricalEvent.builder()
                .actionDate(LocalDateTime.now())
                .userId(userId)
                .actionId(ActionId.CREATE_NEW_PRODUCT.name())
                .actionDescription(productDTO.toString()).build();
        userActivitiesHistoricalEventPublisher.publish(userActivitiesHistoricalEvent);
        return product;
    }

    @Transactional
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    public Product update(ProductDTO productDTO) {
        try {
            Product existing = productRepository.findById(productDTO.getId())
                .orElseThrow(() -> new RuntimeException("Could not found product with id " + productDTO.getId()));
            String userId = SecurityUtils.getCurrentUserLogin().orElse("");
            ProductChangelogHistoricalEvent productChangelogHistoricalEvent = ProductChangelogHistoricalEvent.build(
                    existing, userId
            );
            productChangelogHistoricalEventPublisher.publish(productChangelogHistoricalEvent); // keep the old version of product
            existing = existing.toBuilder()
                .brand(productDTO.getBrand())
                .title(productDTO.getTitle())
                .price(productDTO.getPrice())
                .build();
            existing = productRepository.update(existing);
            return existing;
        } finally {
            UserActivitiesHistoricalEvent userActivitiesHistoricalEvent = UserActivitiesHistoricalEvent.builder()
                    .actionDate(LocalDateTime.now())
                    .userId(SecurityUtils.getCurrentUserLogin().orElse(""))
                    .actionId(ActionId.UPDATE_PRODUCT.name())
                    .actionDescription(productDTO.toString()).build();
            userActivitiesHistoricalEventPublisher.publish(userActivitiesHistoricalEvent);
        }
    }

    @Transactional
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    public void deleteBy(String id) {
        try {
            productRepository.deleteById(id);
        } finally {
            UserActivitiesHistoricalEvent userActivitiesHistoricalEvent = UserActivitiesHistoricalEvent.builder()
                    .actionDate(LocalDateTime.now())
                    .userId(SecurityUtils.getCurrentUserLogin().orElse(""))
                    .actionId(ActionId.DELETE_PRODUCT.name())
                    .actionDescription("id=" + id).build();
            userActivitiesHistoricalEventPublisher.publish(userActivitiesHistoricalEvent);
        }
    }

    @Transactional(readOnly = true)
    public Optional<Product> findDetailBy(String id) {
        try {
            return productRepository.findById(id);
        } finally {
            UserActivitiesHistoricalEvent userActivitiesHistoricalEvent = UserActivitiesHistoricalEvent.builder()
                    .actionDate(LocalDateTime.now())
                    .userId(SecurityUtils.getCurrentUserLogin().orElse(""))
                    .actionId(ActionId.GET_PRODUCT.name())
                    .actionDescription("id=" + id).build();
            userActivitiesHistoricalEventPublisher.publish(userActivitiesHistoricalEvent);
        }
    }

    @Transactional(readOnly = true)
    public Page<Product> search(SearchCriteria searchCriteria, Pageable pageable) {
        try {
            Query query = new Query();
            List<Criteria> criteriaCondition = new ArrayList<>();
            criteriaList.forEach(criteria -> {
                if (criteria.shouldUsethisCriteria(searchCriteria)) {
                    criteriaCondition.add(criteria.buildCriteriaLikeCondition(searchCriteria));
                }
            });
            if (!CollectionUtils.isEmpty(criteriaCondition)) {
                query.addCriteria(new Criteria().andOperator(criteriaCondition.toArray(new Criteria[criteriaCondition.size()])));
            }
            query.with(pageable);
            return new PageImpl<>(mongoTemplate.find(query, Product.class));
        } finally {
            UserActivitiesHistoricalEvent userActivitiesHistoricalEvent = UserActivitiesHistoricalEvent.builder()
                    .actionDate(LocalDateTime.now())
                    .userId(SecurityUtils.getCurrentUserLogin().orElse(""))
                    .actionId(ActionId.SEARCH_PRODUCTS.name())
                    .actionDescription(searchCriteria == null ?
                            "" : searchCriteria.toString()).build();
            userActivitiesHistoricalEventPublisher.publish(userActivitiesHistoricalEvent);
        }
    }
}
