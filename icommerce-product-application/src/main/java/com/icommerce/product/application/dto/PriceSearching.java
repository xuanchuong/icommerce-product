package com.icommerce.product.application.dto;

import org.springframework.data.mongodb.core.query.Criteria;

public class PriceSearching extends ProductSearchCriteria {

    @Override
    public boolean shouldUsethisCriteria(SearchCriteria searchCriteria) {
        return searchCriteria.getPriceGreaterThan() != null || searchCriteria.getPriceLesserThan() != null;
    }

    @Override
    public Criteria buildCriteriaLikeCondition(SearchCriteria searchCriteria) {
        Criteria criteria = Criteria.where("price");
        if (searchCriteria.getPriceLesserThan() != null) {
            criteria.lt(searchCriteria.getPriceLesserThan());
        }
        if (searchCriteria.getPriceGreaterThan() != null) {
            criteria.gt(searchCriteria.getPriceGreaterThan());
        }
        return criteria;
    }
}
