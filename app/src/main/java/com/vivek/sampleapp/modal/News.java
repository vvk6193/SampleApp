package com.vivek.sampleapp.modal;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Created by vivek-pc on 12/12/2015.
 */
@JsonIgnoreProperties(ignoreUnknown=true)
public class News {
    Pagination Pagination;
    List<NewsItem> NewsItem;

    public com.vivek.sampleapp.modal.Pagination getPagination() {
        return Pagination;
    }

    @JsonProperty("Pagination")
    public void setPagination(com.vivek.sampleapp.modal.Pagination pagination) {
        Pagination = pagination;
    }

    public List<com.vivek.sampleapp.modal.NewsItem> getNewsItem() {
        return NewsItem;
    }
    @JsonProperty("NewsItem")
    public void setNewsItem(List<com.vivek.sampleapp.modal.NewsItem> newsItem) {
        NewsItem = newsItem;
    }
}
