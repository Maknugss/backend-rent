package com.backend.rent.infraestructure.adapter.repository;

import com.backend.rent.infraestructure.adapter.entity.PropertyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PropertyRepository extends JpaRepository<PropertyEntity, Long> {

    List<PropertyEntity> findAllByPriceBetweenAndAvailabilityIsTrueAndDeletedIsFalse(Long minPrice, Long maxPrice);

    int countByNameAndDeletedIsFalse(String name);

    Optional<PropertyEntity> findByPropertyIdAndDeletedIsFalse(Long propertyId);
}
