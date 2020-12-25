package com.icommerce.product.domain.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
public class CartDetail {
    private Integer pricePerUnit;
    private Integer quantity;
}
