package com.example.webbtjansterprojekt.model;

import java.util.ArrayList;

public class Track {

    private String artistName;
    private String title;
    private ArrayList<String> videoURLs;

    public Track(String artistName, String title) {
        this.artistName = artistName;
        this.title = title;
        videoURLs = new ArrayList<>();
    }

    public String getArtistName() {
        return artistName;
    }

    public String getTitle() {
        return title;
    }

    public ArrayList<String> getVideoURLs() {
        return videoURLs;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setVideoURLs(ArrayList<String> videoURLs) {
        this.videoURLs = videoURLs;
    }

    public void addVideoURL(String videoURL) {
        videoURLs.add(videoURL);
    }
}
