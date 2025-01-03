package com.spotify_encora_api.spotify.controller;

import com.spotify_encora_api.spotify.model.*;
import com.spotify_encora_api.spotify.service.SpotifyAPIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class SpotifyAPIImpl implements SpotifyAPI {
    private final SpotifyAPIService apiService;
    @Autowired
    SpotifyAPIImpl(SpotifyAPIService apiService){
        this.apiService = apiService;
    }
    @Override
    public ResponseEntity<SpotifySearch> search(@RequestParam String query, @RequestHeader(name = "Authorization") String token, @RequestHeader(name = "Refresh") String refresh) {
        return apiService.search(query, token, refresh);
    }

    @Override
    public ResponseEntity<SpotifyPageableArtists> getUserTopArtists(@RequestHeader(name = "Authorization") String token, @RequestHeader(name = "Refresh") String refresh) {
        return apiService.getUserTopArtists(token, refresh);
    }

    @Override
    public ResponseEntity<SpotifyArtist> getArtistById(@RequestHeader(name = "Authorization") String token, @RequestHeader(name = "Refresh") String refresh, @PathVariable String id) {
        return apiService.getArtistById(token, refresh, id);
    }

    @Override
    public ResponseEntity<SpotifyArtistTopTracks> getArtistTopTracks(@RequestHeader(name = "Authorization") String token, @RequestHeader(name = "Refresh") String refresh, @PathVariable String id) {
        return apiService.getArtistTopTracks(token, refresh, id);
    }

    @Override
    public ResponseEntity<SpotifyPageableAlbums> getArtistAlbums(String token, String refresh, String id) {
        return apiService.getArtistAlbums(token, refresh, id);
    }
}
