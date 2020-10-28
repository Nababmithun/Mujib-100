
package com.ezzetech.mujib100.timeLineApiModel;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Datum implements Parcelable {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("year_en")
    @Expose
    private String yearEn;
    @SerializedName("year_bn")
    @Expose
    private String yearBn;
    @SerializedName("description_en")
    @Expose
    private String descriptionEn;
    @SerializedName("description_bn")
    @Expose
    private String descriptionBn;
    @SerializedName("timeline_images")
    @Expose
    private List<TimelineImage> timelineImages = null;

    protected Datum(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readInt();
        }
        yearEn = in.readString();
        yearBn = in.readString();
        descriptionEn = in.readString();
        descriptionBn = in.readString();
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

    public String getYearEn() {
        return yearEn;
    }

    public void setYearEn(String yearEn) {
        this.yearEn = yearEn;
    }

    public String getYearBn() {
        return yearBn;
    }

    public void setYearBn(String yearBn) {
        this.yearBn = yearBn;
    }

    public String getDescriptionEn() {
        return descriptionEn;
    }

    public void setDescriptionEn(String descriptionEn) {
        this.descriptionEn = descriptionEn;
    }

    public String getDescriptionBn() {
        return descriptionBn;
    }

    public void setDescriptionBn(String descriptionBn) {
        this.descriptionBn = descriptionBn;
    }

    public List<TimelineImage> getTimelineImages() {
        return timelineImages;
    }

    public void setTimelineImages(List<TimelineImage> timelineImages) {
        this.timelineImages = timelineImages;
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
        dest.writeString(yearEn);
        dest.writeString(yearBn);
        dest.writeString(descriptionEn);
        dest.writeString(descriptionBn);
    }
}
