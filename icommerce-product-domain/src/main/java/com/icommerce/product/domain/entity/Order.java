package com.icommerce.product.domain.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Builder
@FieldDefaults(makeFinal = true)
public class Order implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    private String paymentType;

    private String createdBy;

    private Cart cart;

    private LocalDate deliveryDate;

    private LocalDate createdDate;
}
