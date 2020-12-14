package com.icommerce.product.domain.event;

public interface UserActivitiesHistoricalEventPublisher {
    void publish(UserActivitiesHistoricalEvent event);
}
