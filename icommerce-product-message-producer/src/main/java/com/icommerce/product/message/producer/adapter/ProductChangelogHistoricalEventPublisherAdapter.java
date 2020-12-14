package com.icommerce.product.message.producer.adapter;

import com.icommerce.product.domain.event.ProductChangelogHistoricalEvent;
import com.icommerce.product.domain.event.ProductChangelogHistoricalEventPublisher;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.util.MimeTypeUtils;

@AllArgsConstructor
public class ProductChangelogHistoricalEventPublisherAdapter implements ProductChangelogHistoricalEventPublisher {
    private final Logger logger = LoggerFactory.getLogger(ProductChangelogHistoricalEventPublisherAdapter.class);
    private final HistoricalEventChannel historicalEventChannel;

    @Override
    public void publish(ProductChangelogHistoricalEvent event) {
        try {
            Message<ProductChangelogHistoricalEvent> message = MessageBuilder.withPayload(event)
                .setHeader(MessageHeaders.CONTENT_TYPE, MimeTypeUtils.APPLICATION_JSON)
                .build();
            historicalEventChannel.productChangelogHistoricalEventOutputChannel()
                .send(message);
        } catch (Exception exception) {
            logger.error("Unable to send product historical to kafka", exception);
        }
    }
}
