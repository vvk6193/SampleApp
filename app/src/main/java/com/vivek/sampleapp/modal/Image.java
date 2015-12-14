package com.vivek.sampleapp.modal;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by vivek-pc on 12/12/2015.
 */
@JsonIgnoreProperties(ignoreUnknown=true)
public class Image {
    String Photo;
    String Thumb;
    String PhotoCaption;

    public String getPhoto() {
        return Photo;
    }

    @JsonProperty("Photo")
    public void setPhoto(String photo) {
        Photo = photo;
    }

    public String getThumb() {
        return Thumb;
    }

    @JsonProperty("Thumb")
    public void setThumb(String thumb) {
        Thumb = thumb;
    }


    public String getPhotoCaption() {
        return PhotoCaption;
    }

    @JsonProperty("PhotoCaption")
    public void setPhotoCaption(String photoCaption) {
        PhotoCaption = photoCaption;
    }
}
