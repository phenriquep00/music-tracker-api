package com.phenriquep00.musictrackerapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.SpotifyHttpManager;
import org.springframework.beans.factory.annotation.Value;

@Configuration
public class SpotifyApiConfig {

    // ...
    @Value("${spring.application.spotify_client_id}")
    String clientId = "your-client-id";

    @Value("${spring.application.spotify_client_secret}")
    private String clientSecret;

    String redirectUri = "http://localhost:8080/auth/get-user-code/";

    @Bean
    public SpotifyApi spotifyApi() {

        return new SpotifyApi.Builder()
                .setClientId(clientId)
                .setClientSecret(clientSecret)
                .setRedirectUri(SpotifyHttpManager.makeUri(redirectUri))
                .build();
    }
}
