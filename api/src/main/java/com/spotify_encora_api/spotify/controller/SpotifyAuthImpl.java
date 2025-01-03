package com.spotify_encora_api.spotify.controller;

import com.spotify_encora_api.spotify.service.SpotifyAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;

import java.net.URISyntaxException;
import java.util.Optional;

@Controller
public class SpotifyAuthImpl implements SpotifyAuth{

    private final SpotifyAuthService authService;

    @Autowired
    SpotifyAuthImpl(SpotifyAuthService authService){
        this.authService = authService;
    }

    @Override
    public String auth() {
        return  authService.redirectToSpotifyAuth();
    }

    @Override
    public String oauth(@RequestParam Optional<String> error, @RequestParam Optional<String> code, @RequestParam String state) throws URISyntaxException {
        return authService.redirectWithAccessToken(state, error, code);
    }
}
