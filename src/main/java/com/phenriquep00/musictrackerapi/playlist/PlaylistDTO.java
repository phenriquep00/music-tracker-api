package com.phenriquep00.musictrackerapi.playlist;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.phenriquep00.musictrackerapi.track.TrackDTO;

import lombok.Data;
import se.michaelthelin.spotify.enums.ModelObjectType;
import se.michaelthelin.spotify.model_objects.specification.ExternalUrl;
import se.michaelthelin.spotify.model_objects.specification.Image;
import se.michaelthelin.spotify.model_objects.specification.Paging;
import se.michaelthelin.spotify.model_objects.specification.Playlist;
import se.michaelthelin.spotify.model_objects.specification.PlaylistSimplified;
import se.michaelthelin.spotify.model_objects.specification.PlaylistTrack;
import se.michaelthelin.spotify.model_objects.specification.User;

@Data
public class PlaylistDTO {

  private  String id;
  private  Image[] images;
  private  String name;
  private  User owner;
  private  Boolean publicAccess;
  private  String snapshotId;
  private  Boolean collaborative;
  private  ExternalUrl externalUrls;
  private  List<TrackDTO> tracks;
  private  ModelObjectType type;
  private  String uri;

  public PlaylistDTO(PlaylistSimplified playlistSimplified) {
    this.id = playlistSimplified.getId();
    this.images = playlistSimplified.getImages();
    this.name = playlistSimplified.getName();
    this.owner = playlistSimplified.getOwner();
    this.publicAccess = playlistSimplified.getIsPublicAccess();
    this.snapshotId = playlistSimplified.getSnapshotId();
    this.externalUrls = playlistSimplified.getExternalUrls();
    // this.tracks = playlistSimplified.getTracks();
    this.type = playlistSimplified.getType();
    this.uri = playlistSimplified.getUri();

  }

  public PlaylistDTO(Playlist playlist) {
    this.id = playlist.getId();
    this.images = playlist.getImages();
    this.name = playlist.getName();
    this.owner = playlist.getOwner();
    this.publicAccess = playlist.getIsPublicAccess();
    this.snapshotId = playlist.getSnapshotId();
    this.collaborative = playlist.getIsCollaborative();
    this.externalUrls = playlist.getExternalUrls();
    this.tracks = this.convertSpotifyPagingTrackToTrackDTO(playlist.getTracks());
    this.type = playlist.getType();
    this.uri = playlist.getUri();
  }

  private List<TrackDTO> convertSpotifyPagingTrackToTrackDTO(Paging<PlaylistTrack> pagedTracks) {
    final PlaylistTrack[] tracklist = pagedTracks.getItems();

    List<TrackDTO> parsedTracks = Arrays
      .stream(Optional.ofNullable(tracklist).orElse(new PlaylistTrack[0]))
      .map(TrackDTO::new)
      .collect(Collectors.toList());
    return parsedTracks;
  }
}
