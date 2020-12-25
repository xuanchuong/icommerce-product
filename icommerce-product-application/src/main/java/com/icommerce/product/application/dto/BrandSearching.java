package com.icommerce.product.application.dto;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.util.StringUtils;

public class BrandSearching extends ProductSearchCriteria {

    @Override
    public boolean shouldUsethisCriteria(SearchCriteria searchCriteria) {
        return !StringUtils.isEmpty(searchCriteria.getBrand());
    }

    @Override
    public Criteria buildCriteriaLikeCondition(SearchCriteria criteria) {
        return Criteria.where("brand").regex(criteria.getBrand());
    }
}
