package com.backend.rent.infraestructure.controller;

import com.backend.rent.application.usecases.PropertyService;
import com.backend.rent.domain.model.dto.PropertyDto;
import com.backend.rent.domain.model.dto.request.PropertyRequest;
import com.backend.rent.infraestructure.rest.controller.PropertyController;
import com.backend.rent.infraestructure.rest.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class PropertyControllerTest {

    @Mock
    private PropertyService propertyService;

    @InjectMocks
    private PropertyController propertyController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllProperties() {
        // Given
        List<PropertyDto> properties = new ArrayList<>();
        when(propertyService.getAllProperties(any(Long.class), any(Long.class))).thenReturn(properties);

        // When
        ResponseEntity<List<PropertyDto>> responseEntity = propertyController.getAllProperties(100L, 200L);

        // Then
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(properties, responseEntity.getBody());
    }

    @Test
    void testCreateNewProperty() {
        // Given
        PropertyRequest propertyRequest = new PropertyRequest();
        PropertyDto propertyDto = new PropertyDto();
        when(propertyService.createNewProperty(propertyRequest)).thenReturn(propertyDto);

        // When
        ResponseEntity<PropertyDto> responseEntity = propertyController.createNewProperty(propertyRequest);

        // Then
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(propertyDto, responseEntity.getBody());
    }

    @Test
    void testDeletePropertyById() {
        // Given
        Long propertyId = 1L;
        ResponseEntity<Response> expectedResponseEntity = ResponseEntity.status(HttpStatus.OK)
                .body(new Response("Se elimino correctamente la propiedad con id: " + propertyId, HttpStatus.OK.value()));

        // When
        ResponseEntity<Response> responseEntity = propertyController.deletePropertyById(propertyId);

        // Then
        assertEquals(expectedResponseEntity.getStatusCode(), responseEntity.getStatusCode());
        verify(propertyService, times(1)).deletePropertyById(propertyId);
    }

    @Test
    void testRentProperty() {
        // Given
        Long propertyId = 1L;
        PropertyDto propertyDto = new PropertyDto();
        when(propertyService.rentProperty(propertyId)).thenReturn(propertyDto);

        // When
        ResponseEntity<PropertyDto> responseEntity = propertyController.rentProperty(propertyId);

        // Then
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(propertyDto, responseEntity.getBody());
    }

    @Test
    void testUpdateProperty() {
        // Given
        PropertyRequest propertyRequest = new PropertyRequest();
        PropertyDto propertyDto = new PropertyDto();
        when(propertyService.updateProperty(propertyRequest)).thenReturn(propertyDto);

        // When
        ResponseEntity<PropertyDto> responseEntity = propertyController.updateProperty(propertyRequest);

        // Then
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(propertyDto, responseEntity.getBody());
    }
}
