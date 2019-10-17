package com.haris.navigato.ObjectUtil;

import java.io.Serializable;

/**
 * Created by hp on 5/31/2018.
 */

public class EmptyState extends Object implements Serializable {
    private String title;
    private String icon;


    public EmptyState(String title, String icon) {
        this.title = title;
        this.icon = icon;
    }

    public EmptyState() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

}
