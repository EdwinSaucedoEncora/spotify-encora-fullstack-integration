package com.spotify_encora_api.spotify.service;

import com.spotify_encora_api.spotify.model.*;
import org.springframework.http.ResponseEntity;

public interface SpotifyAPIService {
    public ResponseEntity<SpotifySearch> search(String query, String token, String refresh);
    public ResponseEntity<SpotifyPageableArtists> getUserTopArtists(String token, String refresh);
    public ResponseEntity<SpotifyArtistResponse> getArtistById(String token, String refresh, String id);
    public ResponseEntity<SpotifyArtistTopTracks> getArtistTopTracks(String token, String refresh, String id);
    public ResponseEntity<SpotifyPageableAlbums> getArtistAlbums(String token, String refresh, String id);
    public ResponseEntity<SpotifyAlbum> getAlbumById(String token, String refresh, String id);
}
