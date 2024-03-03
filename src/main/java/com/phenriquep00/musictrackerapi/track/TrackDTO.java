package com.phenriquep00.musictrackerapi.track;

import lombok.Data;
import se.michaelthelin.spotify.model_objects.specification.ArtistSimplified;
import se.michaelthelin.spotify.model_objects.specification.TrackSimplified;

@Data
public class TrackDTO {
    private String id;
    private String trackName;
    private Integer duration;
    private String uri;
    private ArtistSimplified[] artists;
    private Boolean explicit;
    private Boolean isPlayable;

    public TrackDTO(TrackSimplified track) {
        this.id = track.getId();
        this.trackName = track.getName();
        this.duration = track.getDurationMs();
        this.uri = track.getUri();
        this.artists = track.getArtists();
        this.explicit = track.getIsExplicit();
        this.isPlayable = track.getIsPlayable();
    }
    
}
