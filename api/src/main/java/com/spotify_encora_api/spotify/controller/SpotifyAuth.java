package com.spotify_encora_api.spotify.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.net.URISyntaxException;
import java.util.Optional;

public interface SpotifyAuth {
    @GetMapping("/auth/spotify")
    public String auth();

    @GetMapping("/oauth")
    public String oauth(@RequestParam Optional<String> error, @RequestParam Optional<String> code, @RequestParam String state) throws URISyntaxException;
}
