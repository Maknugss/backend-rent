package com.backend.rent.infraestructure.rest.controller;

import com.backend.rent.application.usecases.PropertyService;
import com.backend.rent.domain.model.dto.PropertyDto;
import com.backend.rent.domain.model.dto.request.PropertyRequest;
import com.backend.rent.infraestructure.rest.advice.BasicInfo;
import com.backend.rent.infraestructure.rest.response.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/property")
public class PropertyController {

    private final PropertyService propertyService;

    public PropertyController(PropertyService propertiesService) {
        this.propertyService = propertiesService;
    }

    @GetMapping("/minprice/{minPrice}/maxprice/{maxPrice}")
    public ResponseEntity<List<PropertyDto>> getAllProperties(@PathVariable Long minPrice, @PathVariable Long maxPrice){
        return ResponseEntity.status(HttpStatus.OK).body(propertyService.getAllProperties(minPrice, maxPrice));
    }

    @PostMapping()
    public ResponseEntity<PropertyDto> createNewProperty(@Validated(BasicInfo.class) @RequestBody PropertyRequest propertyRequest){
        return ResponseEntity.status(HttpStatus.OK).body(propertyService.createNewProperty(propertyRequest));
    }

    @DeleteMapping("/{propertyId}")
    public ResponseEntity<Response> deletePropertyById(@PathVariable Long propertyId){
        propertyService.deletePropertyById(propertyId);
        return ResponseEntity.status(HttpStatus.OK).body(new Response("Se elimino correctamente la propiedad con id: " + propertyId, HttpStatus.OK.value()));
    }

    @PutMapping("rentProperty/{propertyId}")
    public ResponseEntity<PropertyDto> rentProperty(@PathVariable Long propertyId){
        return ResponseEntity.status(HttpStatus.OK).body(propertyService.rentProperty(propertyId));
    }

    @PutMapping()
    public ResponseEntity<PropertyDto> updateProperty(@Validated(BasicInfo.class) @RequestBody PropertyRequest propertyRequest){
        return ResponseEntity.status(HttpStatus.OK).body(propertyService.updateProperty(propertyRequest));
    }


}
