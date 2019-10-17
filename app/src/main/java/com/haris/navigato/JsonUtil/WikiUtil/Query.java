
package com.haris.navigato.JsonUtil.WikiUtil;

import java.io.Serializable;
import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Query implements Serializable, Parcelable
{

    @SerializedName("geosearch")
    @Expose
    private List<Geosearch> geosearch = null;
    public final static Parcelable.Creator<Query> CREATOR = new Creator<Query>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Query createFromParcel(Parcel in) {
            return new Query(in);
        }

        public Query[] newArray(int size) {
            return (new Query[size]);
        }

    }
    ;
    private final static long serialVersionUID = -5246151103026613815L;

    protected Query(Parcel in) {
        in.readList(this.geosearch, (com.haris.navigato.JsonUtil.WikiUtil.Geosearch.class.getClassLoader()));
    }

    public Query() {
    }

    public List<Geosearch> getGeosearch() {
        return geosearch;
    }

    public void setGeosearch(List<Geosearch> geosearch) {
        this.geosearch = geosearch;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(geosearch);
    }

    public int describeContents() {
        return  0;
    }

}
