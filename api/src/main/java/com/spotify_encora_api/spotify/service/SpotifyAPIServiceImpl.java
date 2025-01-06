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
            ResponseEntity<SpotifySearch> response = restTemplate.exchange("https://api.spotify.com/v1/search?type=album,artist,track&q=" + query, HttpMethod.GET, entity, SpotifySearch.class);
            HttpStatusCode code = response.getStatusCode();
            if(code.is2xxSuccessful()){
                return ResponseEntity.ok().headers(headers).body(response.getBody());
            }
        }
        catch (Exception e)
        {
            if(refresh != null){
                String newToken = authService.getRefreshedToken(refresh);
                headers.set("Authorization", newToken);
                HttpEntity<String> newEntity = new HttpEntity<>(null, headers);
                ResponseEntity<SpotifySearch> response = restTemplate.exchange("https://api.spotify.com/v1/search?type=album,artist,track&q=" + query, HttpMethod.GET, entity, SpotifySearch.class);
                HttpStatusCode code = response.getStatusCode();
                if(code.is2xxSuccessful()){
                    return ResponseEntity.ok().headers(headers).body(response.getBody());
                }
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
                ResponseEntity<SpotifyPageableArtists> response = restTemplate.exchange("https://api.spotify.com/v1/me/top/artists", HttpMethod.GET, entity, SpotifyPageableArtists.class);
                HttpStatusCode code = response.getStatusCode();

                if(code.is2xxSuccessful()) {
                    return ResponseEntity.ok().headers(headers).body(response.getBody());
                }
                    throw new Exception(code.toString());
            }
            catch (Exception e)
            {
                if(e.getMessage().contains("403")){
                    return  new ResponseEntity<>(   null, HttpStatus.FORBIDDEN);
                }
                if(refresh == null){
                    return  new ResponseEntity<>(HttpStatus.FORBIDDEN);
                }
                String newToken = authService.getRefreshedToken(refresh);

                headers.set("Authorization", newToken);
                HttpEntity<String> newEntity = new HttpEntity<>(null, headers);
                ResponseEntity<SpotifyPageableArtists> response = restTemplate.exchange("https://api.spotify.com/v1/me/top/artists", HttpMethod.GET, entity, SpotifyPageableArtists.class);
                HttpHeaders responseHeaders = new HttpHeaders();
                responseHeaders.add("Authorization", newToken);
                return ResponseEntity.ok().headers(responseHeaders).body(response.getBody());
            }
        }
        return  new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

    @Override
    public ResponseEntity<SpotifyArtistResponse> getArtistById(String token, String refresh, String id) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", token);
        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        try {
            ResponseEntity<SpotifyArtist> responseArtist = restTemplate.exchange("https://api.spotify.com/v1/artists/" + id, HttpMethod.GET, entity, SpotifyArtist.class);
            ResponseEntity<SpotifyArtistTopTracks> responseTracks = getArtistTopTracks(token, refresh, id);
            ResponseEntity<SpotifyPageableAlbums> responseAlbums = getArtistAlbums(token, refresh, id);
            HttpStatusCode code = responseArtist.getStatusCode();
            SpotifyArtistResponse response = new SpotifyArtistResponse();
            response.setInfo(responseArtist.getBody());
            response.setPopularSongs(responseTracks.getBody());
            response.setDiscography(responseAlbums.getBody());
            if(code.is2xxSuccessful()){
                return ResponseEntity.ok().headers(headers).body(response);
            }
        }
        catch (Exception e)
        {
            if(refresh != null){
                String newToken = authService.getRefreshedToken(refresh);
                headers.set("Authorization", newToken);
                HttpEntity<String> newEntity = new HttpEntity<>(null, headers);
                ResponseEntity<SpotifyArtist> responseArtist = restTemplate.exchange("https://api.spotify.com/v1/artists/" + id, HttpMethod.GET, entity, SpotifyArtist.class);
                ResponseEntity<SpotifyArtistTopTracks> responseTracks = getArtistTopTracks(token, refresh, id);
                ResponseEntity<SpotifyPageableAlbums> responseAlbums = getArtistAlbums(token, refresh, id);
                HttpStatusCode code = responseArtist.getStatusCode();
                SpotifyArtistResponse response = new SpotifyArtistResponse();
                response.setInfo(responseArtist.getBody());
                response.setPopularSongs(responseTracks.getBody());
                response.setDiscography(responseAlbums.getBody());
                if(code.is2xxSuccessful()){
                    return ResponseEntity.ok().headers(headers).body(response);
                }
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
                ResponseEntity<SpotifyArtistTopTracks> response = restTemplate.exchange("https://api.spotify.com/v1/artists/" + id + "/top-tracks", HttpMethod.GET, entity, SpotifyArtistTopTracks.class);
                HttpStatusCode code = response.getStatusCode();
                if(code.is2xxSuccessful()){
                    return ResponseEntity.ok().headers(headers).body(response.getBody());
                }
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
            ResponseEntity<SpotifyPageableAlbums> response = restTemplate.exchange("https://api.spotify.com/v1/artists/" + id + "/albums", HttpMethod.GET, entity, SpotifyPageableAlbums.class);
            HttpStatusCode code = response.getStatusCode();
            if(code.is2xxSuccessful()){
                return ResponseEntity.ok().headers(headers).body(response.getBody());
            }
        }
        catch (Exception e)
        {
            if(refresh != null){
                String newToken = authService.getRefreshedToken(refresh);
                headers.set("Authorization", newToken);
                HttpEntity<String> newEntity = new HttpEntity<>(null, headers);
                ResponseEntity<SpotifyPageableAlbums> response = restTemplate.exchange("https://api.spotify.com/v1/artists/" + id + "/albums", HttpMethod.GET, entity, SpotifyPageableAlbums.class);
                HttpStatusCode code = response.getStatusCode();
                if(code.is2xxSuccessful()){
                    return ResponseEntity.ok().headers(headers).body(response.getBody());
                }
            }
        }
        return null;
    }

    @Override
    public ResponseEntity<SpotifyAlbum> getAlbumById(String token, String refresh, String id) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", token);
        HttpEntity<SpotifyPageableArtists> entity = new HttpEntity<>(null, headers);
        try {
            ResponseEntity<SpotifyAlbum> response = restTemplate.exchange("https://api.spotify.com/v1/albums/" + id, HttpMethod.GET, entity, SpotifyAlbum.class);
            HttpStatusCode code = response.getStatusCode();
            if(code.is2xxSuccessful()){
                return ResponseEntity.ok().headers(headers).body(response.getBody());
            }
        }
        catch (Exception e)
        {
            if(refresh != null){
                String newToken = authService.getRefreshedToken(refresh);
                headers.set("Authorization", newToken);
                HttpEntity<String> newEntity = new HttpEntity<>(null, headers);
                ResponseEntity<SpotifyAlbum> response = restTemplate.exchange("https://api.spotify.com/v1/albums/" + id, HttpMethod.GET, entity, SpotifyAlbum.class);
                HttpStatusCode code = response.getStatusCode();
                if(code.is2xxSuccessful()){
                    return ResponseEntity.ok().headers(headers).body(response.getBody());
                }
            }
        }
        return null;
    }

}
