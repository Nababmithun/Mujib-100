
package com.ezzetech.mujib100.pressRelease;

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
    @SerializedName("link")
    @Expose
    private String link;
    @SerializedName("news_url")
    @Expose
    private String newsUrl;
    @SerializedName("date")
    @Expose
    private String date;

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

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getNewsUrl() {
        return newsUrl;
    }

    public void setNewsUrl(String newsUrl) {
        this.newsUrl = newsUrl;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

}
