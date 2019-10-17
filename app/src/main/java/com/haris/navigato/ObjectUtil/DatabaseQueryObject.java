package com.haris.navigato.ObjectUtil;

import java.io.Serializable;

/**
 * Created by hp on 5/31/2018.
 */

public class DatabaseQueryObject extends Object implements Serializable {
    String id;
    String placeId;
    String placePicture;
    String placeName;
    String priceLevel;
    String placeRating;
    String placeType;
    String placeAddress;
    Double placeLatitude;
    Double placeLongitude;

    public DatabaseQueryObject() {

    }

    public DatabaseQueryObject(String id, String placePicture, String placeId, String placeName, String priceLevel, String placeRating, String placeType, String placeAddress, Double placeLatitude, Double placeLongitude) {
        this.id = id;
        this.placeId = placeId;
        this.placePicture = placePicture;
        this.placeName = placeName;
        this.priceLevel = priceLevel;
        this.placeRating = placeRating;
        this.placeType = placeType;
        this.placeAddress = placeAddress;
        this.placeLatitude = placeLatitude;
        this.placeLongitude = placeLongitude;
    }

    public DatabaseQueryObject(String id) {
        this.id = id;
    }

    public DatabaseQueryObject(String placeId, int extra) {
        this.placeId = placeId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPlaceId() {
        return placeId;
    }

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }

    public String getPlacePicture() {
        return placePicture;
    }

    public void setPlacePicture(String placePicture) {
        this.placePicture = placePicture;
    }

    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    public String getPriceLevel() {
        return priceLevel;
    }

    public void setPriceLevel(String priceLevel) {
        this.priceLevel = priceLevel;
    }

    public String getPlaceRating() {
        return placeRating;
    }

    public void setPlaceRating(String placeRating) {
        this.placeRating = placeRating;
    }

    public String getPlaceType() {
        return placeType;
    }

    public void setPlaceType(String placeType) {
        this.placeType = placeType;
    }

    public String getPlaceAddress() {
        return placeAddress;
    }

    public void setPlaceAddress(String placeAddress) {
        this.placeAddress = placeAddress;
    }

    public Double getPlaceLatitude() {
        return placeLatitude;
    }

    public void setPlaceLatitude(Double placeLatitude) {
        this.placeLatitude = placeLatitude;
    }

    public Double getPlaceLongitude() {
        return placeLongitude;
    }

    public void setPlaceLongitude(Double placeLongitude) {
        this.placeLongitude = placeLongitude;
    }
}
