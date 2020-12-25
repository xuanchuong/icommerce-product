package com.icommerce.product.domain.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.Map;
import java.util.UUID;


@Getter
@Setter
@ToString
public class Cart {

    private UUID id;

    private String userId;

    private Map<String, CartDetail> selectedProducts;

    private Integer totalAmount;

    private LocalDate createdDate;
}
