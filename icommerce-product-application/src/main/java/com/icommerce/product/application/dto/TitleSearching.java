package com.icommerce.product.application.dto;

import org.apache.commons.lang.StringUtils;
import org.springframework.data.mongodb.core.query.Criteria;

public class TitleSearching extends ProductSearchCriteria {

    @Override
    public boolean shouldUsethisCriteria(SearchCriteria searchCriteria) {
        return StringUtils.isNotBlank(searchCriteria.getTitle());
    }

    @Override
    public Criteria buildCriteriaLikeCondition(SearchCriteria criteria) {
        return Criteria.where("title").regex(criteria.getTitle());
    }
}
