package com.spotify_encora_api.spotify.model;

import lombok.Data;

@Data
public class SpotifyUserTopArtistRequest {
    private String type;
    private Integer limit;
}
