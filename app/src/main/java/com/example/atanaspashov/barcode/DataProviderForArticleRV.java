package com.example.atanaspashov.barcode;

import android.media.Image;

public class DataProviderForArticleRV {
    private String title, text;
    private int imageID;

    public DataProviderForArticleRV(String title, String text, int imageID) {
        this.title = title;
        this.text = text;
        this.imageID = imageID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text= text;
    }

    public int getImage() {
        if (imageID == 1) {
            return R.drawable.img1;
        }
        else if (imageID == 2) {
            return R.drawable.img2;
        }
        else if (imageID == 3) {
            return R.drawable.img3;
        }
        return imageID; }

    public void setImage(int imageID) {
        this.imageID = imageID;
    }
}
