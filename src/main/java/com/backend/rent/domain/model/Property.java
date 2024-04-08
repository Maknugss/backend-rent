package com.backend.rent.domain.model;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

public class Property {
    private Long propertyId;
    private String name;
    private String location;
    private Boolean availability;
    private String pictureUrl;
    private Long price;

    private Date createdDate = new Date();

    public Property() {
    }

    public Property(Long propertyId, String name, String location, Boolean availability, String pictureUrl, Long price) {
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

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public boolean isLessTo30Day() {
        Date currentDay = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDay);
        calendar.add(Calendar.DAY_OF_MONTH, -30);
        Date thirtyDaysAgo = calendar.getTime();
        return this.createdDate.after(thirtyDaysAgo);
    }
}
