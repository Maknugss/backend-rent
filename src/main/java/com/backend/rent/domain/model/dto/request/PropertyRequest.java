package com.backend.rent.domain.model.dto.request;

import com.backend.rent.infraestructure.rest.advice.BasicInfo;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class PropertyRequest {

    private Long propertyId;
    @NotBlank(groups = BasicInfo.class, message = "El nombre no puede estar en blando")
    @NotNull(groups = BasicInfo.class, message = "El nombre no puede ser null")
    private String name;
    @NotBlank(groups = BasicInfo.class, message = "La ubicación no puede estar vacia")
    @NotNull(groups = BasicInfo.class, message = "La ubicación no puede ser null")
    private String location;

    @NotNull(groups = BasicInfo.class, message = "La disponibilidad no puede ser null")
    private Boolean availability;

    @NotBlank(groups = BasicInfo.class, message = "la url de la imagen no puede estar vacia")
    @NotNull(groups = BasicInfo.class, message = "La url de la imagen no puede ser null")
    private String pictureUrl;

    @NotNull(groups = BasicInfo.class, message = "El precio no puede ser nulo")
    private Long price;

    public PropertyRequest() {
    }

    public PropertyRequest(Long propertyId, String name, String location, Boolean availability, String pictureUrl, Long price) {
        this.propertyId = propertyId;
        this.name = name;
        this.location = location;
        this.availability = availability;
        this.pictureUrl = pictureUrl;
        this.price = price;
    }

    public Long getPropertyId() {
        return propertyId;
    }

    public void setPropertyId(Long propertyId) {
        this.propertyId = propertyId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Boolean getAvailability() {
        return availability;
    }

    public void setAvailability(Boolean availability) {
        this.availability = availability;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }
}
