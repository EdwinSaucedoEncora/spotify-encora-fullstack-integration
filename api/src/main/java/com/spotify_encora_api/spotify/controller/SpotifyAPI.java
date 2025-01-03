package com.spotify_encora_api.spotify.controller;

import com.spotify_encora_api.spotify.model.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

public interface SpotifyAPI {

    @GetMapping("/search")
    public ResponseEntity<SpotifySearch> search(@RequestParam String query, @RequestHeader(name = "Authorization") String token, @RequestHeader(name = "Refresh") String refresh);

    @GetMapping("/me/top/artists")
    public ResponseEntity<SpotifyPageableArtists> getUserTopArtists(@RequestHeader(name = "Authorization") String token, @RequestHeader(name = "Refresh") String refresh);

    @GetMapping("/artists/{id}")
    public ResponseEntity<SpotifyArtist> getArtistById(@RequestHeader(name = "Authorization") String token, @RequestHeader(name = "Refresh") String refresh, @PathVariable String id);

    @GetMapping("/artists/{id}/top-tracks")
    public  ResponseEntity<SpotifyArtistTopTracks> getArtistTopTracks(@RequestHeader(name = "Authorization") String token, @RequestHeader(name = "Refresh") String refresh, @PathVariable String id);

    @GetMapping("/artists/{id}/albums")
    public  ResponseEntity<SpotifyPageableAlbums> getArtistAlbums(@RequestHeader(name = "Authorization") String token, @RequestHeader(name = "Refresh") String refresh, @PathVariable String id);
}
