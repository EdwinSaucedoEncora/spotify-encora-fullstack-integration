package com.spotify_encora_api.spotify.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SpotifyResponse {
    private String access_token;
    private String token_type;
    private String scope;
    private Integer expires_in;
    private String refresh_token;

    public String getUrlParameters(){
        try {
            return "access_token="
                    .concat(access_token)
                    .concat("&token_type=")
                    .concat(token_type)
                    .concat("&scope=")
                    .concat(scope)
                    .concat("&expires_in=")
                    .concat(expires_in.toString())
                    .concat("&refresh_token=")
                    .concat(refresh_token);
        }
        catch (Exception e){
            return "";
        }
    }
}
