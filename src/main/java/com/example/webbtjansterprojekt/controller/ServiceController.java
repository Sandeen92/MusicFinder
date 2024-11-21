package com.example.webbtjansterprojekt.controller;

import com.example.webbtjansterprojekt.model.Concert;
import com.example.webbtjansterprojekt.model.Playlist;
import com.example.webbtjansterprojekt.model.User;
import com.example.webbtjansterprojekt.services.SpotifyService;
import com.example.webbtjansterprojekt.services.TicketMasterService;
import com.example.webbtjansterprojekt.services.YouTubeService;
import com.google.gson.JsonObject;
import reactor.core.publisher.Mono;



public class ServiceController {

    private SpotifyService spotifyService;
    private YouTubeService youTubeService;
    private TicketMasterService ticketMasterService;
    //for test
    String playlistID = "2EvkSpeYQrZYApFxW7AF8j";
    String userID = "linan0205";
    String trackID = "4W7yzpXpmBJhe5Guao0piy";

    public ServiceController() {
        init();
    }

    private void init() {
        spotifyService = new SpotifyService(this);
        youTubeService = new YouTubeService(this);
        ticketMasterService = new TicketMasterService(this);
    }



    public User getSpotifyInfo(String userID){
        User user = new User(userID);

        //Get all playlists
        spotifyService.getPlaylists(user);


        return user;
    }

    public Playlist getPlaylistContent(String playlistID){
        Playlist playlist = new Playlist("null", null, playlistID);
        spotifyService.getPlaylist(playlist);

        return playlist;
    }

    public Mono<Concert> getTicketInfo(String keyWord){
        return ticketMasterService.extractLink(keyWord);
    }


    public String getVideoID(String searchQuery) {
        return youTubeService.getVideoID(searchQuery);
    }

}
