package com.johnwoconnor.experiments.apod.models;

import java.util.SimpleTimeZone;

/**
 * Transfer object for an image.
 * Created by john on 9/4/15.
 */
public class ScienceImage {
    private String title;
    private String subtitle;
    private String description;
    private String imageUrl;

    public ScienceImage() {
        title = null;
        subtitle = null;
        description = null;
        imageUrl = null;
    }

    public String getTitle() {
        return title;
    }

    public ScienceImage setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public ScienceImage setSubtitle(String subtitle) {
        this.subtitle = subtitle;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public ScienceImage setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public ScienceImage setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }


}
