package com.icommerce.product.message.producer.configuration;

import com.icommerce.product.domain.event.ProductChangelogHistoricalEventPublisher;
import com.icommerce.product.message.producer.adapter.HistoricalEventChannel;
import com.icommerce.product.domain.event.UserActivitiesHistoricalEventPublisher;
import com.icommerce.product.message.producer.adapter.ProductChangelogHistoricalEventPublisherAdapter;
import com.icommerce.product.message.producer.adapter.UserActivitiesHistoricalEventPublisherAdapter;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.context.annotation.Bean;

@Configurable
@EnableBinding(value = {HistoricalEventChannel.class})
public class MessageProducerConfiguration {

    @Bean
    public UserActivitiesHistoricalEventPublisher userActivitiesHistoricalEvent(HistoricalEventChannel historicalEventChannel) {
        return new UserActivitiesHistoricalEventPublisherAdapter(historicalEventChannel);
    }

    @Bean
    public ProductChangelogHistoricalEventPublisher productChangelogHistoricalEventPublisher(HistoricalEventChannel historicalEventChannel) {
        return new ProductChangelogHistoricalEventPublisherAdapter(historicalEventChannel);
    }
}
