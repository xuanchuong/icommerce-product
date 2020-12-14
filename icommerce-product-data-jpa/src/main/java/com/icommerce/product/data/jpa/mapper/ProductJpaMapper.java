package com.icommerce.product.data.jpa.mapper;

import com.icommerce.product.data.jpa.entity.ProductJpa;
import com.icommerce.product.domain.entity.Product;
import org.mapstruct.Mapper;

import static org.mapstruct.NullValueCheckStrategy.ALWAYS;
import static org.mapstruct.NullValuePropertyMappingStrategy.SET_TO_NULL;

@Mapper(
    componentModel = "spring",
    nullValuePropertyMappingStrategy = SET_TO_NULL,
    nullValueCheckStrategy = ALWAYS
)
public interface ProductJpaMapper {
    Product map(ProductJpa source);

    ProductJpa map(Product source);
}
