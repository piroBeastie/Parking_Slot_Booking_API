package com.example.parking.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "parking_slots")
public class ParkingSlot {
    @Id
    private String id;
    private String location;
    private String slotNumber;
    private double pricePerHour;
    private boolean available;

    public ParkingSlot(){}

    public ParkingSlot(String id, String location, String slotNumber, double pricePerHour, boolean available) {
        this.id = id;
        this.location = location;
        this.slotNumber = slotNumber;
        this.pricePerHour = pricePerHour;
        this.available = available;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getSlotNumber() {
        return slotNumber;
    }

    public void setSlotNumber(String slotNumber) {
        this.slotNumber = slotNumber;
    }

    public double getPricePerHour() {
        return pricePerHour;
    }

    public void setPricePerHour(double pricePerHour) {
        this.pricePerHour = pricePerHour;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }
}
