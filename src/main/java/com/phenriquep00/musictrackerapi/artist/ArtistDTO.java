package com.phenriquep00.musictrackerapi.artist;

import lombok.Data;
import se.michaelthelin.spotify.model_objects.specification.Artist;
import se.michaelthelin.spotify.model_objects.specification.Image;

@Data
public class ArtistDTO {

  private final String id;
  private final Image[] images;
  private final String name;
  private final String href;
  private final String[] genres;
  private final String uri;

  public ArtistDTO(Artist artist) {
    this.id = artist.getId();
    this.images = artist.getImages();
    this.name = artist.getName();
    this.href = artist.getHref();
    this.genres = artist.getGenres();
    this.uri = artist.getUri();
  }
}
