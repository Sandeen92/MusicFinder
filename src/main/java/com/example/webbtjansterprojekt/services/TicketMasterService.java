package com.example.webbtjansterprojekt.services;

import com.example.webbtjansterprojekt.auth.TicketMasterAuth;
import com.example.webbtjansterprojekt.controller.ServiceController;
import com.example.webbtjansterprojekt.model.Concert;
import com.google.gson.*;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;


public class TicketMasterService {

    private ServiceController serviceController;
    private TicketMasterAuth auth;

    public TicketMasterService(ServiceController serviceController) {
        init(serviceController);
    }

    private void init(ServiceController serviceController) {
        this.serviceController = serviceController;
        auth = new TicketMasterAuth();
    }

    /**
     * Retrieves data from the Ticketmaster API using the WebClient.
     *
     * @param keyword the search keyword
     * @return a Mono that emits the response body as a String
     */
    public Mono<String> getTicketMasterData(String keyword) {
        WebClient webClient = WebClient.create();
        String url = buildUrl(keyword);

        return webClient.get()
                .uri(url)
                .retrieve()
                .bodyToMono(String.class);
    }

    /**
     * Extracts the link to the event from the JSON response.
     *
     * @param keyword the word to search for
     * @return a Mono that emits the link to the event
     */
    public Mono<Concert> extractLink(String keyword) {
        return getTicketMasterData(keyword).map(this::parseJsonResponse);
    }

    /**
     * Parses the JSON response and returns the link to the event.
     *
     * @param response JSON response from the ticket master API.
     * @return link to the event
     */
    private Concert parseJsonResponse(String response) {
        Concert concertInfo = new Concert();

        JsonParser jsonParser = new JsonParser();
        JsonObject jsonObject = jsonParser.parse(response).getAsJsonObject();

        if (jsonObject.has("_embedded")) {
            JsonArray eventsArray = jsonObject.getAsJsonObject("_embedded").
                    getAsJsonArray("events");

            if (eventsArray.size() > 0) {
                JsonElement event = eventsArray.get(0);
                JsonObject eventObject = event.getAsJsonObject();

                if (eventObject.has("url")) {
                    if (eventObject.has("dates")) {
                        JsonObject datesObject = eventObject.getAsJsonObject("dates");
                        JsonObject startObject = datesObject.getAsJsonObject("start");
                        String localDate = "";
                        String localTime = "";

                        if (startObject.has("localDate")) {
                            localDate = startObject.getAsJsonPrimitive("localDate").getAsString();
                            concertInfo.setLocalDate(localDate);
                        }

                        if (startObject.has("localTime")) {
                            localTime = startObject.getAsJsonPrimitive("localTime").getAsString();
                            concertInfo.setLocalTime(localTime);
                        }


                        if (eventObject.has("_embedded") && eventObject.getAsJsonObject("_embedded").has("venues")) {
                            JsonArray venuesArray = eventObject.getAsJsonObject("_embedded").getAsJsonArray("venues");

                            if (venuesArray.size() > 0) {
                                JsonElement venue = venuesArray.get(0);
                                JsonObject venueObject = venue.getAsJsonObject();

                                String venueName = venueObject.getAsJsonPrimitive("name").getAsString();
                                concertInfo.setVenueName(venueName);

                            }
                        }

                        JsonArray imagesArray = eventObject.getAsJsonArray("images");

                        if (imagesArray.size() > 0) {

                            JsonElement image = imagesArray.get(0);
                            JsonObject imageObject = image.getAsJsonObject();
                            String imageURL = imageObject.getAsJsonPrimitive("url").getAsString();
                            concertInfo.setImageUrl(imageURL);
                        }

                        String ticketURL = eventObject.getAsJsonPrimitive("url").getAsString();
                        concertInfo.setTicketUrl(ticketURL);
                    }
                }
            }


        }
        return concertInfo;
    }

        private String buildUrl (String keyword){
            String encodedKeyword = null;
            String baseUrl = auth.getURI();
            String apiKey = auth.getKey();

            try {
                encodedKeyword = URLEncoder.encode(keyword, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }
            return String.format("%s?apikey=%s&size=1&keyword=%s&locale=*&page=0&sort=date,asc", baseUrl, apiKey, encodedKeyword);
        }

    }
