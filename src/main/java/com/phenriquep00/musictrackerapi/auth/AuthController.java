package com.phenriquep00.musictrackerapi.auth;

import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.SpotifyHttpManager;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.credentials.AuthorizationCodeCredentials;
import se.michaelthelin.spotify.model_objects.specification.Album;
import se.michaelthelin.spotify.model_objects.specification.Artist;
import se.michaelthelin.spotify.model_objects.specification.Paging;
import se.michaelthelin.spotify.model_objects.specification.SavedAlbum;
import se.michaelthelin.spotify.requests.authorization.authorization_code.AuthorizationCodeRequest;
import se.michaelthelin.spotify.requests.authorization.authorization_code.AuthorizationCodeUriRequest;
import se.michaelthelin.spotify.requests.data.library.GetCurrentUsersSavedAlbumsRequest;
import se.michaelthelin.spotify.requests.data.personalization.simplified.GetUsersTopArtistsRequest;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/auth")
public class AuthController {

  @Value("${spring.application.spotify_client_id}")
  private String clientId;

  @Value("${spring.application.spotify_client_secret}")
  private String clientSecret;

  private static final URI redirectUri = SpotifyHttpManager.makeUri(
    "http://localhost:8080/auth/get-user-code/"
  );
  private String code = "";

  private SpotifyApi spotifyApi;

  @jakarta.annotation.PostConstruct
  public void initializeSpotifyApi() {
    this.spotifyApi =
      new SpotifyApi.Builder()
        .setClientId(clientId)
        .setClientSecret(clientSecret)
        .setRedirectUri(redirectUri)
        .build();
  }

  @GetMapping("/login")
  public String getUserCode() {
    AuthorizationCodeUriRequest authorizationCodeUriRequest = spotifyApi
      .authorizationCodeUri()
      .scope("user-read-private, user-read-email, user-top-read, user-library-read")
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

      // Set access and refresh token for further "spotifyApi" object usage
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
      System.out.println("Error: " + e.getMessage());
    }

    response.sendRedirect("http://localhost:4200/home");

    return spotifyApi.getAccessToken();
  }

  // TODO: move this from auth controller to user controler
  @GetMapping("user-top-artists")
  public Artist[] getUserTopArtists() {
    final GetUsersTopArtistsRequest getUsersTopArtistsRequest = spotifyApi
      .getUsersTopArtists()
      .time_range("medium_term")
      .limit(10)
      .offset(5)
      .build();

    try {
      final Paging<Artist> artistPaging = getUsersTopArtistsRequest.execute();

      return artistPaging.getItems();
    } catch (Exception e) {
      System.out.println("Something went wrong! \n" + e.getMessage());
    }

    return new Artist[0];
  }

  @GetMapping("user-saved-albums")
public SavedAlbum[] getUserSavedAlbums() {
    try {
        final GetCurrentUsersSavedAlbumsRequest getCurrentUsersSavedAlbumsRequest = spotifyApi
                .getCurrentUsersSavedAlbums()
                .limit(10)
                .offset(0)
                .build();

        final Paging<SavedAlbum> albumPaging = getCurrentUsersSavedAlbumsRequest.execute();

        return albumPaging.getItems();
    } catch (SpotifyWebApiException e) {
        System.out.println("Spotify API error: " + e.getMessage());
    } catch (IOException e) {
        System.out.println("IO error: " + e.getMessage());
    } catch (Exception e) {
        System.out.println("Something went wrong! \n" + e.getMessage());
    }

    return new SavedAlbum[0];
}

}
