package com.spotify_encora_api.spotify.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;

import java.util.Optional;

@RequestMapping("/")
public interface SpotifyAuth {
    @GetMapping("/auth/spotify")
    public String auth(ModelMap model);

    @GetMapping("/oauth")
    public ResponseEntity<String> oauth(@RequestParam(name = "error") Optional<String> error);
}
