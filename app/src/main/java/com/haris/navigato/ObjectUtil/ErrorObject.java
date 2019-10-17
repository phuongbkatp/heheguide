package com.haris.navigato.ObjectUtil;

/**
 * Created by hp on 5/23/2018.
 */

public class ErrorObject {
    String error;
    String title;
    String tagline;
    int errorImage;

    public ErrorObject(String title, String tagline, int errorImage) {
        this.title = title;
        this.tagline = tagline;
        this.errorImage = errorImage;
    }

    public ErrorObject(String error) {
        this.error = error;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTagline() {
        return tagline;
    }

    public void setTagline(String tagline) {
        this.tagline = tagline;
    }

    public int getErrorImage() {
        return errorImage;
    }

    public void setErrorImage(int errorImage) {
        this.errorImage = errorImage;
    }
}
