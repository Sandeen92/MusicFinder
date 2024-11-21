package com.example.webbtjansterprojekt.services;

import com.example.webbtjansterprojekt.auth.SpotifyAuth;
import com.example.webbtjansterprojekt.controller.ServiceController;

import com.example.webbtjansterprojekt.model.Playlist;
import com.example.webbtjansterprojekt.model.Track;
import com.example.webbtjansterprojekt.model.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;


import java.net.URI;
import java.sql.SQLOutput;
import java.util.List;
import java.util.Optional;

public class SpotifyService {

    private ServiceController serviceController;
    private SpotifyAuth auth;
    public SpotifyService(ServiceController serviceController) {
        init(serviceController);
    }

    private void init(ServiceController serviceController) {
        this.serviceController = serviceController;
        auth = new SpotifyAuth();
    }


    /**
     * Gets the playlist from spotify wich returns a json, then calls polishChosenPlaylist
     * to handle the data
     * @param playlist
     */

    public void getPlaylist(Playlist playlist) {
        String playlistAPI = "https://api.spotify.com/v1/playlists/" + playlist.getId();

        try{
            URI playlistURI = new URI(playlistAPI);
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer " + auth.getAccestoken());
            headers.setContentType(MediaType.APPLICATION_JSON);

            RequestEntity<Void> requestEntity = new RequestEntity<>(headers, HttpMethod.GET, playlistURI);
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> response = restTemplate.exchange(requestEntity, String.class);


            JSONObject jsonObject = new JSONObject(response.getBody());
            polishChosenPlaylistJson(jsonObject, playlist);

        } catch (Exception e){
            e.printStackTrace();
        }

    }

    /**
     * Takes the retrieved playlist and uses it to store its tracks in the user
     * by searching for keywords for the artist name and song name
     * @param jsonObject
     * @param playlist
     * @throws JSONException
     */
        //TODO tar 1 millisekund
    public void polishChosenPlaylistJson(JSONObject jsonObject, Playlist playlist) throws JSONException {
        JSONObject temp = jsonObject.getJSONObject("tracks");
        JSONArray tempArray = temp.getJSONArray("items");
        for(int i = 0; i < tempArray.length(); i++){
            JSONObject tempObject = tempArray.getJSONObject(i).getJSONObject("track");
            JSONObject artistName = new JSONObject(tempObject.getJSONArray("artists").getString(0));
            Track track = new Track(artistName.getString("name"), tempObject.getString("name"));
            playlist.addTrack(track);
        }

    }

    /**
     * gets all public playlists from a user and returns them
     * with only name, id and images
     * @param user
     */

    public void getPlaylists(User user){
        String playlistsAPI = "https://api.spotify.com/v1/users/" + user.getUsername() + "/playlists";

        try{
            URI playlistUrl = new URI(playlistsAPI);
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer " + auth.getAccestoken());
            headers.setContentType(MediaType.APPLICATION_JSON);

            RequestEntity<Void> requestEntity = new RequestEntity<>(headers, HttpMethod.GET, playlistUrl);
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> response = restTemplate.exchange(requestEntity, String.class);

            //TODO spara ner ID, Images, Name of playlist
            JSONObject jsonObject = new JSONObject(response.getBody());
            polishPlaylistsJson(jsonObject,user);

        } catch (Exception e){
            user.setPlaylists(null);
        }
    }

    /**
     * Takes in the data from all the playlists a user has and
     * handles the data to only get playlistname, id and images
     * @param jsonObject
     * @param user
     * @throws JSONException
     */
    private void polishPlaylistsJson(JSONObject jsonObject, User user) throws JSONException {
        JSONArray temp = jsonObject.getJSONArray("items");
        for(int i = 0; i < temp.length(); i++){

            JSONObject tempJson = new JSONObject(jsonObject.getJSONArray("items").getString(i));
            JSONObject check = tempJson.getJSONObject("tracks");
            if(Integer.parseInt(check.getString("total")) > 0){
                JSONObject tempPicture = new JSONObject(tempJson.getJSONArray("images").getString(0));
                Playlist playlist = new Playlist(tempJson.getString("name"), tempPicture.getString("url")
                        , tempJson.getString("id"));
                user.addPlaylist(playlist);
            }
        }
    }

}
