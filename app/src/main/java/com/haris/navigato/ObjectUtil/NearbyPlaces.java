package com.haris.navigato.ObjectUtil;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by hp on 5/21/2018.
 */

public class NearbyPlaces extends Object implements Serializable {
    String id;
    String placeName;
    String placeId;
    String placeAddress;
    String placeRating;
    Double latitude;
    Double longitude;
    boolean isOpenNow;
    String formattedAddress;
    String phone;
    String mapUrl;
    String webUrl;
    String placeType;
    String priceLevel;
    String nextPageToken;
    ArrayList<Object> photoArrayList = new ArrayList<>();
    ArrayList<Object> reviewArraylist = new ArrayList<>();


    public NearbyPlaces(String id, String placeId, String placeName, String placeAddress, String placeRating, Double latitude, Double longitude, boolean isOpenNow, ArrayList<Object> photoArrayList) {
        this.id = id;
        this.placeName = placeName;
        this.placeId = placeId;
        this.placeAddress = placeAddress;
        this.placeRating = placeRating;
        this.latitude = latitude;
        this.longitude = longitude;
        this.isOpenNow = isOpenNow;
        this.photoArrayList = photoArrayList;
    }


    public NearbyPlaces(String id, String placeId, String placeName, String placeAddress, String placeRating, Double latitude, Double longitude, boolean isOpenNow, ArrayList<Object> photoArrayList, String placeType, String priceLevel) {
        this.id = id;
        this.placeName = placeName;
        this.placeId = placeId;
        this.placeAddress = placeAddress;
        this.placeRating = placeRating;
        this.latitude = latitude;
        this.longitude = longitude;
        this.isOpenNow = isOpenNow;
        this.photoArrayList = photoArrayList;
        this.placeType = placeType;
        this.priceLevel = priceLevel;
    }


    public NearbyPlaces(String id, String placeId, String placeName, String placeAddress, String placeRating, Double latitude, Double longitude, boolean isOpenNow, ArrayList<Object> photoArrayList, String placeType, String priceLevel, String nextPageToken) {
        this.id = id;
        this.placeName = placeName;
        this.placeId = placeId;
        this.placeAddress = placeAddress;
        this.placeRating = placeRating;
        this.latitude = latitude;
        this.longitude = longitude;
        this.isOpenNow = isOpenNow;
        this.photoArrayList = photoArrayList;
        this.placeType = placeType;
        this.priceLevel = priceLevel;
        this.nextPageToken = nextPageToken;
    }


    public NearbyPlaces(String formattedAddress, String phone, String mapUrl, String webUrl, ArrayList<Object> reviewArraylist) {
        this.formattedAddress = formattedAddress;
        this.phone = phone;
        this.mapUrl = mapUrl;
        this.webUrl = webUrl;
        this.reviewArraylist = reviewArraylist;
    }

    public NearbyPlaces(String formattedAddress, String phone, String mapUrl, String webUrl, ArrayList<Object> reviewArraylist, ArrayList<Object> photoArrayList) {
        this.formattedAddress = formattedAddress;
        this.phone = phone;
        this.mapUrl = mapUrl;
        this.webUrl = webUrl;
        this.reviewArraylist = reviewArraylist;
        this.photoArrayList = photoArrayList;
    }


    public NearbyPlaces(String formattedAddress, String phone, String mapUrl, String webUrl, ArrayList<Object> reviewArraylist, ArrayList<Object> photoArrayList, String id, String placeId, String placeName, String placeAddress, String placeRating, Double latitude, Double longitude, boolean isOpenNow, String placeType, String priceLevel, String nextPageToken) {
        this.formattedAddress = formattedAddress;
        this.phone = phone;
        this.mapUrl = mapUrl;
        this.webUrl = webUrl;
        this.reviewArraylist = reviewArraylist;
        this.photoArrayList = photoArrayList;
        this.id = id;
        this.placeName = placeName;
        this.placeId = placeId;
        this.placeAddress = placeAddress;
        this.placeRating = placeRating;
        this.latitude = latitude;
        this.longitude = longitude;
        this.isOpenNow = isOpenNow;
        this.placeType = placeType;
        this.priceLevel = priceLevel;
        this.nextPageToken = nextPageToken;
    }



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    public String getPlaceId() {
        return placeId;
    }

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }

    public String getPlaceAddress() {
        return placeAddress;
    }

    public void setPlaceAddress(String placeAddress) {
        this.placeAddress = placeAddress;
    }

    public String getPlaceRating() {
        return placeRating;
    }

    public void setPlaceRating(String placeRating) {
        this.placeRating = placeRating;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public boolean isOpenNow() {
        return isOpenNow;
    }

    public void setOpenNow(boolean openNow) {
        isOpenNow = openNow;
    }

    public ArrayList<Object> getPhotoArrayList() {
        return photoArrayList;
    }

    public void setPhotoArrayList(ArrayList<Object> photoArrayList) {
        this.photoArrayList = photoArrayList;
    }

    public ArrayList<Object> getReviewArraylist() {
        return reviewArraylist;
    }

    public void setReviewArraylist(ArrayList<Object> reviewArraylist) {
        this.reviewArraylist = reviewArraylist;
    }

    public String getFormattedAddress() {
        return formattedAddress;
    }

    public void setFormattedAddress(String formattedAddress) {
        this.formattedAddress = formattedAddress;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMapUrl() {
        return mapUrl;
    }

    public void setMapUrl(String mapUrl) {
        this.mapUrl = mapUrl;
    }

    public String getWebUrl() {
        return webUrl;
    }

    public void setWebUrl(String webUrl) {
        this.webUrl = webUrl;
    }

    public String getPlaceType() {
        return placeType;
    }

    public void setPlaceType(String placeType) {
        this.placeType = placeType;
    }

    public String getPriceLevel() {
        return priceLevel;
    }

    public void setPriceLevel(String priceLevel) {
        this.priceLevel = priceLevel;
    }

    public String getNextPageToken() {
        return nextPageToken;
    }

    public void setNextPageToken(String nextPageToken) {
        this.nextPageToken = nextPageToken;
    }



    /* @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.placeName);
        dest.writeString(this.placeId);
        dest.writeString(this.placeAddress);
        dest.writeString(this.placeRating);
        dest.writeValue(this.latitude);
        dest.writeValue(this.longitude);
        dest.writeByte(this.isOpenNow ? (byte) 1 : (byte) 0);
        dest.writeString(this.formattedAddress);
        dest.writeString(this.phone);
        dest.writeString(this.mapUrl);
        dest.writeString(this.webUrl);
        dest.writeString(this.placeType);
        dest.writeString(this.priceLevel);
        dest.writeList(this.photoArrayList);
        //dest.writeList(this.reviewArraylist);
    }

    protected NearbyPlaces(Parcel in) {
        this.id = in.readString();
        this.placeName = in.readString();
        this.placeId = in.readString();
        this.placeAddress = in.readString();
        this.placeRating = in.readString();
        this.latitude = (Double) in.readValue(Double.class.getClassLoader());
        this.longitude = (Double) in.readValue(Double.class.getClassLoader());
        this.isOpenNow = in.readByte() != 0;
        this.formattedAddress = in.readString();
        this.phone = in.readString();
        this.mapUrl = in.readString();
        this.webUrl = in.readString();
        this.placeType = in.readString();
        this.priceLevel = in.readString();
        ////this.photoArrayList = new ArrayList<Object>();
        in.readArrayList(Object.class.getClassLoader());
        ///this.reviewArraylist = new ArrayList<Object>();
        //in.readList(this.reviewArraylist, Photo.class.getClassLoader());
    }

    public static final Creator<NearbyPlaces> CREATOR = new Creator<NearbyPlaces>() {
        @Override
        public NearbyPlaces createFromParcel(Parcel source) {
            return new NearbyPlaces(source);
        }

        @Override
        public NearbyPlaces[] newArray(int size) {
            return new NearbyPlaces[size];
        }
    };*/
}
