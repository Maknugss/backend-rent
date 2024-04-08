package com.backend.rent.infraestructure.adapter;

import com.backend.rent.domain.model.Property;
import com.backend.rent.domain.port.PropertyPersistencePort;
import com.backend.rent.infraestructure.adapter.entity.PropertyEntity;
import com.backend.rent.infraestructure.adapter.exception.PropertyException;
import com.backend.rent.infraestructure.adapter.mapper.PropertyDbMapper;
import com.backend.rent.infraestructure.adapter.repository.PropertyRepository;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PropertyJpaAdapter implements PropertyPersistencePort {

    private final PropertyRepository propertyRepository;
    private final PropertyDbMapper propertyDBMapper;


    public PropertyJpaAdapter(PropertyRepository propertyRepository, PropertyDbMapper propertyDBMapper) {
        this.propertyRepository = propertyRepository;
        this.propertyDBMapper = propertyDBMapper;
    }

    @Override
    public List<Property> getAllProperties(Long minPrice, Long maxPrice) {
        return propertyRepository.findAllByPriceBetweenAndAvailabilityIsTrueAndDeletedIsFalse(minPrice, maxPrice)
                .stream()
                .map(propertyDBMapper::toDomain)
                .toList();
    }

    @Override
    public Property createNewOrUpdateProperty(Property property) {
        PropertyEntity propertyEntityToSave = propertyDBMapper.toDb(property);
        PropertyEntity propertySaved = propertyRepository.save(propertyEntityToSave);
        return propertyDBMapper.toDomain(propertySaved);
    }

    @Override
    public void deleteProperty(Property property) {
        PropertyEntity propertyEntityToDelete =  propertyDBMapper.toDb(property);
        propertyEntityToDelete.setDeleted(true);
        propertyRepository.save(propertyEntityToDelete);

    }

    @Override
    public Property getPropertyById(Long propertyId) {
        Optional<PropertyEntity> propertyEntityGot = propertyRepository.findById(propertyId);
        if(propertyEntityGot.isEmpty()){
            throw new PropertyException(HttpStatus.NOT_FOUND, "No se logro encontrar la propiedad que se desea consultar con id " + propertyId);
        }
        return propertyDBMapper.toDomain(propertyEntityGot.get());
    }

    @Override
    public int countPropertyByName(String name) {
        return propertyRepository.countByName(name);
    }

}
