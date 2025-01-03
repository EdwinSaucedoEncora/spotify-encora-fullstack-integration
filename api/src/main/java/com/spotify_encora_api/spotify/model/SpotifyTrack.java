package com.spotify_encora_api.spotify.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Optional;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SpotifyTrack extends SpotifyEntity {
    private SpotifyAlbum album;
    private List<SpotifyArtist> artists;
    private Integer disc_number;
    private Integer duration_ms;
    private Boolean is_playable;
    private Optional<String> preview_url;
    private Integer track_number;
}
