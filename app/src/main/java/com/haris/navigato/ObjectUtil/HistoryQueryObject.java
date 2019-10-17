package com.haris.navigato.ObjectUtil;

import java.io.Serializable;

/**
 * Created by hp on 6/1/2018.
 */

public class HistoryQueryObject extends Object implements Serializable {
    private String id;
    private String routeName;
    private String conveyanceName;
    private String sourceName;
    private String destinationName;
    private String distance;
    private String duration;
    private String petrol;
    private Double sourceLatitude;
    private Double sourceLongitude;
    private Double destinationLatitude;
    private Double destinationLongitude;


    public HistoryQueryObject(String id, String routeName, String conveyanceName, String sourceName, String destinationName, String distance, String duration, String petrol, Double sourceLatitude, Double sourceLongitude, Double destinationLatitude, Double destinationLongitude) {
        this.id = id;
        this.routeName = routeName;
        this.conveyanceName = conveyanceName;
        this.sourceName = sourceName;
        this.destinationName = destinationName;
        this.distance = distance;
        this.duration = duration;
        this.petrol = petrol;
        this.sourceLatitude = sourceLatitude;
        this.sourceLongitude = sourceLongitude;
        this.destinationLatitude = destinationLatitude;
        this.destinationLongitude = destinationLongitude;
    }


    public HistoryQueryObject(String id) {
        this.id = id;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRouteName() {
        return routeName;
    }

    public void setRouteName(String routeName) {
        this.routeName = routeName;
    }

    public String getConveyanceName() {
        return conveyanceName;
    }

    public void setConveyanceName(String conveyanceName) {
        this.conveyanceName = conveyanceName;
    }

    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

    public String getDestinationName() {
        return destinationName;
    }

    public void setDestinationName(String destinationName) {
        this.destinationName = destinationName;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getPetrol() {
        return petrol;
    }

    public void setPetrol(String petrol) {
        this.petrol = petrol;
    }

    public Double getSourceLatitude() {
        return sourceLatitude;
    }

    public void setSourceLatitude(Double sourceLatitude) {
        this.sourceLatitude = sourceLatitude;
    }

    public Double getSourceLongitude() {
        return sourceLongitude;
    }

    public void setSourceLongitude(Double sourceLongitude) {
        this.sourceLongitude = sourceLongitude;
    }

    public Double getDestinationLatitude() {
        return destinationLatitude;
    }

    public void setDestinationLatitude(Double destinationLatitude) {
        this.destinationLatitude = destinationLatitude;
    }

    public Double getDestinationLongitude() {
        return destinationLongitude;
    }

    public void setDestinationLongitude(Double destinationLongitude) {
        this.destinationLongitude = destinationLongitude;
    }
}



