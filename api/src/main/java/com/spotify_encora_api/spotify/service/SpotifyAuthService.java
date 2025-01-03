package com.spotify_encora_api.spotify.service;

import java.util.Optional;

public interface SpotifyAuthService {
    public String redirectToSpotifyAuth();
    public String redirectWithAccessToken(String state, Optional<String> error, Optional<String> code);
    public String getRefreshedToken(String token);
}
