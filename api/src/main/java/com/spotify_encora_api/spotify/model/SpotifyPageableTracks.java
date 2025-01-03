package com.spotify_encora_api.spotify.model;

import lombok.Data;

import java.util.List;

@Data
public class SpotifyPageableTracks extends SpotifyPaginatedEntity {
    private List<SpotifyTrack> items;
}
