package com.example.login;

public class Photo {

    private String photoPath;
    private String photoName;
    public Photo(String photoName, String photoPath) {
        this.photoName=photoName;
        this.photoPath=photoPath;
    }
    public String getPhotoPath() {
        return photoPath;
    }

    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
    }

    public String getPhotoName() {
        return photoName;
    }

    public void setPhotoName(String photoName) {
        this.photoName = photoName;
    }
}
