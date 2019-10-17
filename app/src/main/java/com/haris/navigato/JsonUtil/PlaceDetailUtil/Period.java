
package com.haris.navigato.JsonUtil.PlaceDetailUtil;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Period implements Parcelable
{

    @SerializedName("open")
    @Expose
    private Open open;
    public final static Parcelable.Creator<Period> CREATOR = new Creator<Period>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Period createFromParcel(Parcel in) {
            return new Period(in);
        }

        public Period[] newArray(int size) {
            return (new Period[size]);
        }

    }
    ;

    protected Period(Parcel in) {
        this.open = ((Open) in.readValue((Open.class.getClassLoader())));
    }

    public Period() {
    }

    public Open getOpen() {
        return open;
    }

    public void setOpen(Open open) {
        this.open = open;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(open);
    }

    public int describeContents() {
        return  0;
    }

}
