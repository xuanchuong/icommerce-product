package com.icommerce.product.data.jpa.mapper;

import com.icommerce.product.data.jpa.entity.FootballMatchJpa;
import com.icommerce.product.domain.entity.FootballMatch;
import org.mapstruct.Mapper;

import static org.mapstruct.NullValueCheckStrategy.ALWAYS;
import static org.mapstruct.NullValuePropertyMappingStrategy.SET_TO_NULL;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = SET_TO_NULL,
        nullValueCheckStrategy = ALWAYS
)
public interface FootballMatchMapper {

    FootballMatch map(FootballMatchJpa source);

    FootballMatchJpa map(FootballMatch source);
}
