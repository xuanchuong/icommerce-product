package com.icommerce.product.domain.event;

public interface ProductChangelogHistoricalEventPublisher {

    void publish(ProductChangelogHistoricalEvent event);
}
