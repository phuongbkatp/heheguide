package com.haris.navigato.ObjectUtil;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

public class ARObject implements Parcelable {
    private ArrayList<InstructionObject> instructionObjects = new ArrayList<>();
    private ArrayList<LatLng> pointerArraylist = new ArrayList<>();


    public ARObject(ArrayList<InstructionObject> instructionObjects) {
        this.instructionObjects = instructionObjects;
    }

    public ARObject(ArrayList<InstructionObject> instructionObjects, ArrayList<LatLng> pointerArraylist) {
        this.instructionObjects = instructionObjects;
        this.pointerArraylist = pointerArraylist;
    }

    public ArrayList<InstructionObject> getInstructionObjects() {
        return instructionObjects;
    }

    public void setInstructionObjects(ArrayList<InstructionObject> instructionObjects) {
        this.instructionObjects = instructionObjects;
    }

    public ArrayList<LatLng> getPointerArraylist() {
        return pointerArraylist;
    }

    public void setPointerArraylist(ArrayList<LatLng> pointerArraylist) {
        this.pointerArraylist = pointerArraylist;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(this.instructionObjects);
        dest.writeTypedList(this.pointerArraylist);
    }

    protected ARObject(Parcel in) {
        this.instructionObjects = in.createTypedArrayList(InstructionObject.CREATOR);
        this.pointerArraylist = in.createTypedArrayList(LatLng.CREATOR);
    }

    public static final Parcelable.Creator<ARObject> CREATOR = new Parcelable.Creator<ARObject>() {
        @Override
        public ARObject createFromParcel(Parcel source) {
            return new ARObject(source);
        }

        @Override
        public ARObject[] newArray(int size) {
            return new ARObject[size];
        }
    };
}
