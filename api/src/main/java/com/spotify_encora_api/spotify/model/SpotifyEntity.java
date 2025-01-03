package com.spotify_encora_api.spotify.model;

import lombok.Data;

import java.util.List;

@Data
public class SpotifyEntity {
    private String id;
    private List<SpotifyImage> images;
    private String name;
    private String uri;
}
