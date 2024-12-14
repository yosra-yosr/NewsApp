package com.example.worldnewsapp;

import java.io.Serializable;

public class News implements Serializable {
    private String title;
    private String description;
    private String urlToImage;
    private String url;

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getUrlToImage() { return urlToImage; }
    public void setUrlToImage(String urlToImage) { this.urlToImage = urlToImage; }

    public String getUrl() { return url; }
    public void setUrl(String url) { this.url = url; }
}
