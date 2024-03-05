package com.phenriquep00.musictrackerapi.playlist;

import lombok.Data;
import se.michaelthelin.spotify.enums.ModelObjectType;
import se.michaelthelin.spotify.model_objects.miscellaneous.PlaylistTracksInformation;
import se.michaelthelin.spotify.model_objects.specification.ExternalUrl;
import se.michaelthelin.spotify.model_objects.specification.Image;
import se.michaelthelin.spotify.model_objects.specification.PlaylistSimplified;
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
  private  PlaylistTracksInformation tracks;
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
    this.tracks = playlistSimplified.getTracks();
    this.type = playlistSimplified.getType();
    this.uri = playlistSimplified.getUri();
    
  }
}
