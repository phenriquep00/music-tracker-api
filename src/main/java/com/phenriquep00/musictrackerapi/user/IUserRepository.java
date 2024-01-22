package com.phenriquep00.musictrackerapi.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface IUserRepository extends JpaRepository<UserModel, UUID>{
    UserModel findByUsername(String username);
    UserModel findByEmail(String email);
}
