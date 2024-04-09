package com.backend.rent.infraestructure.advice;

import com.backend.rent.infraestructure.adapter.exception.PropertyException;
import com.backend.rent.infraestructure.rest.advice.ControllerAdvice;
import com.backend.rent.infraestructure.rest.response.ErrorResponse;
import com.backend.rent.infraestructure.rest.response.ErrorResponseMap;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ControllerAdviceTest {

    @InjectMocks
    private ControllerAdvice controllerAdvice;

    public ControllerAdviceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testHandlePropertyException() {
        // Given
        PropertyException propertyException = new PropertyException(HttpStatus.NOT_FOUND, "Property not found");

        // When
        ResponseEntity<ErrorResponse> responseEntity = controllerAdvice.handleInput(propertyException);

        // Then
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertEquals("Property not found", responseEntity.getBody().getMessage());
    }

    @Test
    void testHandleInvalidArguments() {
        // Given
        MethodArgumentNotValidException exception = new MethodArgumentNotValidException(null, mock(BindingResult.class));
        BindingResult bindingResult = exception.getBindingResult();

        List<FieldError> fieldErrors = new ArrayList<>();
        FieldError fieldError1 = new FieldError("objectName", "field1", "Error message 1");
        FieldError fieldError2 = new FieldError("objectName", "field2", "Error message 2");
        fieldErrors.add(fieldError1);
        fieldErrors.add(fieldError2);

        when(bindingResult.getFieldErrors()).thenReturn(fieldErrors);

        // When
        ResponseEntity<ErrorResponseMap> responseEntity = controllerAdvice.handleInvalidArguments(exception);

        // Then
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

}
