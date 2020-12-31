package com.icommerce.product.data.jpa.entity;

import com.icommerce.product.domain.entity.Cart;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;

@Document(collection = "order")
@Getter
@Setter
@ToString
public class OrderJpa implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull
    @Field("payment_type")
    private String paymentType;

    @NotNull
    @Field("created_by")
    private String createdBy;

    @NotNull
    @Field("cart")
    private Cart cart;

    @NotNull
    @Field("delivery_date")
    private LocalDate deliveryDate;

    @NotNull
    @Field("created_date")
    private LocalDate createdDate;
}
