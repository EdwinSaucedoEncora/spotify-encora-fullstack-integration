package com.spotify_encora_api.spotify.model;

import lombok.Data;

import java.util.List;

@Data
public class SpotifyPageableArtists extends SpotifyPaginatedEntity {
    private List<SpotifyArtist> items;
}
