
package com.haris.navigato.JsonUtil.WikiUtil;

import java.io.Serializable;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WikiJson implements Serializable, Parcelable
{

    @SerializedName("batchcomplete")
    @Expose
    private String batchcomplete;
    @SerializedName("query")
    @Expose
    private Query query;
    public final static Parcelable.Creator<WikiJson> CREATOR = new Creator<WikiJson>() {


        @SuppressWarnings({
            "unchecked"
        })
        public WikiJson createFromParcel(Parcel in) {
            return new WikiJson(in);
        }

        public WikiJson[] newArray(int size) {
            return (new WikiJson[size]);
        }

    }
    ;
    private final static long serialVersionUID = 8602305422381676407L;

    protected WikiJson(Parcel in) {
        this.batchcomplete = ((String) in.readValue((String.class.getClassLoader())));
        this.query = ((Query) in.readValue((Query.class.getClassLoader())));
    }

    public WikiJson() {
    }

    public String getBatchcomplete() {
        return batchcomplete;
    }

    public void setBatchcomplete(String batchcomplete) {
        this.batchcomplete = batchcomplete;
    }

    public Query getQuery() {
        return query;
    }

    public void setQuery(Query query) {
        this.query = query;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(batchcomplete);
        dest.writeValue(query);
    }

    public int describeContents() {
        return  0;
    }

}
