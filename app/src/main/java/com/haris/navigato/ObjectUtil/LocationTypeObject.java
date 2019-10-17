package com.haris.navigato.ObjectUtil;

/**
 * Created by hp on 5/24/2018.
 */

public class LocationTypeObject {
    String placeName;
    String placeTag;

    public LocationTypeObject(String placeName, String placeTag) {
        this.placeName = placeName;
        this.placeTag = placeTag;
    }


    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    public String getPlaceTag() {
        return placeTag;
    }

    public void setPlaceTag(String placeTag) {
        this.placeTag = placeTag;
    }
}
