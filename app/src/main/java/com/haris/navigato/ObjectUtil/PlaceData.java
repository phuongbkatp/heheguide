package com.haris.navigato.ObjectUtil;

import java.io.Serializable;

/**
 * Created by hp on 5/20/2018.
 */

public class PlaceData extends Object implements Serializable {
    String placeName;
    String placeTag;
    int placeIcon;

    public PlaceData(String placeName, String placeTag, int placeIcon) {
        this.placeName = placeName;
        this.placeTag = placeTag;
        this.placeIcon = placeIcon;
    }

    public PlaceData(String placeName, int placeIcon) {
        this.placeName = placeName;
        this.placeIcon = placeIcon;
    }


    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    public int getPlaceIcon() {
        return placeIcon;
    }

    public void setPlaceIcon(int placeIcon) {
        this.placeIcon = placeIcon;
    }

    public String getPlaceTag() {
        return placeTag;
    }

    public void setPlaceTag(String placeTag) {
        this.placeTag = placeTag;
    }
}
