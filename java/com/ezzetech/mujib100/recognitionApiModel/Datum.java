
package com.ezzetech.mujib100.recognitionApiModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Datum {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name_en")
    @Expose
    private String nameEn;
    @SerializedName("name_bn")
    @Expose
    private String nameBn;
    @SerializedName("profession_en")
    @Expose
    private String professionEn;
    @SerializedName("profession_bn")
    @Expose
    private String professionBn;
    @SerializedName("quote_en")
    @Expose
    private String quoteEn;
    @SerializedName("quote_bn")
    @Expose
    private String quoteBn;
    @SerializedName("link")
    @Expose
    private String link;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNameEn() {
        return nameEn;
    }

    public void setNameEn(String nameEn) {
        this.nameEn = nameEn;
    }

    public String getNameBn() {
        return nameBn;
    }

    public void setNameBn(String nameBn) {
        this.nameBn = nameBn;
    }

    public String getProfessionEn() {
        return professionEn;
    }

    public void setProfessionEn(String professionEn) {
        this.professionEn = professionEn;
    }

    public String getProfessionBn() {
        return professionBn;
    }

    public void setProfessionBn(String professionBn) {
        this.professionBn = professionBn;
    }

    public String getQuoteEn() {
        return quoteEn;
    }

    public void setQuoteEn(String quoteEn) {
        this.quoteEn = quoteEn;
    }

    public String getQuoteBn() {
        return quoteBn;
    }

    public void setQuoteBn(String quoteBn) {
        this.quoteBn = quoteBn;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

}
