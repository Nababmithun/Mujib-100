
package com.ezzetech.mujib100.timeLineApiModel;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TimelineImage implements Parcelable {

    @SerializedName("timeline_id")
    @Expose
    private String timelineId;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("link")
    @Expose
    private String link;

    protected TimelineImage(Parcel in) {
        timelineId = in.readString();
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readInt();
        }
        link = in.readString();
    }

    public static final Creator<TimelineImage> CREATOR = new Creator<TimelineImage>() {
        @Override
        public TimelineImage createFromParcel(Parcel in) {
            return new TimelineImage(in);
        }

        @Override
        public TimelineImage[] newArray(int size) {
            return new TimelineImage[size];
        }
    };

    public String getTimelineId() {
        return timelineId;
    }

    public void setTimelineId(String timelineId) {
        this.timelineId = timelineId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(timelineId);
        if (id == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(id);
        }
        dest.writeString(link);
    }
}
