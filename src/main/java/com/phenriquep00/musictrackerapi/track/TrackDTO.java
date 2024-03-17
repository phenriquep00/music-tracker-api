package com.phenriquep00.musictrackerapi.track;

import lombok.Data;
import se.michaelthelin.spotify.model_objects.specification.ArtistSimplified;
import se.michaelthelin.spotify.model_objects.specification.PlaylistTrack;
import se.michaelthelin.spotify.model_objects.specification.Track;
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

    public TrackDTO(Track track) {
        this.id = track.getId();
        this.trackName = track.getName();
        this.duration = track.getDurationMs();
        this.uri = track.getUri();
        this.artists = track.getArtists();
        this.explicit = track.getIsExplicit();
        this.isPlayable = track.getIsPlayable();
    }

    public TrackDTO(PlaylistTrack track) {
        this.id = track.getTrack().getId();
        this.trackName = track.getTrack().getName();
        this.duration = track.getTrack().getDurationMs();
        this.uri = track.getTrack().getUri();
        this.artists = new ArtistSimplified[0];
        this.explicit = false;
        this.isPlayable = true;
    }
    
}
