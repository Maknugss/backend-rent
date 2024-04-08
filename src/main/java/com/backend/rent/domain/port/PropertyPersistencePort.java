package com.backend.rent.domain.port;

import com.backend.rent.domain.model.Property;

import java.util.List;

public interface PropertyPersistencePort {
    List<Property> getAllProperties(Long minPrice, Long maxPrice);

    Property createNewOrUpdateProperty(Property property);

    void deleteProperty(Property property);
    
    Property getPropertyById(Long propertyId);

    int countPropertyByName(String name);
}
