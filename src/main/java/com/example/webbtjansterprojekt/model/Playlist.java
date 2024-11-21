package com.example.webbtjansterprojekt.model;

import java.util.ArrayList;

public class Playlist {

    private String name;
    private ArrayList<Track> tracks;
    private String image;
    private String id;

    public Playlist(String name, String image, String id) {
        this.name = name;
        tracks = new ArrayList<>();
        this.image = image;
        this.id = id;
    }

    public void addTrack(Track track) {
        tracks.add(track);
    }


    public String getName() {
        return name;
    }

    public ArrayList<Track> getTracks() {
        return tracks;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTracks(ArrayList<Track> tracks) {
        this.tracks = tracks;
    }

    public String getId() {
        return id;
    }

    public String getImage(){
        return image;
    }
}
