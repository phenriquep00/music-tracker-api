package com.phenriquep00.musictrackerapi.album;

import java.util.Date;
import java.util.HashSet;


import com.phenriquep00.musictrackerapi.artist.ArtistModel;
import com.phenriquep00.musictrackerapi.genre.*;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;
import java.util.Set;

@Data
@Entity(name = "tb_album")
public class AlbumModel {
    @Id
    private String albumId;

    private String title;

    @Temporal(TemporalType.DATE)
    private Date releaseDate;

    @ManyToOne
    private ArtistModel artist;

    @ManyToMany
    @JoinTable(
      name = "album_genre", 
      joinColumns = @JoinColumn(name = "album_id"), 
      inverseJoinColumns = @JoinColumn(name = "genre_id"))
    private Set<GenreModel> genres = new HashSet<>();
}
