package com.haris.navigato.ObjectUtil;

/**
 * Created by hp on 5/28/2018.
 */

public class SpinnerItem {
    private int picture;
    private String title;

    public SpinnerItem(int picture, String title) {
        this.picture = picture;
        this.title = title;
    }


    public int getPicture() {
        return picture;
    }

    public void setPicture(int picture) {
        this.picture = picture;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
