package com.icommerce.product.domain.event;

import com.icommerce.product.domain.entity.Product;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Getter
@Builder
@FieldDefaults(makeFinal = true)
public class ProductChangelogHistoricalEvent {
    private String userId;
    private String productId;
    private Integer version;
    private Map<String, Object> detail;
    private LocalDate updateDate;

    public static ProductChangelogHistoricalEvent build(Product product, String userId) {
        Map<String, Object> detail = new HashMap<>();
        detail.put("title", product.getTitle());
        detail.put("brand", product.getBrand());
        detail.put("price", product.getPrice());
        return ProductChangelogHistoricalEvent.builder()
                .productId(product.getId())
                .updateDate(LocalDate.now())
                .version(product.getVersion())
                .userId(userId)
                .detail(detail).build();
    }

}
