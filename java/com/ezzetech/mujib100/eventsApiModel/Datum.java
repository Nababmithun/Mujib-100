
package com.ezzetech.mujib100.eventsApiModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Datum {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("title_en")
    @Expose
    private String titleEn;
    @SerializedName("title_bn")
    @Expose
    private String titleBn;
    @SerializedName("event_date")
    @Expose
    private String eventDate;
    @SerializedName("location")
    @Expose
    private String location;
    @SerializedName("bn_location")
    @Expose
    private String bnLocation;
    @SerializedName("link")
    @Expose
    private String link;
    @SerializedName("reg_link")
    @Expose
    private String regLink;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitleEn() {
        return titleEn;
    }

    public void setTitleEn(String titleEn) {
        this.titleEn = titleEn;
    }

    public String getTitleBn() {
        return titleBn;
    }

    public void setTitleBn(String titleBn) {
        this.titleBn = titleBn;
    }

    public String getEventDate() {
        return eventDate;
    }

    public void setEventDate(String eventDate) {
        this.eventDate = eventDate;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getBnLocation() {
        return bnLocation;
    }

    public void setBnLocation(String bnLocation) {
        this.bnLocation = bnLocation;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getRegLink() {
        return regLink;
    }

    public void setRegLink(String regLink) {
        this.regLink = regLink;
    }

}
