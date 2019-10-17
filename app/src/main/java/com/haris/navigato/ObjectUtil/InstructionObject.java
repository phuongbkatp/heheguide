package com.haris.navigato.ObjectUtil;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.maps.model.LatLng;
import com.haris.navigato.Utility.Utility;

import java.util.ArrayList;

public class InstructionObject implements Parcelable {
    String instruction;
    double distance;
    double duration;
    double latitude;
    double longitude;
    boolean isBanner;
    String type;
    String modifier;
    double startLatitude;
    double startLongitude;
    double endLatitude;
    double endLongitude;
    String bannerInstruction;
    ArrayList<LatLng> location = new ArrayList<>();
    String action;

    public InstructionObject(ArrayList<LatLng> location, String action) {
        this.location = location;
        this.action = action;
    }

    public InstructionObject(String instruction, double latitude, double longitude) {
        this.instruction = instruction;
        this.latitude = latitude;
        this.longitude = longitude;

        Utility.Logger("Instruction Object", instruction + " " + latitude + " " + longitude);

    }

    public InstructionObject(String instruction, double distance, double duration, double latitude, double longitude, boolean isBanner) {
        this.instruction = instruction;
        this.distance = distance;
        this.duration = duration;
        this.latitude = latitude;
        this.longitude = longitude;
        this.isBanner = isBanner;
    }

    public InstructionObject(String instruction, double distance, double duration, double latitude, double longitude, boolean isBanner, String type, String modifier) {
        this.instruction = instruction;
        this.distance = distance;
        this.duration = duration;
        this.latitude = latitude;
        this.longitude = longitude;
        this.isBanner = isBanner;
        this.type = type;
        this.modifier = modifier;
    }

    public InstructionObject(String instruction, double distance, double duration, double latitude, double longitude, boolean isBanner, String type, String modifier, double startLatitude, double startLongitude, double endLatitude, double endLongitude) {
        this.instruction = instruction;
        this.distance = distance;
        this.duration = duration;
        this.latitude = latitude;
        this.longitude = longitude;
        this.isBanner = isBanner;
        this.type = type;
        this.modifier = modifier;
        this.startLatitude = startLatitude;
        this.startLongitude = startLongitude;
        this.endLatitude = endLatitude;
        this.endLongitude = endLongitude;
    }

    public InstructionObject(String instruction, double distance, double duration, double latitude, double longitude, boolean isBanner, String type, String modifier, double startLatitude, double startLongitude, double endLatitude, double endLongitude, String bannerInstruction) {
        this.instruction = instruction;
        this.distance = distance;
        this.duration = duration;
        this.latitude = latitude;
        this.longitude = longitude;
        this.isBanner = isBanner;
        this.type = type;
        this.modifier = modifier;
        this.startLatitude = startLatitude;
        this.startLongitude = startLongitude;
        this.endLatitude = endLatitude;
        this.endLongitude = endLongitude;
        this.bannerInstruction = bannerInstruction;
    }

    public String getInstruction() {
        return instruction;
    }

    public void setInstruction(String instruction) {
        this.instruction = instruction;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public double getDuration() {
        return duration;
    }

    public void setDuration(double duration) {
        this.duration = duration;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public boolean isBanner() {
        return isBanner;
    }

    public void setBanner(boolean banner) {
        isBanner = banner;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getModifier() {
        return modifier;
    }

    public void setModifier(String modifier) {
        this.modifier = modifier;
    }

    public double getStartLatitude() {
        return startLatitude;
    }

    public void setStartLatitude(double startLatitude) {
        this.startLatitude = startLatitude;
    }

    public double getStartLongitude() {
        return startLongitude;
    }

    public void setStartLongitude(double startLongitude) {
        this.startLongitude = startLongitude;
    }

    public double getEndLatitude() {
        return endLatitude;
    }

    public void setEndLatitude(double endLatitude) {
        this.endLatitude = endLatitude;
    }

    public double getEndLongitude() {
        return endLongitude;
    }

    public void setEndLongitude(double endLongitude) {
        this.endLongitude = endLongitude;
    }

    public String getBannerInstruction() {
        return bannerInstruction;
    }

    public void setBannerInstruction(String bannerInstruction) {
        this.bannerInstruction = bannerInstruction;
    }

    public ArrayList<LatLng> getLocation() {
        return location;
    }

    public void setLocation(ArrayList<LatLng> location) {
        this.location = location;
    }


    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.instruction);
        dest.writeDouble(this.distance);
        dest.writeDouble(this.duration);
        dest.writeDouble(this.latitude);
        dest.writeDouble(this.longitude);
        dest.writeByte(this.isBanner ? (byte) 1 : (byte) 0);
        dest.writeString(this.type);
        dest.writeString(this.modifier);
        dest.writeDouble(this.startLatitude);
        dest.writeDouble(this.startLongitude);
        dest.writeDouble(this.endLatitude);
        dest.writeDouble(this.endLongitude);
        dest.writeString(this.bannerInstruction);
        dest.writeTypedList(this.location);
        dest.writeString(this.action);
    }

    protected InstructionObject(Parcel in) {
        this.instruction = in.readString();
        this.distance = in.readDouble();
        this.duration = in.readDouble();
        this.latitude = in.readDouble();
        this.longitude = in.readDouble();
        this.isBanner = in.readByte() != 0;
        this.type = in.readString();
        this.modifier = in.readString();
        this.startLatitude = in.readDouble();
        this.startLongitude = in.readDouble();
        this.endLatitude = in.readDouble();
        this.endLongitude = in.readDouble();
        this.bannerInstruction = in.readString();
        this.location = in.createTypedArrayList(LatLng.CREATOR);
        this.action = in.readString();
    }

    public static final Creator<InstructionObject> CREATOR = new Creator<InstructionObject>() {
        @Override
        public InstructionObject createFromParcel(Parcel source) {
            return new InstructionObject(source);
        }

        @Override
        public InstructionObject[] newArray(int size) {
            return new InstructionObject[size];
        }
    };
}
