package com.example.tanut.mapsearch.data.db.model;

import java.io.Serializable;

/**
 * Created by tanut on 9/26/2017.
 */

public class Item implements Serializable{

    private String title = "Row Title :";
    private String subtitle = "Row SubTitle :";
    private int image;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
