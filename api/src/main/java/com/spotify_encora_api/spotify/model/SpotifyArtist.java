package com.spotify_encora_api.spotify.model;

import lombok.Data;

import java.util.List;

@Data
public class SpotifyArtist extends SpotifyEntity{
    private List<String> genres;
}
