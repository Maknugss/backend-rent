package com.backend.rent.domain.model.dto.request;

public class PropertyRequest {

    private Long propertyId;
    private String name;
    private String location;
    private Boolean availability;
    private String pictureUrl;
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
