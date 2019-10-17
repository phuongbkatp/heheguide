package com.haris.navigato.ObjectUtil;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.maps.model.LatLng;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by hp on 5/27/2018.
 */

public class RouteDataObject extends Object implements Serializable, Parcelable {
    String distance;
    String duration;
    String distanceInMeter;
    String durationInSeconds;
    String transportMode;
    ArrayList<StepDataObject> dataObjectArrayList = new ArrayList<>();
    ArrayList<LatLng> directionArraylist = new ArrayList<>();

    public RouteDataObject(String distance, String duration) {
        this.distance = distance;
        this.duration = duration;
    }

    public RouteDataObject(String distance, String duration, String distanceInMeter, String durationInSeconds) {
        this.distance = distance;
        this.duration = duration;
        this.distanceInMeter = distanceInMeter;
        this.durationInSeconds = durationInSeconds;
    }

    public RouteDataObject(String distance, String duration, String distanceInMeter, String durationInSeconds, ArrayList<StepDataObject> dataObjectArrayList) {
        this.distance = distance;
        this.duration = duration;
        this.distanceInMeter = distanceInMeter;
        this.durationInSeconds = durationInSeconds;
        this.dataObjectArrayList = dataObjectArrayList;
    }

    public RouteDataObject(String distance, String duration, String distanceInMeter, String durationInSeconds, String transportMode, ArrayList<StepDataObject> dataObjectArrayList, ArrayList<LatLng> directionArraylist) {
        this.distance = distance;
        this.duration = duration;
        this.distanceInMeter = distanceInMeter;
        this.durationInSeconds = durationInSeconds;
        this.transportMode = transportMode;
        this.dataObjectArrayList = dataObjectArrayList;
        this.directionArraylist = directionArraylist;
    }

    public RouteDataObject(String distance, String duration, String distanceInMeter, String durationInSeconds, ArrayList<StepDataObject> dataObjectArrayList, ArrayList<LatLng> directionArraylist) {
        this.distance = distance;
        this.duration = duration;
        this.distanceInMeter = distanceInMeter;
        this.durationInSeconds = durationInSeconds;
        this.dataObjectArrayList = dataObjectArrayList;
        this.directionArraylist = directionArraylist;
    }

    public String getDistanceInMeter() {
        return distanceInMeter;
    }

    public void setDistanceInMeter(String distanceInMeter) {
        this.distanceInMeter = distanceInMeter;
    }

    public String getDurationInSeconds() {
        return durationInSeconds;
    }

    public void setDurationInSeconds(String durationInSeconds) {
        this.durationInSeconds = durationInSeconds;
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

    public ArrayList<StepDataObject> getDataObjectArrayList() {
        return dataObjectArrayList;
    }

    public void setDataObjectArrayList(ArrayList<StepDataObject> dataObjectArrayList) {
        this.dataObjectArrayList = dataObjectArrayList;
    }

    public ArrayList<LatLng> getDirectionArraylist() {
        return directionArraylist;
    }

    public void setDirectionArraylist(ArrayList<LatLng> directionArraylist) {
        this.directionArraylist = directionArraylist;
    }


    public String getTransportMode() {
        return transportMode;
    }

    public void setTransportMode(String transportMode) {
        this.transportMode = transportMode;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.distance);
        dest.writeString(this.duration);
        dest.writeString(this.distanceInMeter);
        dest.writeString(this.durationInSeconds);
        dest.writeString(this.transportMode);
        dest.writeTypedList(this.dataObjectArrayList);
        dest.writeTypedList(this.directionArraylist);
    }

    protected RouteDataObject(Parcel in) {
        this.distance = in.readString();
        this.duration = in.readString();
        this.distanceInMeter = in.readString();
        this.durationInSeconds = in.readString();
        this.transportMode = in.readString();
        this.dataObjectArrayList = in.createTypedArrayList(StepDataObject.CREATOR);
        this.directionArraylist = in.createTypedArrayList(LatLng.CREATOR);
    }

    public static final Creator<RouteDataObject> CREATOR = new Creator<RouteDataObject>() {
        @Override
        public RouteDataObject createFromParcel(Parcel source) {
            return new RouteDataObject(source);
        }

        @Override
        public RouteDataObject[] newArray(int size) {
            return new RouteDataObject[size];
        }
    };
}
