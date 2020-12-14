package com.icommerce.product.domain.event;

import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Builder
@FieldDefaults(makeFinal = true)
public class UserActivitiesHistoricalEvent {
    private String userId;
    private String correlationId;
    private String actionId;
    private String actionDescription;
    @Builder.Default
    private LocalDateTime actionDate = LocalDateTime.now();
}
