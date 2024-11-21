package com.example.webbtjansterprojekt.services;

import com.example.webbtjansterprojekt.auth.YouTubeAuth;
import com.example.webbtjansterprojekt.controller.ServiceController;
import org.json.JSONException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import java.util.ArrayList;
import java.util.Objects;


public class YouTubeService {

    private ServiceController serviceController;
    private YouTubeAuth auth;
    private RestTemplate restTemplate;
    private ArrayList<String> searchHistory;

    public YouTubeService(ServiceController serviceController) {
        init(serviceController);
    }

    private void init(ServiceController serviceController) {
        this.serviceController = serviceController;
        auth = new YouTubeAuth();
        restTemplate = new RestTemplate();
        searchHistory = new ArrayList<String>();
    }

    /**
     * Hämtar en lista med videoURLs från en sökning på YouTube.

     * @param searchQuery
     * @return
     */
    public String getVideoID(String searchQuery) {
        String encodedQuery = "";

        try {
           encodedQuery = URLEncoder.encode(searchQuery, "UTF-8");
        } 
        catch(Exception e) {}
        
        JSONArray items = getVideoIds(encodedQuery);
        String temp = "";

        try {
            JSONObject video = items.getJSONObject(0);  //tar första sökresultatet, kan göra filtrering senare
            JSONObject id = video.getJSONObject("id");  //tar id från sökresultatet
            temp = id.getString("videoId"); //tar strängen från id
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
        
        return temp;
    }

    /**
     * Hämtar en lista med videoId:n från en sökning på YouTube.
     */
    public JSONArray getVideoIds(String searchQuery) {
        JSONArray items = null;
        try {
            JSONObject jsonObject = new JSONObject(getJsonFromSearchQuery(searchQuery));
            items = jsonObject.getJSONArray("items");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return items;
    }

    /**
     * Hämtar JSON från en sökning på YouTube.

     * @param searchQuery
     * @return
     */
    public String getJsonFromSearchQuery(String searchQuery) {
        String searchURL = constructSearchURL(searchQuery);
        ResponseEntity<String> response = restTemplate.getForEntity(searchURL, String.class);
        String json = response.getBody();
        return json;
    }

    /**
     * Konstruerar en URL för att söka på YouTube.
     * @param searchQuery
     * @return
     */
    public String constructSearchURL(String searchQuery) {
        String searchURL = "";
        String searchAmount = "4";  //antal sökresultat
        if (!Objects.equals(searchQuery, "")){
            searchURL = auth.getURI() + "search?part=snippet&maxResults="+searchAmount+"&q=" + searchQuery + "&key=" + auth.getKey();
        }
        return searchURL;
    }
}