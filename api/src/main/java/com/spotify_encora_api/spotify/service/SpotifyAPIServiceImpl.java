package com.spotify_encora_api.spotify.service;

import com.spotify_encora_api.spotify.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
public class SpotifyAPIServiceImpl implements SpotifyAPIService {
    private final SpotifyAuthService authService;

    @Autowired
    SpotifyAPIServiceImpl(SpotifyAuthService authService){
        this.authService = authService;
    }

    @Override
    public ResponseEntity<SpotifySearch> search(String query, String token, String refresh) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", token);
        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        try {
            return restTemplate.exchange("https://api.spotify.com/v1/search?q=" + query, HttpMethod.GET, entity, SpotifySearch.class);
        }
        catch (Exception e)
        {
            if(refresh != null){
                String newToken = authService.getRefreshedToken(refresh);
                headers.set("Authorization", newToken);
                HttpEntity<String> newEntity = new HttpEntity<>(null, headers);
                return restTemplate.exchange("https://api.spotify.com/v1/search?type=album,artist,track&q=" + query, HttpMethod.GET, entity, SpotifySearch.class);
            }
        }
        return null;
    }

    @Override
    public ResponseEntity<SpotifyPageableArtists> getUserTopArtists(String token, String refresh) {
        if(token != null){
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", token);
            HttpEntity<SpotifyPageableArtists> entity = new HttpEntity<>(null, headers);
            try {
                return restTemplate.exchange("https://api.spotify.com/v1/me/top/artists", HttpMethod.GET, entity, SpotifyPageableArtists.class);
            }
            catch (Exception e)
            {
                if(refresh == null){
                    return  new ResponseEntity<>(HttpStatus.FORBIDDEN);
                }
                String newToken = authService.getRefreshedToken(refresh);

                headers.set("Authorization", newToken);
                HttpEntity<String> newEntity = new HttpEntity<>(null, headers);
                ResponseEntity<SpotifyPageableArtists> response = restTemplate.exchange("https://api.spotify.com/v1/me/top/artists", HttpMethod.GET, entity, SpotifyPageableArtists.class);
                MultiValueMap<String, String> responseHeaders = new LinkedMultiValueMap<>();
                responseHeaders.add("Authorization", newToken);
                return new ResponseEntity<SpotifyPageableArtists>(response.getBody(), responseHeaders, HttpStatus.OK);
            }
        }
        return  new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

    @Override
    public ResponseEntity<SpotifyArtist> getArtistById(String token, String refresh, String id) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", token);
        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        try {
            return restTemplate.exchange("https://api.spotify.com/v1/artists/" + id, HttpMethod.GET, entity, SpotifyArtist.class);
        }
        catch (Exception e)
        {
            if(refresh != null){
                String newToken = authService.getRefreshedToken(refresh);
                headers.set("Authorization", newToken);
                HttpEntity<String> newEntity = new HttpEntity<>(null, headers);
                return restTemplate.exchange("https://api.spotify.com/v1/artists/" + id, HttpMethod.GET, newEntity, SpotifyArtist.class);

            }
        }
        return null;
    }

    @Override
    public ResponseEntity<SpotifyArtistTopTracks> getArtistTopTracks(String token, String refresh, String id) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", token);
        HttpEntity<SpotifyPageableArtists> entity = new HttpEntity<>(null, headers);
        try {
            return restTemplate.exchange("https://api.spotify.com/v1/artists/" + id + "/top-tracks", HttpMethod.GET, entity, SpotifyArtistTopTracks.class);
        }
        catch (Exception e)
        {
            if(refresh != null){
                String newToken = authService.getRefreshedToken(refresh);
                headers.set("Authorization", newToken);
                HttpEntity<String> newEntity = new HttpEntity<>(null, headers);
                return restTemplate.exchange("https://api.spotify.com/v1/artists/" + id + "/top-tracks", HttpMethod.GET, entity, SpotifyArtistTopTracks.class);
            }
        }
        return null;
    }
    public ResponseEntity<SpotifyPageableAlbums> getArtistAlbums(String token, String refresh, String id) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", token);
        HttpEntity<SpotifyPageableArtists> entity = new HttpEntity<>(null, headers);
        try {
            return restTemplate.exchange("https://api.spotify.com/v1/artists/" + id + "/albums", HttpMethod.GET, entity, SpotifyPageableAlbums.class);
        }
        catch (Exception e)
        {
            if(refresh != null){
                String newToken = authService.getRefreshedToken(refresh);
                headers.set("Authorization", newToken);
                HttpEntity<String> newEntity = new HttpEntity<>(null, headers);
                return restTemplate.exchange("https://api.spotify.com/v1/artists/" + id + "/albums", HttpMethod.GET, entity, SpotifyPageableAlbums.class);
            }
        }
        return null;
    }
}
