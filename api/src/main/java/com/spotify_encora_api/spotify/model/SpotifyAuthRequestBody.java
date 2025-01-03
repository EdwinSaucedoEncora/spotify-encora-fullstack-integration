package com.spotify_encora_api.spotify.model;

import lombok.Data;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@Data
public class SpotifyRequestBody {
    private String grant_type;
    private String code;
    private String redirect_uri;

    public MultiValueMap<String, String> getFormData(){
        MultiValueMap<String, String> formData =  new LinkedMultiValueMap<>();
        formData.add("grant_type", grant_type);
        formData.add("code", code);
        formData.add("redirect_uri", redirect_uri);
        return formData;
    }
}
