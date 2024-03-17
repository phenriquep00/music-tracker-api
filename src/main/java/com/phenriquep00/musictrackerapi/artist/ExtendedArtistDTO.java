package com.phenriquep00.musictrackerapi.artist;

import lombok.Data;
import se.michaelthelin.spotify.model_objects.specification.Artist;

@Data
public class ExtendedArtistDTO extends ArtistDTO {

    public ExtendedArtistDTO(Artist artist) {
        super(artist);
    }
    
}
