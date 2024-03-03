package com.phenriquep00.musictrackerapi.album;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.phenriquep00.musictrackerapi.track.TrackDTO;

import lombok.Data;
import se.michaelthelin.spotify.model_objects.specification.*;

@Data
public class AlbumDTO {
    
    private String id;
    private String albumName;
    private ArtistSimplified[] artists; 
    private String[] genres; 
    private Image[] images;
    private String year;
    private List<TrackDTO> tracks;

    public AlbumDTO(SavedAlbum savedAlbum) {
        this.id = savedAlbum.getAlbum().getId();
        this.albumName = savedAlbum.getAlbum().getName();
        this.artists = savedAlbum.getAlbum().getArtists();
        this.genres = savedAlbum.getAlbum().getGenres();
        this.images = savedAlbum.getAlbum().getImages();
    }

    public AlbumDTO(Album album) {
        this.id = album.getId();
        this.albumName = album.getName();
        this.artists = album.getArtists();
        this.genres = album.getGenres();
        this.images = album.getImages();
        this.year = album.getReleaseDate();
        this.tracks = convertTrackSimplifiedToTrackDTO(album.getTracks().getItems());
    }

    public List<TrackDTO> convertTrackSimplifiedToTrackDTO(TrackSimplified[] inputTracksSimplified) {
        List<TrackSimplified> tracks = Arrays.asList(inputTracksSimplified);

        List<TrackDTO> parsedTracks = tracks.stream()
                .map(TrackDTO::new)
                .collect(Collectors.toList());

        return parsedTracks;
    }
}
