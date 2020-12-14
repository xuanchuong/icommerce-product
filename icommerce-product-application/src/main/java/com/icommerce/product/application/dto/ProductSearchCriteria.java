package com.icommerce.product.application.dto;

import org.springframework.data.mongodb.core.query.Criteria;

public abstract class ProductSearchCriteria {
    public abstract boolean shouldUsethisCriteria(SearchCriteria searchCriteria);
    public abstract Criteria buildCriteriaLikeCondition(SearchCriteria criteria);

}
