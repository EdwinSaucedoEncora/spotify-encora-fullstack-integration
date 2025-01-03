package com.spotify_encora_api.spotify.model;

import lombok.Data;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;

@Data
public class SpotifyTokenRequest {
    private String url;
    private HttpMethod method;
    private SpotifyRequestBody body;
    private HttpHeaders headers;
}
