package com.phenriquep00.musictrackerapi.album;

import lombok.Data;
import se.michaelthelin.spotify.model_objects.specification.*;

@Data
public class AlbumDTO {
    
    private String id;
    private String albumName;
    private ArtistSimplified[] artists; 
    private String[] genres; 
    private Image[] images;

    public AlbumDTO(SavedAlbum savedAlbum) {
        this.id = savedAlbum.getAlbum().getId();
        this.albumName = savedAlbum.getAlbum().getName();
        this.artists = savedAlbum.getAlbum().getArtists();
        this.genres = savedAlbum.getAlbum().getGenres();
        this.images = savedAlbum.getAlbum().getImages();
    }
}
