package com.example.webbtjansterprojekt.controller;

import com.example.webbtjansterprojekt.model.Concert;
import com.example.webbtjansterprojekt.model.Playlist;
import com.example.webbtjansterprojekt.model.User;
import com.google.gson.JsonObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;


@RestController
@RequestMapping("/API")
public class WebController {
    private ServiceController service;

    public WebController() {
        init();
    }

    private void init() {
        service = new ServiceController();
    }

    //http://localhost:8080/API/user?id=theid
    @GetMapping("/user")
    private User returnSpotify(@RequestParam("id") String userId) {
        return service.getSpotifyInfo(userId);
    }

    //http://localhost:8080/API/playlist?id=theid
    @GetMapping("/playlist")
    private Playlist returnPlaylist(@RequestParam("id") String playlistId) {
        return service.getPlaylistContent(playlistId);
    }

    //http://localhost:8080/API/tickets?keyWord=thekeyword
    @GetMapping("/tickets")
    private Mono<Concert> returnTicketInfo (@RequestParam("keyWord") String keyWord){
        return service.getTicketInfo(keyWord);
    }

    //http://localhost:8080/API/video?query=thequery
    @GetMapping("/video")
    private String getVideoID (@RequestParam("query") String searchQuery){
        return service.getVideoID(searchQuery);
    }
}