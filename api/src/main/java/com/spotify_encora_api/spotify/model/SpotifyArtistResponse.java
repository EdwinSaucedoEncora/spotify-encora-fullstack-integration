package com.spotify_encora_api.spotify.model;

import lombok.Data;

@Data
public class SpotifyArtistResponse {
    private SpotifyArtist info;
    private SpotifyArtistTopTracks popularSongs;
    private SpotifyPageableAlbums discography;
}
