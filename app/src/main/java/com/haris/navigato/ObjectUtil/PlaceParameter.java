package com.haris.navigato.ObjectUtil;

import com.haris.navigato.InterfaceUtil.ConnectivityCallback;
import com.haris.navigato.InterfaceUtil.DirectoryCallback;

/**
 * Created by hp on 5/21/2018.
 */

public class PlaceParameter {
    String placeId;
    String latitude;
    String longitude;
    String placeType;
    String radius;
    String keyword;
    String placeName;
    String placeMiles;
    String placeRating;
    String placePriceLevel;
    String nextPageToken;
    ConnectivityCallback connectivityCallback;
    DirectoryCallback callback;


    public PlaceParameter(String placeName, String latitude, String longitude, ConnectivityCallback connectivityCallback, DirectoryCallback callback) {
        this.placeName = placeName;
        this.latitude = latitude;
        this.longitude = longitude;
        this.connectivityCallback = connectivityCallback;
        this.callback = callback;

    }


    public PlaceParameter(String placeName, String latitude, String longitude, String nextPageToken, ConnectivityCallback connectivityCallback, DirectoryCallback callback) {
        this.placeName = placeName;
        this.latitude = latitude;
        this.longitude = longitude;
        this.nextPageToken = nextPageToken;
        this.connectivityCallback = connectivityCallback;
        this.callback = callback;

    }


    public PlaceParameter(String placeId, String placeType, String placeName, String placeMiles, String placeRating, String placePriceLevel) {
        this.placeId = placeId;
        this.placeType = placeType;
        this.placeName = placeName;
        this.placeMiles = placeMiles;
        this.placeRating = placeRating;
        this.placePriceLevel = placePriceLevel;
    }

    public PlaceParameter(String placeId, String latitude, String longitude, String placeType, String radius) {
        this.placeId = placeId;
        this.latitude = latitude;
        this.longitude = longitude;
        this.placeType = placeType;
        this.radius = radius;
    }


    public PlaceParameter(String placeId, String latitude, String longitude, String placeType, String radius, ConnectivityCallback connectivityCallback, DirectoryCallback callback) {
        this.placeId = placeId;
        this.latitude = latitude;
        this.longitude = longitude;
        this.placeType = placeType;
        this.radius = radius;
        this.connectivityCallback = connectivityCallback;
        this.callback = callback;
    }


    public PlaceParameter(String placeId, String latitude, String longitude, String placeType, String radius, String nextPageToken, ConnectivityCallback connectivityCallback, DirectoryCallback callback) {
        this.placeId = placeId;
        this.latitude = latitude;
        this.longitude = longitude;
        this.placeType = placeType;
        this.radius = radius;
        this.nextPageToken = nextPageToken;
        this.connectivityCallback = connectivityCallback;
        this.callback = callback;
    }

    public String getPlaceId() {
        return placeId;
    }

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getPlaceType() {
        return placeType;
    }

    public void setPlaceType(String placeType) {
        this.placeType = placeType;
    }

    public String getRadius() {
        return radius;
    }

    public void setRadius(String radius) {
        this.radius = radius;
    }

    public ConnectivityCallback getConnectivityCallback() {
        return connectivityCallback;
    }

    public void setConnectivityCallback(ConnectivityCallback connectivityCallback) {
        this.connectivityCallback = connectivityCallback;
    }

    public DirectoryCallback getCallback() {
        return callback;
    }

    public void setCallback(DirectoryCallback callback) {
        this.callback = callback;
    }

    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    public String getPlaceMiles() {
        return placeMiles;
    }

    public void setPlaceMiles(String placeMiles) {
        this.placeMiles = placeMiles;
    }

    public String getPlaceRating() {
        return placeRating;
    }

    public void setPlaceRating(String placeRating) {
        this.placeRating = placeRating;
    }

    public String getPlacePriceLevel() {
        return placePriceLevel;
    }

    public void setPlacePriceLevel(String placePriceLevel) {
        this.placePriceLevel = placePriceLevel;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getNextPageToken() {
        return nextPageToken;
    }

    public void setNextPageToken(String nextPageToken) {
        this.nextPageToken = nextPageToken;
    }
}
