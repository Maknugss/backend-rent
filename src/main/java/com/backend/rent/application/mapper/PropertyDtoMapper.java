package com.backend.rent.application.mapper;

import com.backend.rent.domain.model.Property;
import com.backend.rent.domain.model.dto.PropertyDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PropertyDtoMapper {

    PropertyDto toDto(Property properties);
}
