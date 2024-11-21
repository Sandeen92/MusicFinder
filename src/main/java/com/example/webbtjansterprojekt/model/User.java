package com.example.webbtjansterprojekt.model;

import java.util.ArrayList;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class User {

    private String username;
    private ArrayList<Playlist> playlists;

    public User(String username) {
        this.username = username;
        playlists = new ArrayList<>();
    }

    public void addPlaylist(Playlist playlist) {
        playlists.add(playlist);
    }

    public String getUsername() {
        return username;
    }

    public ArrayList<Playlist> getPlaylists() {
        return playlists;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPlaylists(ArrayList<Playlist> playlists) {
        this.playlists = playlists;
    }

    public Playlist getPlaylist(String name) {
        for (Playlist playlist : playlists) {
            if (playlist.getName().equals(name)) {
                return playlist;
            }
        }
        return null;
    }



    /** Test för att se om klasserna fungerar som de ska och serialiseras till JSON med hjälp av GSON
    public static void main(String[] args) {
        Playlist playlist = new Playlist("testPlaylist");
        Artist artist = new Artist("testArtist");
        Track track = new Track(artist.getName(), "testTrack");
        track.addVideoURL("youtube.com/testVideoURL1");
        track.addVideoURL("youtube.com/testVideoURL2");
        User user = new User("testUsername");
        artist.addTrack(track);
        playlist.addTrack(track);
        user.addPlaylist(playlist);
        Track track2 = new Track(artist.getName(), "testTrack2");
        track2.addVideoURL("youtube.com/testVideoURL3");
        track2.addVideoURL("youtube.com/testVideoURL4");
        artist.addTrack(track2);
        playlist.addTrack(track2);
        Artist artist2 = new Artist("testArtist2");
        Track track3 = new Track(artist2.getName(), "testTrack3");
        track3.addVideoURL("youtube.com/testVideoURL5");
        artist2.addTrack(track3);
        playlist.addTrack(track3);



        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(user);
        System.out.println(json);
    }
     */
}
