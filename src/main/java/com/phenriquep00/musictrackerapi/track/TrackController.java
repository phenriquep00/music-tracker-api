package com.phenriquep00.musictrackerapi.track;

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
import se.michaelthelin.spotify.model_objects.specification.Paging;
import se.michaelthelin.spotify.model_objects.specification.Track;
import se.michaelthelin.spotify.requests.data.personalization.simplified.GetUsersTopTracksRequest;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/track")
public class TrackController {

  @Autowired
  private SpotifyApi spotifyApi;

  @GetMapping("/get-user-top-tracks")
  public ResponseEntity<List<TrackDTO>> getUserTopTracks() {
    final GetUsersTopTracksRequest getUsersTopTracksRequest = spotifyApi.getUsersTopTracks()
//          .limit(10)
//          .offset(0)
    .time_range("long_term")
    .build();

    try {
        final Paging<Track> trackPaging = getUsersTopTracksRequest.execute();
        final Track[] pagedTracks = trackPaging.getItems();

        List<TrackDTO> parsedTracks = Arrays
            .stream(Optional.ofNullable(pagedTracks).orElse(new Track[0]))
            .map(TrackDTO::new)
            .collect(Collectors.toList());

        return ResponseEntity.ok(parsedTracks);

    } catch (IOException | SpotifyWebApiException | ParseException e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }
}
