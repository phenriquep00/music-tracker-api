package com.phenriquep00.musictrackerapi.album;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.specification.Album;
import se.michaelthelin.spotify.model_objects.specification.Artist;
import se.michaelthelin.spotify.model_objects.specification.Paging;
import se.michaelthelin.spotify.model_objects.specification.SavedAlbum;
import se.michaelthelin.spotify.requests.data.albums.GetAlbumRequest;
import se.michaelthelin.spotify.requests.data.library.GetCurrentUsersSavedAlbumsRequest;
import se.michaelthelin.spotify.requests.data.personalization.simplified.GetUsersTopArtistsRequest;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/album")
public class AlbumController {

  @Autowired
  private SpotifyApi spotifyApi;

  @GetMapping("/top-artists")
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

  @GetMapping("/get-user-recent-albums")
  public ResponseEntity<List<AlbumDTO>> getUserRecentAlbums() {
    try {
      SavedAlbum[] savedAlbums = getUserSavedAlbums();

      List<AlbumDTO> userRecentAlbums = Arrays
        .stream(Optional.ofNullable(savedAlbums).orElse(new SavedAlbum[0]))
        .map(AlbumDTO::new)
        .collect(Collectors.toList());

      return ResponseEntity.ok(userRecentAlbums);
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }

  @GetMapping("/get-album/{albumId}")
  public ResponseEntity<AlbumDTO> getAlbumById(@PathVariable String albumId) {
    final GetAlbumRequest getAlbumRequest = spotifyApi
      .getAlbum(albumId)
      .build();

    try {
      Album response = getAlbumRequest.execute();
      AlbumDTO album = new AlbumDTO(response);
      
      return ResponseEntity.ok(album);

    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }

  // TODO: move this method to another layer, since it's not a endpoint to be inside the controller
  public SavedAlbum[] getUserSavedAlbums() {
    try {
      final GetCurrentUsersSavedAlbumsRequest getCurrentUsersSavedAlbumsRequest = spotifyApi
        .getCurrentUsersSavedAlbums()
        .limit(10)
        .offset(0)
        .build();

      final Paging<SavedAlbum> albumPaging = getCurrentUsersSavedAlbumsRequest.execute();
      return albumPaging.getItems();
    } catch (Exception e) {
      System.out.println("Something went wrong! \n" + e.getMessage());
    }

    return new SavedAlbum[0];
  }
}
