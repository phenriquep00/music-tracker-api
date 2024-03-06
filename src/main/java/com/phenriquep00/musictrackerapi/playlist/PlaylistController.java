package com.phenriquep00.musictrackerapi.playlist;

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
import se.michaelthelin.spotify.model_objects.special.FeaturedPlaylists;
import se.michaelthelin.spotify.model_objects.specification.Paging;
import se.michaelthelin.spotify.model_objects.specification.PlaylistSimplified;
import se.michaelthelin.spotify.requests.data.browse.GetListOfFeaturedPlaylistsRequest;
import se.michaelthelin.spotify.requests.data.playlists.GetListOfCurrentUsersPlaylistsRequest;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/playlist")
public class PlaylistController {

  @Autowired
  private SpotifyApi spotifyApi;

  @GetMapping("/user-playlists")
  public ResponseEntity<List<PlaylistDTO>> getCurrentUserPlaylists() {
    final GetListOfCurrentUsersPlaylistsRequest getListOfCurrentUsersPlaylistsRequest = spotifyApi
      .getListOfCurrentUsersPlaylists()
      .build();

    try {
      final Paging<PlaylistSimplified> playlistSimplifiedPaging = getListOfCurrentUsersPlaylistsRequest.execute();
      final PlaylistSimplified[] pagingPlaylistSimplifiedItems = playlistSimplifiedPaging.getItems();

      List<PlaylistDTO> responsePlaylist = Arrays
        .stream(
          Optional
            .ofNullable(pagingPlaylistSimplifiedItems)
            .orElse(new PlaylistSimplified[0])
        )
        .map(PlaylistDTO::new)
        .collect(Collectors.toList());

      return ResponseEntity.ok(responsePlaylist);
    } catch (IOException | SpotifyWebApiException | ParseException e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }

  @GetMapping("/user-featured-playlists")
  public ResponseEntity<List<PlaylistDTO>> getFeaturedPlaylists() {
    final GetListOfFeaturedPlaylistsRequest getListOfFeaturedPlaylistsRequest = spotifyApi
      .getListOfFeaturedPlaylists()
      //          .country(CountryCode.SE)
      //          .limit(10)
      //          .offset(0)
      //          .timestamp(new Date(1414054800000L))
      .build();

    try {
      final FeaturedPlaylists featuredPlaylists = getListOfFeaturedPlaylistsRequest.execute();
      final Paging<PlaylistSimplified> pagedPlaylists = featuredPlaylists.getPlaylists();
      final PlaylistSimplified[] playlists = pagedPlaylists.getItems();

      List<PlaylistDTO> parsedPlaylists = Arrays
        .stream(
          Optional.ofNullable(playlists).orElse(new PlaylistSimplified[0])
        )
        .map(PlaylistDTO::new)
        .collect(Collectors.toList());

      return ResponseEntity.ok(parsedPlaylists);
    } catch (IOException | SpotifyWebApiException | ParseException e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }
}
