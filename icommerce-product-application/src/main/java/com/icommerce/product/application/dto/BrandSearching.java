package com.icommerce.product.application.dto;

import com.icommerce.product.application.dto.ProductSearchCriteria;
import com.icommerce.product.application.dto.SearchCriteria;
import org.apache.commons.lang.StringUtils;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

public class BrandSearching extends ProductSearchCriteria {

    @Override
    public boolean shouldUsethisCriteria(SearchCriteria searchCriteria) {
        return StringUtils.isNotBlank(searchCriteria.getBrand());
    }

    @Override
    public Criteria buildCriteriaLikeCondition(SearchCriteria criteria) {
        return Criteria.where("brand").regex(criteria.getBrand());
    }
}
