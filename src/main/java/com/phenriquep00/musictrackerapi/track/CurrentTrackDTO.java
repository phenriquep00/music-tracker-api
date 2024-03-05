package com.phenriquep00.musictrackerapi.track;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.model_objects.miscellaneous.CurrentlyPlaying;
import se.michaelthelin.spotify.model_objects.specification.ArtistSimplified;
import se.michaelthelin.spotify.model_objects.specification.Track;
import se.michaelthelin.spotify.requests.data.tracks.GetTrackRequest;

@Data
public class CurrentTrackDTO {

  @Autowired
  private SpotifyApi spotifyApi;

  private String id;
  private Long timeStamp;
  private Integer progressMs;
  private Boolean isPlaying;
  private String trackName;
  private Integer duration;
  private String uri;
  private ArtistSimplified[] artists;
  private Boolean explicit;
  private Boolean isPlayable;

  public CurrentTrackDTO(CurrentlyPlaying currentlyPlaying) {
    this.id = currentlyPlaying.getItem().getId();
    this.timeStamp = currentlyPlaying.getTimestamp();
    this.progressMs = currentlyPlaying.getProgress_ms();
    this.isPlaying = currentlyPlaying.getIs_playing();
    this.trackName = currentlyPlaying.getItem().getName();
    this.duration = currentlyPlaying.getItem().getDurationMs();
    this.uri = currentlyPlaying.getItem().getUri();
  }
}
