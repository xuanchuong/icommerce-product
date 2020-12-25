package com.icommerce.product.application.dto;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.util.StringUtils;

public class TitleSearching extends ProductSearchCriteria {

    @Override
    public boolean shouldUsethisCriteria(SearchCriteria searchCriteria) {
        return !StringUtils.isEmpty(searchCriteria.getTitle());
    }

    @Override
    public Criteria buildCriteriaLikeCondition(SearchCriteria criteria) {
        return Criteria.where("title").regex(criteria.getTitle());
    }
}
