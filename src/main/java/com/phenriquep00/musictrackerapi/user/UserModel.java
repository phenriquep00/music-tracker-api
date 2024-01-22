package com.phenriquep00.musictrackerapi.user;

import lombok.Data;
import java.util.UUID;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDateTime;

@Data
@Entity(name = "tb_user")
public class UserModel {
    @Id
    @GeneratedValue(generator = "UUID")
    private UUID userId;

    @Column(unique = true)
    private String username;

    private String name;

    @Column(unique = true)
    private String email;

    private String password;

    private String picture;

    @CreationTimestamp
    private LocalDateTime createdAt;
}
