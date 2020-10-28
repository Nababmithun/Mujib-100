package com.ezzetech.mujib100.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Mujib implements Parcelable {
    private int novelImage;

    public Mujib(int novelImage) {
        this.novelImage = novelImage;
    }

    protected Mujib(Parcel in) {
        novelImage = in.readInt();
    }

    public static final Creator<Mujib> CREATOR = new Creator<Mujib>() {
        @Override
        public Mujib createFromParcel(Parcel in) {
            return new Mujib(in);
        }

        @Override
        public Mujib[] newArray(int size) {
            return new Mujib[size];
        }
    };

    public int getNovelImage() {
        return novelImage;
    }

    public void setNovelImage(int novelImage) {
        this.novelImage = novelImage;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(novelImage);
    }
}
