package com.example.complin;

import android.graphics.Bitmap;

public class ModelClass {
    private String category,description,severity;
    private Bitmap image;


    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSeverity() {
        return severity;
    }

    public void setSeverity(String severity) {
        this.severity = severity;
    }



    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }



    public ModelClass(String category, String description, String severity,  Bitmap image) {
        this.category = category;
        this.description = description;
        this.severity = severity;

        this.image = image;
    }
}
