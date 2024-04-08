package com.backend.rent.application.usecases;

import com.backend.rent.domain.model.dto.PropertyDto;
import com.backend.rent.domain.model.dto.request.PropertyRequest;

import java.util.List;

public interface PropertyService {
    List<PropertyDto> getAllProperties(Long minPrice, Long maxPrice);

    PropertyDto createNewProperty(PropertyRequest propertyRequest);

    void deletePropertyById(Long propertyId);

    PropertyDto rentProperty(Long propertyId);

    PropertyDto updateProperty(PropertyRequest propertyRequest);
}
