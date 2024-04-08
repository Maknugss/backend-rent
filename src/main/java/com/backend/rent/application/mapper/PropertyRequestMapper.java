package com.backend.rent.application.mapper;

import com.backend.rent.domain.model.Property;
import com.backend.rent.domain.model.dto.request.PropertyRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PropertyRequestMapper {

    Property toDomain(PropertyRequest propertyRequest);

}
