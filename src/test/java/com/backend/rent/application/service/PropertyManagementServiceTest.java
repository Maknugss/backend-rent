package com.backend.rent.application.service;

import com.backend.rent.application.mapper.PropertyDtoMapper;
import com.backend.rent.application.mapper.PropertyRequestMapper;
import com.backend.rent.domain.model.Property;
import com.backend.rent.domain.model.dto.PropertyDto;
import com.backend.rent.domain.model.dto.request.PropertyRequest;
import com.backend.rent.domain.port.PropertyPersistencePort;
import com.backend.rent.infraestructure.adapter.exception.PropertyException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class PropertyManagementServiceTest {

    @Mock
    private PropertyPersistencePort propertyPersistencePort;

    @Mock
    private PropertyDtoMapper propertyDtoMapper;

    @Mock
    private PropertyRequestMapper propertyRequestMapper;

    @InjectMocks
    private PropertyManagementService propertyManagementService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllProperties() {
        // Given
        List<Property> propertiesList = new ArrayList<>();
        propertiesList.add(new Property());
        propertiesList.add(new Property());

        when(propertyPersistencePort.getAllProperties(any(), any())).thenReturn(propertiesList);
        when(propertyDtoMapper.toDto(any(Property.class))).thenReturn(new PropertyDto());

        // When
        List<PropertyDto> result = propertyManagementService.getAllProperties(0L, 1000L);

        // Then
        assertEquals(2, result.size());
    }

    @Test
    void testDeletePropertyById() {
        // Given
        Long propertyId = 1L;
        Property property = new Property();
        when(propertyPersistencePort.getPropertyById(propertyId)).thenReturn(property);

        // When
        propertyManagementService.deletePropertyById(propertyId);

        // Then
        verify(propertyPersistencePort, times(1)).deleteProperty(property);
    }

    @Test
    void test_delete_property_less_than_30_days_old() {
        // Arrange
        Long propertyId = 1L;
        Property property = new Property(propertyId, "Property 1", "Medellin", true, "url", 1000000L);
        when(propertyPersistencePort.getPropertyById(propertyId)).thenReturn(property);

        // Act
        propertyManagementService.deletePropertyById(propertyId);

        // Assert
        verify(propertyPersistencePort, times(1)).deleteProperty(property);
    }

    @Test
    void testUpdateProperty_LocationChangedWhileRented_ThrowsException() {
        // Given
        Long propertyId = 1L;
        PropertyRequest propertyRequest = new PropertyRequest(propertyId, "UpdatedName", "NewLocation", true, "pictureUrl", 100L);
        Property currentProperty = new Property();
        currentProperty.setAvailability(false);
        currentProperty.setPrice(100L);
        currentProperty.setPropertyId(1L);
        currentProperty.setLocation("Location");
        currentProperty.setName("UpdatedName");

        Property propertyToUpdated = new Property();
        propertyToUpdated.setAvailability(false);
        propertyToUpdated.setPrice(100L);
        propertyToUpdated.setPropertyId(1L);
        propertyToUpdated.setLocation("Location2");
        propertyToUpdated.setName("UpdatedName");
        when(propertyRequestMapper.toDomain(any(PropertyRequest.class))).thenReturn(propertyToUpdated);
        when(propertyPersistencePort.getPropertyById(any(Long.class))).thenReturn(currentProperty);

        // When & Then
        Assertions.assertThrows(PropertyException.class, () -> {
            propertyManagementService.updateProperty(propertyRequest);
        });
    }

    @Test
    void testUpdateProperty_PriceChangedWhileRented_ThrowsException() {
        // Given
        Long propertyId = 1L;
        PropertyRequest propertyRequest = new PropertyRequest(propertyId, "UpdatedName", "Location", true, "pictureUrl", 200L);
        Property currentProperty = new Property();
        currentProperty.setAvailability(false);
        currentProperty.setPrice(100L);
        currentProperty.setPropertyId(1L);
        currentProperty.setLocation("Location");
        currentProperty.setName("UpdatedName");

        Property propertyToUpdated = new Property();
        propertyToUpdated.setAvailability(false);
        propertyToUpdated.setPrice(200L);
        propertyToUpdated.setPropertyId(1L);
        propertyToUpdated.setLocation("Location");
        propertyToUpdated.setName("UpdatedName");
        when(propertyRequestMapper.toDomain(any(PropertyRequest.class))).thenReturn(propertyToUpdated);
        when(propertyPersistencePort.getPropertyById(any(Long.class))).thenReturn(currentProperty);


        // When & Then
        Assertions.assertThrows(PropertyException.class, () -> {
            propertyManagementService.updateProperty(propertyRequest);
        });
    }

    @Test
    void testUpdateProperty_ValidUpdate_ReturnsUpdatedPropertyDto() {
        // Given
        Long propertyId = 1L;
        PropertyRequest propertyRequest = new PropertyRequest(propertyId, "UpdatedName", "Location", true, "pictureUrl", 100L);
        Property currentProperty = new Property();
        currentProperty.setAvailability(false);
        currentProperty.setPrice(100L);
        currentProperty.setPropertyId(1L);
        currentProperty.setLocation("Location");
        currentProperty.setName("UpdatedName");

        Property propertyToUpdated = new Property();
        propertyToUpdated.setAvailability(false);
        propertyToUpdated.setPrice(100L);
        propertyToUpdated.setPropertyId(1L);
        propertyToUpdated.setLocation("Location");
        propertyToUpdated.setName("UpdatedName");

        PropertyDto propertyToUpdatedDto = new PropertyDto();
        propertyToUpdatedDto.setAvailability(false);
        propertyToUpdatedDto.setPrice(100L);
        propertyToUpdatedDto.setPropertyId(1L);
        propertyToUpdatedDto.setLocation("Location");
        propertyToUpdatedDto.setName("UpdatedName");
        when(propertyRequestMapper.toDomain(any(PropertyRequest.class))).thenReturn(propertyToUpdated);
        when(propertyPersistencePort.getPropertyById(any(Long.class))).thenReturn(currentProperty);
        when(propertyPersistencePort.createNewOrUpdateProperty(any())).thenReturn(propertyToUpdated);
        when(propertyDtoMapper.toDto(any())).thenReturn(propertyToUpdatedDto);

        // When
        PropertyDto updatedPropertyDto = propertyManagementService.updateProperty(propertyRequest);

        // Then
        Assertions.assertEquals(updatedPropertyDto.getPropertyId(), propertyRequest.getPropertyId());
    }

    @Test
    void testRentProperty_ValidProperty_ReturnsUpdatedPropertyDto() {
        // Given
        Long propertyId = 1L;
        Property property = new Property();
        property.setAvailability(true);
        when(propertyPersistencePort.getPropertyById(propertyId)).thenReturn(property);

        Property propertyUpdated = new Property();
        propertyUpdated.setAvailability(false);
        when(propertyPersistencePort.createNewOrUpdateProperty(any())).thenReturn(propertyUpdated);

        PropertyDto propertyDtoUpdated = new PropertyDto();
        propertyDtoUpdated.setAvailability(false);
        when(propertyDtoMapper.toDto(any())).thenReturn(propertyDtoUpdated);

        // When
        PropertyDto rentedPropertyDto = propertyManagementService.rentProperty(propertyId);

        // Then
        Assertions.assertEquals(propertyDtoUpdated, rentedPropertyDto);
    }

    @Test
    void testRentProperty_PropertyNotAvailable_ReturnsOriginalPropertyDto() {
        // Given
        Long propertyId = 1L;
        Property property = new Property();
        property.setAvailability(false);
        when(propertyPersistencePort.getPropertyById(propertyId)).thenReturn(property);

        PropertyDto propertyDto = new PropertyDto();
        when(propertyDtoMapper.toDto(any())).thenReturn(propertyDto);

        // When
        PropertyDto rentedPropertyDto = propertyManagementService.rentProperty(propertyId);

        // Then
        Assertions.assertEquals(propertyDto, rentedPropertyDto);
    }

    @Test
    void testCreateNewProperty_PropertyExists_ThrowsException() {
        // Given
        PropertyRequest propertyRequest = new PropertyRequest();
        Property propertyToCreate = new Property();
        when(propertyRequestMapper.toDomain(propertyRequest)).thenReturn(propertyToCreate);
        when(propertyPersistencePort.countPropertyByName(any())).thenReturn(1);

        // When & Then
        Assertions.assertThrows(PropertyException.class, () -> {
            propertyManagementService.createNewProperty(propertyRequest);
        });
    }

    @Test
    void testDeletePropertyById_PropertyCreatedMoreThan30DaysAgo_ThrowsException() {
        // Given
        Long propertyId = 1L;
        Property property = new Property();

        // Set the property creation date 40 days ago
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, -40);
        Date fortyDaysAgo = calendar.getTime();
        property.setCreatedDate(fortyDaysAgo);

        when(propertyPersistencePort.getPropertyById(propertyId)).thenReturn(property);

        // When & Then
        Assertions.assertThrows(PropertyException.class, () -> {
            propertyManagementService.deletePropertyById(propertyId);
        });
    }

    @Test
    void testUpdateProperty_ValidUpdate_ReturnsUpdatedPropertyDto2() {
        // Given
        Long propertyId = 1L;
        PropertyRequest propertyRequest = new PropertyRequest(propertyId, "UpdatedName", "Location", true, "pictureUrl", 100L);
        Property currentProperty = new Property();
        currentProperty.setAvailability(false);
        currentProperty.setPrice(100L);
        currentProperty.setPropertyId(1L);
        currentProperty.setLocation("Location");
        currentProperty.setName("UpdatedName");

        Property propertyToUpdated = new Property();
        propertyToUpdated.setAvailability(false);
        propertyToUpdated.setPrice(100L);
        propertyToUpdated.setPropertyId(1L);
        propertyToUpdated.setLocation("Location");
        propertyToUpdated.setName("UpdatedName");

        PropertyDto propertyToUpdatedDto = new PropertyDto();
        propertyToUpdatedDto.setAvailability(false);
        propertyToUpdatedDto.setPrice(100L);
        propertyToUpdatedDto.setPropertyId(1L);
        propertyToUpdatedDto.setLocation("Location");
        propertyToUpdatedDto.setName("UpdatedName");

        when(propertyRequestMapper.toDomain(propertyRequest)).thenReturn(propertyToUpdated);
        when(propertyPersistencePort.getPropertyById(propertyId)).thenReturn(currentProperty);
        when(propertyPersistencePort.createNewOrUpdateProperty(propertyToUpdated)).thenReturn(propertyToUpdated);
        when(propertyDtoMapper.toDto(propertyToUpdated)).thenReturn(propertyToUpdatedDto);

        // When
        PropertyDto updatedPropertyDto = propertyManagementService.updateProperty(propertyRequest);

        // Then
        Assertions.assertEquals(updatedPropertyDto.getPropertyId(), propertyRequest.getPropertyId());
    }

    @Test
    void testCreateNewProperty_PropertyWithSameNameExists_ThrowsException() {
        // Given
        PropertyRequest propertyRequest = new PropertyRequest();
        Property propertyToCreate = new Property();
        when(propertyRequestMapper.toDomain(propertyRequest)).thenReturn(propertyToCreate);
        when(propertyPersistencePort.countPropertyByName(any())).thenReturn(1);

        // When & Then
        Assertions.assertThrows(PropertyException.class, () -> {
            propertyManagementService.createNewProperty(propertyRequest);
        });
    }

    @Test
    void testCreateNewProperty_InvalidLocation_ThrowsException() {
        // Given
        PropertyRequest propertyRequest = new PropertyRequest(1L, "UpdatedName", "Location", true, "pictureUrl", 100L);

        Property propertyToCreate = new Property();
        propertyToCreate.setAvailability(false);
        propertyToCreate.setPrice(100L);
        propertyToCreate.setPropertyId(1L);
        propertyToCreate.setLocation("invalidLocation");
        propertyToCreate.setName("UpdatedName");
        when(propertyRequestMapper.toDomain(any(PropertyRequest.class))).thenReturn(propertyToCreate);
        when(propertyPersistencePort.countPropertyByName(anyString())).thenReturn(0);

        // When & Then
        Assertions.assertThrows(PropertyException.class, () -> {
            propertyManagementService.createNewProperty(propertyRequest);
        });
    }

    @Test
    void testCreateNewProperty_NegativePrice_ThrowsException() {
        // Given
        PropertyRequest propertyRequest = new PropertyRequest(1L, "UpdatedName", "Location", true, "pictureUrl", 100L);

        Property propertyToCreate = new Property();
        propertyToCreate.setAvailability(false);
        propertyToCreate.setPrice(-100L);
        propertyToCreate.setPropertyId(1L);
        propertyToCreate.setLocation("Bogota");
        propertyToCreate.setName("UpdatedName");
        when(propertyRequestMapper.toDomain(any(PropertyRequest.class))).thenReturn(propertyToCreate);
        when(propertyPersistencePort.countPropertyByName(anyString())).thenReturn(0);

        // When & Then
        Assertions.assertThrows(PropertyException.class, () -> {
            propertyManagementService.createNewProperty(propertyRequest);
        });
    }

    @Test
    void testCreateNewProperty_PriceLessThanMinimum_BogotaOrCali_ThrowsException() {
        // Given
        PropertyRequest propertyRequest = new PropertyRequest(1L, "UpdatedName", "Location", true, "pictureUrl", 100L);

        Property propertyToCreate = new Property();
        propertyToCreate.setAvailability(false);
        propertyToCreate.setPrice(100L);
        propertyToCreate.setPropertyId(1L);
        propertyToCreate.setLocation("Bogota");
        propertyToCreate.setName("UpdatedName");
        when(propertyRequestMapper.toDomain(any(PropertyRequest.class))).thenReturn(propertyToCreate);
        when(propertyPersistencePort.countPropertyByName(anyString())).thenReturn(0);

        // When & Then
        Assertions.assertThrows(PropertyException.class, () -> {
            propertyManagementService.createNewProperty(propertyRequest);
        });
    }

    @Test
    void testCreateNewProperty() {
        // Given
        PropertyRequest propertyRequest = new PropertyRequest(1L, "UpdatedName", "Location", true, "pictureUrl", 100L);

        Property propertyToCreate = new Property();
        propertyToCreate.setAvailability(false);
        propertyToCreate.setPrice(1000000000L);
        propertyToCreate.setPropertyId(1L);
        propertyToCreate.setLocation("Bogota");
        propertyToCreate.setName("UpdatedName");
        propertyToCreate.setPictureUrl("pictureUrl");

        PropertyDto propertyDto = new PropertyDto();
        propertyDto.setAvailability(false);
        propertyDto.setPrice(1000000000L);
        propertyDto.setPropertyId(1L);
        propertyDto.setLocation("Bogota");
        propertyDto.setName("UpdatedName");
        propertyDto.setPictureUrl("pictureUrl");

        when(propertyRequestMapper.toDomain(any(PropertyRequest.class))).thenReturn(propertyToCreate);
        when(propertyPersistencePort.countPropertyByName(anyString())).thenReturn(0);
        when(propertyPersistencePort.createNewOrUpdateProperty(any())).thenReturn(propertyToCreate);
        when(propertyDtoMapper.toDto(any())).thenReturn(propertyDto);

        PropertyDto propertyDtoFinal = propertyManagementService.createNewProperty(propertyRequest);

        Assertions.assertEquals(propertyDtoFinal.getPropertyId(), propertyRequest.getPropertyId());
        Assertions.assertEquals(propertyDtoFinal.getPictureUrl(), propertyRequest.getPictureUrl());
    }

}
