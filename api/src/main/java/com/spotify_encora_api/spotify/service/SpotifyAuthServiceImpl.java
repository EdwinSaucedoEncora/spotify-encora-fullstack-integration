package com.spotify_encora_api.spotify.service;

import com.spotify_encora_api.spotify.model.SpotifyAuthRequestBody;
import com.spotify_encora_api.spotify.model.SpotifyResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;
import java.util.UUID;

@Service
public class SpotifyAuthServiceImpl implements SpotifyAuthService {
    @Value("${SPOTIFY.CLIENT_ID}")
    private String clientId;

    @Value(("${SPOTIFY.CREDENTIALS}"))
    private String credentials;

    @Override
    public String redirectToSpotifyAuth() {
        String state = UUID.randomUUID().toString().replaceAll("-", "").substring(0, 8);
        String url =
                ("https://accounts.spotify.com/authorize?")
                        .concat("client_id=" + clientId)
                        .concat("&show_dialog=" + "true")
                        .concat("&scope=user-read-private user-read-email user-top-read")
                        .concat("&redirect_uri=http://localhost:3000/oauth")
                        .concat("&state=")
                        .concat(state)
                        .concat("&response_type=code");
        return "redirect:" + url;
    }

    public String getRefreshedToken(String refresh){
        SpotifyAuthRequestBody body = new SpotifyAuthRequestBody();

        if(refresh != null){
            HttpHeaders headers = new HttpHeaders();
            headers.set("Content-Type", MediaType.APPLICATION_FORM_URLENCODED.toString());
            headers.set("Authorization","Basic ".concat(this.credentials));
            body.setRefresh_token(refresh);
            body.setGrant_type("refresh_token");
            RestTemplate restTemplate = new RestTemplate();
            HttpEntity<MultiValueMap<String, String>> requestHttpEntity = new HttpEntity<>(body.getFormData(), headers);
            try {
                ResponseEntity<SpotifyResponse> responseEntity = restTemplate.exchange("https://accounts.spotify.com/api/token", HttpMethod.POST, requestHttpEntity, SpotifyResponse.class);
                SpotifyResponse response = responseEntity.getBody();
                return "Bearer " + response.getAccess_token();
            }
            catch (Exception e)
            {
                return null;
            }
        }
        return  null;
    }

    @Override
    public String redirectWithAccessToken(String state, Optional<String> error, Optional<String> code) {

        StringBuilder url = new StringBuilder("http://localhost:8080/callback");
        url.append("?state=").append(state).append("&");
        if(error.isPresent()){
            url.append("error=").append(error);
        }
        else {
            HttpHeaders headers = new HttpHeaders();
            headers.set("Content-Type", MediaType.APPLICATION_FORM_URLENCODED.toString());
            headers.set("Authorization","Basic ".concat(this.credentials));
            SpotifyAuthRequestBody body = new SpotifyAuthRequestBody();
            if(code.isPresent()){
                body.setCode(code.get());
                body.setRedirect_uri("http://localhost:3000/oauth");
                body.setGrant_type("authorization_code");

                RestTemplate restTemplate = new RestTemplate();
                HttpEntity<MultiValueMap<String, String>> requestHttpEntity = new HttpEntity<>(body.getFormData(), headers);
                try {
                    ResponseEntity<SpotifyResponse> responseEntity = restTemplate.exchange("https://accounts.spotify.com/api/token", HttpMethod.POST, requestHttpEntity, SpotifyResponse.class);
                    SpotifyResponse response = responseEntity.getBody();
                    assert response != null;
                    url.append(response.getUrlParameters());
                }
                catch (Exception e)
                {
                    return "redirect:" + url + "?" + e.toString();
                }
            }


        }

        return "redirect:" + url;
    }
}
