package com.spotify_encora_api.spotify.model;

import lombok.Data;

import java.util.List;

@Data
public class SpotifyAlbum extends SpotifyEntity{
    private String album_type;
    private Integer total_tracks;
    private String release_date;
    private String release_date_precision;
    private List<SpotifyArtist> artists;
    private SpotifyPageableTracks tracks;
    private Integer popularity;
}
