package com.spotify_encora_api.spotify.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Controller
public class SpotifyAuthImpl implements SpotifyAuth{
    @Value("${SPOTIFY.CLIENT_ID}")
    private String clientId;

    @Autowired
    RestTemplate restTemplate;
    @Override
    public String auth(ModelMap model) {
        String url =

                ("https://accounts.spotify.com/authorize?"
        )
                .concat("client_id=" + clientId)
                .concat("&scope=user-read-private user-read-email")
                .concat("&redirect_uri=http://localhost:8080/oauth")
                .concat("&state=s1A1c221T2f28s3c")
                .concat("&response_type=code");
        return "redirect:" + url;
    }

    @Override
    public ResponseEntity<String> oauth(@RequestParam(name = "error") Optional<String> error) {
        return error.map(s -> new ResponseEntity<>(s, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>("No error", HttpStatus.BAD_REQUEST));
    }


}
