package com.spotify_encora_api.spotify.model;

import lombok.Data;

@Data
public class SpotifyPaginatedEntity {
    private Integer limit;
    private String next;
    private String previous;
    private Long total;
    private String href;
}
