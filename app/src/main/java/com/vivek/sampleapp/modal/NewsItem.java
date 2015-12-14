package com.vivek.sampleapp.modal;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by vivek-pc on 12/12/2015.
 */
@JsonIgnoreProperties(ignoreUnknown=true)
public class NewsItem {

    String NewsItemId;
    String HeadLine;
    String Agency;
    String DateLine;
    String WebURL;
    String Caption;
    Image Image;
    String Keywords;
    String Story;
    String DetailFeed;
    NewsItem[] HomeRelated;

    public String getNewsItemId() {
        return NewsItemId;
    }
    @JsonProperty("NewsItemId")
    public void setNewsItemId(String newsItemId) {
        NewsItemId = newsItemId;
    }

    public String getHeadLine() {
        return HeadLine;
    }

    @JsonProperty("HeadLine")
    public void setHeadLine(String headLine) {
        HeadLine = headLine;
    }


    public String getAgency() {
        return Agency;
    }

    @JsonProperty("Agency")
    public void setAgency(String agency) {
        Agency = agency;
    }

    public String getDateLine() {
        return DateLine;
    }

    @JsonProperty("DateLine")
    public void setDateLine(String dateLine) {
        DateLine = dateLine;
    }

    public String getWebURL() {
        return WebURL;
    }

    @JsonProperty("WebURL")
    public void setWebURL(String webURL) {
        WebURL = webURL;
    }

    public String getCaption() {
        return Caption;
    }

    @JsonProperty("Caption")
    public void setCaption(String caption) {
        Caption = caption;
    }

    public com.vivek.sampleapp.modal.Image getImage() {
        return Image;
    }

    @JsonProperty("Image")
    public void setImage(com.vivek.sampleapp.modal.Image image) {
        Image = image;
    }

    public String getKeywords() {
        return Keywords;
    }

    @JsonProperty("Keywords")
    public void setKeywords(String keywords) {
        Keywords = keywords;
    }

    public String getStory() {
        return Story;
    }

    @JsonProperty("Story")
    public void setStory(String story) {
        Story = story;
    }

    public String getDetailFeed() {
        return DetailFeed;
    }

    @JsonProperty("DetailFeed")
    public void setDetailFeed(String detailFeed) {
        DetailFeed = detailFeed;
    }


//    @JsonProperty("HomeRelated")
    public void setHomeRelated(NewsItem[] homeRelated) {
        HomeRelated = homeRelated;
    }

    public NewsItem[] getHomeRelated() {
        return HomeRelated;
    }
}
