package com.icommerce.product.application.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
public class ProductDTO {
    private String title;
    private String id;
    private String brand;
    private Integer price;
}
