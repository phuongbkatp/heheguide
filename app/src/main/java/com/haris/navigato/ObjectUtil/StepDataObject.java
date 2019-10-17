package com.haris.navigato.ObjectUtil;

import android.os.Parcel;
import android.os.Parcelable;

import com.akexorcist.googledirection.model.Coordination;

import java.io.Serializable;

/**
 * Created by hp on 5/27/2018.
 */

public class StepDataObject extends Object implements Serializable, Parcelable {
    private String stepDistance;
    private Double stepDistanceInMet;
    private String stepDuration;
    private Double stepDurationInSec;
    private String travelMode;
    private Coordination stepStartLoation;
    private Coordination stepEndLocation;
    private String stepSign;

    public StepDataObject(String stepDistance, Double stepDistanceInMet, String stepDuration, Double stepDurationInSec, String travelMode, Coordination stepStartLoation, Coordination stepEndLocation, String stepSign) {
        this.stepDistance = stepDistance;
        this.stepDistanceInMet = stepDistanceInMet;
        this.stepDuration = stepDuration;
        this.stepDurationInSec = stepDurationInSec;
        this.travelMode = travelMode;
        this.stepStartLoation = stepStartLoation;
        this.stepEndLocation = stepEndLocation;
        this.stepSign = stepSign;
    }


    public String getStepDistance() {
        return stepDistance;
    }

    public void setStepDistance(String stepDistance) {
        this.stepDistance = stepDistance;
    }

    public Double getStepDistanceInMet() {
        return stepDistanceInMet;
    }

    public void setStepDistanceInMet(Double stepDistanceInMet) {
        this.stepDistanceInMet = stepDistanceInMet;
    }

    public String getStepDuration() {
        return stepDuration;
    }

    public void setStepDuration(String stepDuration) {
        this.stepDuration = stepDuration;
    }

    public Double getStepDurationInSec() {
        return stepDurationInSec;
    }

    public void setStepDurationInSec(Double stepDurationInSec) {
        this.stepDurationInSec = stepDurationInSec;
    }

    public String getTravelMode() {
        return travelMode;
    }

    public void setTravelMode(String travelMode) {
        this.travelMode = travelMode;
    }

    public Coordination getStepStartLoation() {
        return stepStartLoation;
    }

    public void setStepStartLoation(Coordination stepStartLoation) {
        this.stepStartLoation = stepStartLoation;
    }

    public Coordination getStepEndLocation() {
        return stepEndLocation;
    }

    public void setStepEndLocation(Coordination stepEndLocation) {
        this.stepEndLocation = stepEndLocation;
    }

    public String getStepSign() {
        return stepSign;
    }

    public void setStepSign(String stepSign) {
        this.stepSign = stepSign;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.stepDistance);
        dest.writeValue(this.stepDistanceInMet);
        dest.writeString(this.stepDuration);
        dest.writeValue(this.stepDurationInSec);
        dest.writeString(this.travelMode);
        dest.writeParcelable(this.stepStartLoation, flags);
        dest.writeParcelable(this.stepEndLocation, flags);
        dest.writeString(this.stepSign);
    }

    protected StepDataObject(Parcel in) {
        this.stepDistance = in.readString();
        this.stepDistanceInMet = (Double) in.readValue(Double.class.getClassLoader());
        this.stepDuration = in.readString();
        this.stepDurationInSec = (Double) in.readValue(Double.class.getClassLoader());
        this.travelMode = in.readString();
        this.stepStartLoation = in.readParcelable(Coordination.class.getClassLoader());
        this.stepEndLocation = in.readParcelable(Coordination.class.getClassLoader());
        this.stepSign = in.readString();
    }

    public static final Parcelable.Creator<StepDataObject> CREATOR = new Parcelable.Creator<StepDataObject>() {
        @Override
        public StepDataObject createFromParcel(Parcel source) {
            return new StepDataObject(source);
        }

        @Override
        public StepDataObject[] newArray(int size) {
            return new StepDataObject[size];
        }
    };
}
