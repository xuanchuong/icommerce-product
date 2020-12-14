package com.icommerce.product.message.producer.adapter;

import com.icommerce.product.domain.event.UserActivitiesHistoricalEvent;
import com.icommerce.product.domain.event.UserActivitiesHistoricalEventPublisher;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.util.MimeTypeUtils;

@AllArgsConstructor
public class UserActivitiesHistoricalEventPublisherAdapter implements UserActivitiesHistoricalEventPublisher {
    private final Logger logger = LoggerFactory.getLogger(UserActivitiesHistoricalEventPublisherAdapter.class);
    private final HistoricalEventChannel historicalEventChannel;

    @Override
    public void publish(UserActivitiesHistoricalEvent event) {
        try {
            Message<UserActivitiesHistoricalEvent> message = MessageBuilder.withPayload(event)
                    .setHeader(MessageHeaders.CONTENT_TYPE, MimeTypeUtils.APPLICATION_JSON)
                    .build();
            historicalEventChannel.userActivitiesHistoricalEventOutputChannel()
                    .send(message);
        } catch (Exception exception) {
            logger.error("Unable to send user historical to kafka", exception);
        }
    }
}
