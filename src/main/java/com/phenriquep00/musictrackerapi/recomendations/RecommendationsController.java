package com.phenriquep00.musictrackerapi.recomendations;

import com.phenriquep00.musictrackerapi.track.TrackDTO;
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
import se.michaelthelin.spotify.model_objects.specification.Recommendations;
import se.michaelthelin.spotify.model_objects.specification.Track;
import se.michaelthelin.spotify.requests.data.browse.GetRecommendationsRequest;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/recommendations")
public class RecommendationsController {

  @Autowired
  private SpotifyApi spotifyApi;

  @GetMapping("/get-track-recommendations")
  public ResponseEntity<Track[]> getTrackRecommendations() {
    System.out.println("on get track recomendation , start building request");
    final GetRecommendationsRequest getRecommendationsRequest = spotifyApi
      .getRecommendations()
      .limit(10)
      .build();
      System.out.println("request built");

    try {
      System.out.println("on try block, executing request");
      final Recommendations recommendations = getRecommendationsRequest.execute();
      System.out.println("request executed, getting tracks");
      final Track[] recommendedTracks = recommendations.getTracks();
      System.out.println("tracks fetched");

      List<TrackDTO> parsedRecommendedTracks = Arrays
        .stream(Optional.ofNullable(recommendedTracks).orElse(new Track[0]))
        .map(TrackDTO::new)
        .collect(Collectors.toList());

      System.out.println(parsedRecommendedTracks);
      System.out.println(recommendedTracks);
      return ResponseEntity.ok(recommendedTracks);

    } catch (IOException | SpotifyWebApiException | ParseException e) {
      System.out.println("failed: " + e);
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }
}
