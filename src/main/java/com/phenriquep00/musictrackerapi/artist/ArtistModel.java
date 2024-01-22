package com.phenriquep00.musictrackerapi.artist;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;


@Data
@Entity(name = "tb_artist")
public class ArtistModel {
    @Id
    private String ArtistId;

    private String name;
    private String bio;
}
