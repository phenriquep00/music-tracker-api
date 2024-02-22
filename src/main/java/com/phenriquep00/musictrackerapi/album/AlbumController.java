package com.phenriquep00.musictrackerapi.album;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.model_objects.specification.Artist;
import se.michaelthelin.spotify.model_objects.specification.Paging;
import se.michaelthelin.spotify.model_objects.specification.SavedAlbum;
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

    // TODO: change this to return a list of AlbumDTO instead os SavedAlbum
    @GetMapping("/saved-albums")
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
