package com.phenriquep00.musictrackerapi.player;

import com.phenriquep00.musictrackerapi.track.CurrentTrackDTO;

import java.io.IOException;

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
import se.michaelthelin.spotify.model_objects.miscellaneous.CurrentlyPlaying;
import se.michaelthelin.spotify.requests.data.player.GetUsersCurrentlyPlayingTrackRequest;
import se.michaelthelin.spotify.requests.data.player.StartResumeUsersPlaybackRequest;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/player")
public class PlayerController {

  @Autowired
  private SpotifyApi spotifyApi;

  @GetMapping("/current-track")
  public ResponseEntity<CurrentlyPlaying> getCurrentTrack() {
    final GetUsersCurrentlyPlayingTrackRequest getUsersCurrentlyPlayingTrackRequest = spotifyApi
      .getUsersCurrentlyPlayingTrack()
      .build();

    try {
      final CurrentlyPlaying currentlyPlaying = getUsersCurrentlyPlayingTrackRequest.execute();
      // CurrentTrackDTO currentTrack = new CurrentTrackDTO(currentlyPlaying);
      System.out.println(currentlyPlaying);
      return ResponseEntity.ok(currentlyPlaying);
    } catch (IOException | SpotifyWebApiException | ParseException e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }

  @GetMapping("/toggle-play")
  public ResponseEntity<String> togglePlay() {
    final StartResumeUsersPlaybackRequest startResumeUsersPlaybackRequest = spotifyApi.startResumeUsersPlayback().build();

    try{
        final String string = startResumeUsersPlaybackRequest.execute();
        
        return ResponseEntity.ok(string);
    } catch (IOException | SpotifyWebApiException | ParseException e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }
}
