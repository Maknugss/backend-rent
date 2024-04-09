package com.backend.rent.domain;

import com.backend.rent.domain.model.Property;
import com.backend.rent.infraestructure.adapter.exception.PropertyException;
import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.Date;

import static com.backend.rent.domain.model.constant.Constants.MEDELLIN;
import static org.junit.jupiter.api.Assertions.*;

class PropertyTest {

    @Test
    void testIsLessTo30Day_PropertyCreatedLessThan30DaysAgo_ReturnsTrue() {
        // Given
        Property property = new Property();
        Date currentDate = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);
        calendar.add(Calendar.DAY_OF_MONTH, -29);
        Date dateLessThan30DaysAgo = calendar.getTime();
        property.setCreatedDate(dateLessThan30DaysAgo);

        // When
        boolean result = property.isLessTo30Day();

        // Then
        assertTrue(result);
    }

    @Test
    void testIsLessTo30Day_PropertyCreatedMoreThan30DaysAgo_ReturnsFalse() {
        // Given
        Property property = new Property();
        Date currentDate = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);
        calendar.add(Calendar.DAY_OF_MONTH, -31);
        Date dateMoreThan30DaysAgo = calendar.getTime();
        property.setCreatedDate(dateMoreThan30DaysAgo);

        // When
        boolean result = property.isLessTo30Day();

        // Then
        assertFalse(result);
    }

    @Test
    void testValidateLocation_ValidLocation_DoesNotThrowException() {
        // Given
        Property property = new Property();
        property.setLocation(MEDELLIN);

        // When & Then
        assertDoesNotThrow(() -> {
            property.validateLocation();
        });
    }

    @Test
    void testValidateLocation_InvalidLocation_ThrowsException() {
        // Given
        Property property = new Property();
        property.setLocation("InvalidLocation");

        // When & Then
        assertThrows(PropertyException.class, () -> {
            property.validateLocation();
        });
    }

    @Test
    void testValidatePriceGreaterThanZero_PositivePrice_DoesNotThrowException() {
        // Given
        Property property = new Property();
        property.setPrice(100L);

        // When & Then
        assertDoesNotThrow(() -> {
            property.validatePriceGreaterThanZero();
        });
    }

    @Test
    void testValidatePriceGreaterThanZero_NegativePrice_ThrowsException() {
        // Given
        Property property = new Property();
        property.setPrice(-100L);

        // When & Then
        assertThrows(PropertyException.class, () -> {
            property.validatePriceGreaterThanZero();
        });
    }

    @Test
    void testValidatePriceBogotaAndCali_ValidPrice_DoesNotThrowException() {
        // Given
        Property property = new Property();
        property.setLocation("Bogota");
        property.setPrice(2000000L);

        // When & Then
        assertDoesNotThrow(() -> {
            property.validatePriceBogotaAndCali();
        });
    }

    @Test
    void testValidatePriceBogotaAndCali_InvalidPrice_ThrowsException() {
        // Given
        Property property = new Property();
        property.setLocation("Bogota");
        property.setPrice(1000000L);

        // When & Then
        assertThrows(PropertyException.class, () -> {
            property.validatePriceBogotaAndCali();
        });
    }
}
