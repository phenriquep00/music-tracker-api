package com.phenriquep00.musictrackerapi.genre;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity(name = "tb_genre")
public class GenreModel {
    @Id
    private String genreId;

    private String name;
    private String description;
}
