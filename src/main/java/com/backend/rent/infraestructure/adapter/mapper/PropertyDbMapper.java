package com.backend.rent.infraestructure.adapter.mapper;

import com.backend.rent.domain.model.Property;
import com.backend.rent.infraestructure.adapter.entity.PropertyEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PropertyDbMapper {
    PropertyEntity toDb(Property properties);

    Property toDomain(PropertyEntity propertiesEntity);
}