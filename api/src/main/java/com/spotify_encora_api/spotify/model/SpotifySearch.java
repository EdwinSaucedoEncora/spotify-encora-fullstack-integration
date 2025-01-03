package com.spotify_encora_api.spotify.model;

import lombok.Data;

@Data
public class SpotifySearch {
   SpotifyPageableTracks tracks;
    SpotifyPageableArtists artists;
    SpotifyPageableAlbums albums;
}
