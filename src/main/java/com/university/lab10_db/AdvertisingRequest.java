package com.university.lab10_db;

import java.sql.Date;

public class AdvertisingRequest {
    public int id;
    public RequestType type;
    public String clientName;
    public String placementLocation;
    public Date date;
    public String content;
    public double cost;
    public boolean paymentStatus;
    public int customerId;
    public int publicationId;

    public void setId(int id) {
        this.id = id;
    }

    public void setType(RequestType type) {
        this.type = type;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public void setPlacementLocation(String placementLocation) {
        this.placementLocation = placementLocation;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public void setPaymentStatus(boolean paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public void setPublicationId(int publicationId) {
        this.publicationId = publicationId;
    }

    public RequestType getType() {
        return type;
    }

    public String getClientName() {
        return clientName;
    }

    public String getPlacementLocation() {
        return placementLocation;
    }

    public Date getDate() {
        return date;
    }

    public String getContent() {
        return content;
    }

    public double getCost() {
        return cost;
    }

    public boolean isPaymentStatus() {
        return paymentStatus;
    }

    public int getCustomerId() {
        return customerId;
    }

    public int getPublicationId() {
        return publicationId;
    }

    public int getId() {
        return id;
    }

    public enum RequestType {
        TEXT("Текстовая реклама"),
        GRAPHIC("Графическая реклама");

        private final String description;

        RequestType(String description) {
            this.description = description;
        }

        public String getDescription() {
            return description;
        }
    }

}
