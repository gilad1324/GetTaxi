package com.jct.gilad.gettaxi.model.entities;

import android.location.Location;

import java.util.Date;


public class Drive {
    public Drive(Status1 status, Location sourceLocation, Location destLocation, String clientName, long clientPhoneNumber, String clientEmail) {

        this.status = status;
        this.sourceLocation = sourceLocation;
        this.destLocation = destLocation;
        this.startTime = null;
        this.endTime = null;
        this.clientName = clientName;
        this.clientPhoneNumber = clientPhoneNumber;
        this.clientEmail = clientEmail;
    }

    Status1 status;
    Location sourceLocation;
    Location destLocation;
    Date startTime;
    Date endTime;
    String clientName;
    long clientPhoneNumber;
    String clientEmail;

    public Status1 getStatus() {
        return status;
    }

    public void setStatus(Status1 status) {
        this.status = status;
    }

    public Location getSourceLocation() {
        return sourceLocation;
    }

    public void setSourceLocation(Location sourceLocation) {
        this.sourceLocation = sourceLocation;
    }

    public Location getDestLocation() {
        return destLocation;
    }

    public void setDestLocation(Location destLocation) {
        this.destLocation = destLocation;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public Long getClientPhoneNumber() {
        return clientPhoneNumber;
    }

    public void setClientPhoneNumber(int clientPhoneNumber) {
        this.clientPhoneNumber = clientPhoneNumber;
    }

    public String getClientEmail() {
        return clientEmail;
    }

    public void setClientEmail(String clientEmail) {
        this.clientEmail = clientEmail;
    }

}
