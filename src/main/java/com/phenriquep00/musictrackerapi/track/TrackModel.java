package com.phenriquep00.musictrackerapi.track;


import java.util.HashSet;
import java.util.Set;

import com.phenriquep00.musictrackerapi.album.AlbumModel;
import com.phenriquep00.musictrackerapi.artist.ArtistModel;
import com.phenriquep00.musictrackerapi.genre.GenreModel;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.Data;


@Data
@Entity(name = "tb_track")
public class TrackModel {
    @Id
    private String TrackId;

    private String title;
    private int duration;

    @ManyToOne
    @JoinColumn(name = "album_id")
    private AlbumModel album;

    @ManyToOne
    @JoinColumn(name = "artist_id")
    private ArtistModel artist;

    @ManyToMany
    @JoinTable(
      name = "track_genre", 
      joinColumns = @JoinColumn(name = "track_id"), 
      inverseJoinColumns = @JoinColumn(name = "genre_id"))
    private Set<GenreModel> genres = new HashSet<>();
}
