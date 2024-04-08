package com.backend.rent.application.service;

import com.backend.rent.application.mapper.PropertyDtoMapper;
import com.backend.rent.application.mapper.PropertyRequestMapper;
import com.backend.rent.application.usecases.PropertyService;
import com.backend.rent.domain.model.Property;
import com.backend.rent.domain.model.dto.PropertyDto;
import com.backend.rent.domain.model.dto.request.PropertyRequest;
import com.backend.rent.domain.port.PropertyPersistencePort;
import com.backend.rent.infraestructure.adapter.exception.PropertyException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PropertyManagementService implements PropertyService {
    private final PropertyPersistencePort propertyPersistencePort;
    private final PropertyDtoMapper propertyDtoMapper;
    private final PropertyRequestMapper propertyRequestMapper;

    public PropertyManagementService(PropertyPersistencePort propertiesPersistencePort, PropertyDtoMapper propertiesDtoMapper, PropertyRequestMapper propertyRequestMapper) {
        this.propertyPersistencePort = propertiesPersistencePort;
        this.propertyDtoMapper = propertiesDtoMapper;
        this.propertyRequestMapper = propertyRequestMapper;
    }
    public List<PropertyDto> getAllProperties(Long minPrice, Long maxPrice) {
        List<Property> propertiesList = propertyPersistencePort.getAllProperties(minPrice, maxPrice);
        return propertiesList
                .stream()
                .map(propertyDtoMapper::toDto)
                .toList();
    }

    @Override
    public PropertyDto createNewProperty(PropertyRequest propertyRequest) {
        Property propertyToCreate = propertyRequestMapper.toDto(propertyRequest);
        Property propertyCreated = propertyPersistencePort.createNewOrUpdateProperty(propertyToCreate);
        return propertyDtoMapper.toDto(propertyCreated);
    }

    @Override
    public void deletePropertyById(Long propertyId) {
        Property property = propertyPersistencePort.getPropertyById(propertyId);
        if(property.isLessTo30Day()){
            propertyPersistencePort.deleteProperty(property);
        }
        else throw new PropertyException(HttpStatus.BAD_REQUEST, "La propiedad que se desea eliminar supera los 30 días");
    }

    @Override
    public PropertyDto rentProperty(Long propertyId) {
        Property property = propertyPersistencePort.getPropertyById(propertyId);
        property.setAvailability(false);
        Property propertyUpdated = propertyPersistencePort.createNewOrUpdateProperty(property);
        return propertyDtoMapper.toDto(propertyUpdated);
    }

    @Override
    public PropertyDto updateProperty(PropertyRequest propertyRequest) {
        Property propertyToUpdate = propertyRequestMapper.toDto(propertyRequest);
        Property currentProperty = propertyPersistencePort.getPropertyById(propertyToUpdate.getPropertyId());
        if(!currentProperty.getAvailability() && !currentProperty.getLocation().equals(propertyToUpdate.getLocation())){
            throw new PropertyException(HttpStatus.BAD_REQUEST, "No se puede modificar la ubicación de una propiedad rentada");
        }
        if(!currentProperty.getAvailability() && !currentProperty.getPrice().equals(propertyToUpdate.getPrice())){
            throw new PropertyException(HttpStatus.BAD_REQUEST, "No se puede modificar el precio de una propiedad rentada");
        }
        if((propertyToUpdate.getLocation().equals("Bogota") || propertyToUpdate.getLocation().equals("Cali")) && propertyToUpdate.getPrice() < 2000000L){
            throw new PropertyException(HttpStatus.BAD_REQUEST, "En " + propertyToUpdate.getLocation() + " no se puede poner un precio menor a 2'000.000");
        }
        Property propertyUpdated = propertyPersistencePort.createNewOrUpdateProperty(propertyToUpdate);
        return propertyDtoMapper.toDto(propertyUpdated);
    }
}
