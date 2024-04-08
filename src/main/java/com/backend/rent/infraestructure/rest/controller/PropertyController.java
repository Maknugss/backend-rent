package com.backend.rent.infraestructure.rest.controller;

import com.backend.rent.application.usecases.PropertyService;
import com.backend.rent.domain.model.dto.PropertyDto;
import com.backend.rent.domain.model.dto.request.PropertyRequest;
import com.backend.rent.infraestructure.rest.advice.BasicInfo;
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
    public List<PropertyDto> getAllProperties(@PathVariable Long minPrice, @PathVariable Long maxPrice){
        return propertyService.getAllProperties(minPrice, maxPrice);
    }

    @PostMapping()
    public PropertyDto createNewProperty(@Validated(BasicInfo.class) @RequestBody PropertyRequest propertyRequest){
        return propertyService.createNewProperty(propertyRequest);
    }

    @DeleteMapping("/{propertyId}")
    public void deletePropertyById(@PathVariable Long propertyId){
        propertyService.deletePropertyById(propertyId);
    }

    @PutMapping("rentProperty/{propertyId}")
    public PropertyDto rentProperty(@PathVariable Long propertyId){
       return propertyService.rentProperty(propertyId);
    }

    @PutMapping()
    public PropertyDto updateProperty(@Validated(BasicInfo.class) @RequestBody PropertyRequest propertyRequest){
        return propertyService.updateProperty(propertyRequest);
    }


}
