package com.example.webbtjansterprojekt.model;

import java.util.ArrayList;

public class Artist {

    private String name;
    private ArrayList<Track> tracks;

    public Artist(String name) {
        this.name = name;
        tracks = new ArrayList<>();
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

    public Track getTrack(String title) {
        for (Track track : tracks) {
            if (track.getTitle().equals(title)) {
                return track;
            }
        }
        return null;
    }
}
