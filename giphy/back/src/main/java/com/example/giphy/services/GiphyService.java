package com.example.giphy.services;

import com.example.giphy.model.Gif;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Component
public class GiphyService {

    @Value("${giphy.api.url}")
    private String apiURL;
    @Value("${giphy.api.key}")
    private String key;

    public List<Gif> getSearchResults(String searchString) {

        String url = this.apiURL + this.key + "&limit=10&q=" + searchString;


        HttpEntity<String> httpEntity = new HttpEntity<>("");
        RestTemplate restTemplate = new RestTemplate();

        // These two are new! We'll see how they work.
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode;
        List<Gif> gifList = new ArrayList<>();

        // Code start:
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, httpEntity, String.class);
        System.out.println(response.getBody());

        try {
            jsonNode = objectMapper.readTree(response.getBody());
            JsonNode root = jsonNode.path("data");

            for (int i=0; i< root.size(); i++) {
                String title = root.path(i).path("title").asText();
                String username = root.path(i).path("username").asText();
                String gifUrl = root.path(i).path("images").path("original").path("url").asText();
                Gif currentGif = new Gif(gifUrl, username, title);
                gifList.add(currentGif);
            }
        }catch(JsonProcessingException e) {

        }


        return gifList;
    }
}
