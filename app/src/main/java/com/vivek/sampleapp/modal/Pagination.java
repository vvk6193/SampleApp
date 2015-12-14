package com.vivek.sampleapp.modal;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by vivek-pc on 12/12/2015.
 */
@JsonIgnoreProperties(ignoreUnknown=true)
public class Pagination {
    int TotalPages;
    int PageNo;
    int PerPage;
    String WebURL;

    public int getTotalPages() {
        return TotalPages;
    }

    @JsonProperty("TotalPages")
    public void setTotalPages(int totalPages) {
        TotalPages = totalPages;
    }

    public int getPageNo() {
        return PageNo;
    }

    @JsonProperty("PageNo")
    public void setPageNo(int pageNo) {
        PageNo = pageNo;
    }

    public int getPerPage() {
        return PerPage;
    }

    @JsonProperty("PerPage")
    public void setPerPage(int perPage) {
        PerPage = perPage;
    }

    public String getWebURL() {
        return WebURL;
    }

    @JsonProperty("WebURL")
    public void setWebURL(String webURL) {
        WebURL = webURL;
    }
}
