package com.phenriquep00.musictrackerapi.auth;

import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.credentials.AuthorizationCodeCredentials;
import se.michaelthelin.spotify.requests.authorization.authorization_code.AuthorizationCodeRequest;
import se.michaelthelin.spotify.requests.authorization.authorization_code.AuthorizationCodeUriRequest;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/auth")
public class AuthController {

  public String acessToken = " ";

  @Autowired
  private SpotifyApi spotifyApi;

  private String code = "";

  @GetMapping("/login")
  public String getUserCode() {
    AuthorizationCodeUriRequest authorizationCodeUriRequest = spotifyApi
      .authorizationCodeUri()
      .scope(
        "user-read-currently-playing, user-read-recently-played, user-read-playback-state, user-top-read, user-modify-playback-state, user-library-read, playlist-read-private, playlist-read-collaborative"
      )
      .show_dialog(true)
      .build();

    final URI uri = authorizationCodeUriRequest.execute();

    return uri.toString();
  }

  @GetMapping("/get-user-code/")
  public String getSpotifyUserCode(
    @RequestParam("code") String userCode,
    HttpServletResponse response
  ) throws IOException {
    code = userCode;

    AuthorizationCodeRequest authorizationCodeRequest = spotifyApi
      .authorizationCode(code)
      .build();

    try {
      final AuthorizationCodeCredentials authorizationCodeCredentials = authorizationCodeRequest.execute();

      spotifyApi.setAccessToken(authorizationCodeCredentials.getAccessToken());
      spotifyApi.setRefreshToken(
        authorizationCodeCredentials.getRefreshToken()
      );

      System.out.println(
        "Expires in: " + authorizationCodeCredentials.getExpiresIn()
      );
    } catch (
      IOException
      | SpotifyWebApiException
      | org.apache.hc.core5.http.ParseException e
    ) {
      System.out.println(
        "Error: (inside get spotify user code)" + e.getMessage()
      );
    }

    response.sendRedirect("http://localhost:4200/home");

    System.out.println(spotifyApi.getAccessToken());
    acessToken = spotifyApi.getAccessToken();

    return spotifyApi.getAccessToken();
  }

  @GetMapping("/token-callback")
  public ResponseEntity<String> getTokenCallback() {
    return ResponseEntity.status(HttpStatus.OK).body(acessToken);
  }
}
