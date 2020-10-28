
package com.ezzetech.mujib100.stempApiModel;

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
    @SerializedName("use_year_en")
    @Expose
    private String useYearEn;
    @SerializedName("use_year_bn")
    @Expose
    private String useYearBn;
    @SerializedName("link")
    @Expose
    private String link;

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

    public String getUseYearEn() {
        return useYearEn;
    }

    public void setUseYearEn(String useYearEn) {
        this.useYearEn = useYearEn;
    }

    public String getUseYearBn() {
        return useYearBn;
    }

    public void setUseYearBn(String useYearBn) {
        this.useYearBn = useYearBn;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

}
