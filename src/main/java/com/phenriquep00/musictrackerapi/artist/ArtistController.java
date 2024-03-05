package com.phenriquep00.musictrackerapi.artist;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.hc.core5.http.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.specification.Artist;
import se.michaelthelin.spotify.model_objects.specification.Paging;
import se.michaelthelin.spotify.requests.data.personalization.simplified.GetUsersTopArtistsRequest;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/artist")
public class ArtistController {

  @Autowired
  private SpotifyApi spotifyApi;

  @GetMapping("/user-top-artists")
  public ResponseEntity<List<ArtistDTO>> getUserTopArtists() {
    final GetUsersTopArtistsRequest getUsersTopArtistsRequest = spotifyApi.getUsersTopArtists().build();

    try {
        final Paging<Artist> artistPaging = getUsersTopArtistsRequest.execute();

        final Artist[] artistsList = artistPaging.getItems();

        List<ArtistDTO> userTopArtists = Arrays
            .stream(Optional.ofNullable(artistsList).orElse(new Artist[0]))
            .map(ArtistDTO::new)
            .collect(Collectors.toList());

        return ResponseEntity.ok(userTopArtists);
    } catch (IOException | SpotifyWebApiException | ParseException e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }
}
