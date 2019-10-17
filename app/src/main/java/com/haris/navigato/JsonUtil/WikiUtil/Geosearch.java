
package com.haris.navigato.JsonUtil.WikiUtil;

import java.io.Serializable;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Geosearch implements Serializable, Parcelable
{

    @SerializedName("pageid")
    @Expose
    private Integer pageid;
    @SerializedName("ns")
    @Expose
    private Integer ns;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("lat")
    @Expose
    private Double lat;
    @SerializedName("lon")
    @Expose
    private Double lon;
    @SerializedName("dist")
    @Expose
    private Double dist;
    @SerializedName("primary")
    @Expose
    private String primary;
    public final static Parcelable.Creator<Geosearch> CREATOR = new Creator<Geosearch>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Geosearch createFromParcel(Parcel in) {
            return new Geosearch(in);
        }

        public Geosearch[] newArray(int size) {
            return (new Geosearch[size]);
        }

    }
    ;
    private final static long serialVersionUID = 3388384892268509928L;

    protected Geosearch(Parcel in) {
        this.pageid = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.ns = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.title = ((String) in.readValue((String.class.getClassLoader())));
        this.lat = ((Double) in.readValue((Double.class.getClassLoader())));
        this.lon = ((Double) in.readValue((Double.class.getClassLoader())));
        this.dist = ((Double) in.readValue((Double.class.getClassLoader())));
        this.primary = ((String) in.readValue((String.class.getClassLoader())));
    }

    public Geosearch() {
    }

    public Integer getPageid() {
        return pageid;
    }

    public void setPageid(Integer pageid) {
        this.pageid = pageid;
    }

    public Integer getNs() {
        return ns;
    }

    public void setNs(Integer ns) {
        this.ns = ns;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLon() {
        return lon;
    }

    public void setLon(Double lon) {
        this.lon = lon;
    }

    public Double getDist() {
        return dist;
    }

    public void setDist(Double dist) {
        this.dist = dist;
    }

    public String getPrimary() {
        return primary;
    }

    public void setPrimary(String primary) {
        this.primary = primary;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(pageid);
        dest.writeValue(ns);
        dest.writeValue(title);
        dest.writeValue(lat);
        dest.writeValue(lon);
        dest.writeValue(dist);
        dest.writeValue(primary);
    }

    public int describeContents() {
        return  0;
    }

}
