
package com.ezzetech.mujib100.speechesApiModel;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Datum implements Parcelable {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("caption_en")
    @Expose
    private String captionEn;
    @SerializedName("caption_bn")
    @Expose
    private String captionBn;
    @SerializedName("link")
    @Expose
    private String link;
    @SerializedName("v_link")
    @Expose
    private String vLink;

    protected Datum(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readInt();
        }
        captionEn = in.readString();
        captionBn = in.readString();
        link = in.readString();
        vLink = in.readString();
    }

    public static final Creator<Datum> CREATOR = new Creator<Datum>() {
        @Override
        public Datum createFromParcel(Parcel in) {
            return new Datum(in);
        }

        @Override
        public Datum[] newArray(int size) {
            return new Datum[size];
        }
    };

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCaptionEn() {
        return captionEn;
    }

    public void setCaptionEn(String captionEn) {
        this.captionEn = captionEn;
    }

    public String getCaptionBn() {
        return captionBn;
    }

    public void setCaptionBn(String captionBn) {
        this.captionBn = captionBn;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getVLink() {
        return vLink;
    }

    public void setVLink(String vLink) {
        this.vLink = vLink;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (id == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(id);
        }
        dest.writeString(captionEn);
        dest.writeString(captionBn);
        dest.writeString(link);
        dest.writeString(vLink);
    }
}
