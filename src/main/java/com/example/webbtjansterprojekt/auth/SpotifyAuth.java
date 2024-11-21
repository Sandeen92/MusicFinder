package com.example.webbtjansterprojekt.auth;

import org.json.JSONObject;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class SpotifyAuth {
    private static final String CLIENT_ID = "";
    private static final String CLIENT_SECRET = "";
    private static final String TOKEN_URL = "";
    private static final String TOKENREQUESTBODY  = "grant_type=client_credentials&client_id=" + CLIENT_ID + "&client_secret=" + CLIENT_SECRET;
    private static String contentType = "application/x-www-form-urlencoded";

    /**
     * Connects to spotify to retrive an accestoken
     * by using our clientsecret and client id 
     * @return
     */
    public String getAccestoken(){
        try{
            URL tokenUrl = new URL(TOKEN_URL);
            HttpURLConnection connection = (HttpURLConnection) tokenUrl.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", contentType);
            connection.setDoOutput(true);

            connection.getOutputStream().write(TOKENREQUESTBODY.getBytes(StandardCharsets.UTF_8));

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while((line = reader.readLine()) != null){
                response.append(line);
            }
            reader.close();

            String accestoken = response.toString().split("\"access_token\":\"")[1].split("\"")[0];
            return accestoken;
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }

    }
}
