package com.spotify_encora_api.spotify.model;

import lombok.Data;

@Data
public class TopArtistsSpotifyRequest {
    private String type;
    private Integer limit;
}
