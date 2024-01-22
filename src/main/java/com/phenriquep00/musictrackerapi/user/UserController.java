package com.phenriquep00.musictrackerapi.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import at.favre.lib.crypto.bcrypt.BCrypt;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/user")
public class UserController {

    @Autowired
    private IUserRepository userRepository;

    @PostMapping("/")
    public ResponseEntity<Object> create(@RequestBody UserModel userModel) {
        if (this.userRepository.findByUsername(userModel.getUsername()) != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Username already exists");
        }

        if (this.userRepository.findByEmail(userModel.getEmail()) != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email already exists");
        }

        try {
            userModel.setPassword(BCrypt.withDefaults().hashToString(12, userModel.getPassword().toCharArray()));
            UserModel createdUser = this.userRepository.save(userModel);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error creating user");
        }
}


    @GetMapping("/{username}")
    public ResponseEntity<Object> getById(@PathVariable String username)
    {
        System.out.println(username);
        UserModel userModel = this.userRepository.findByUsername(username);

        if(userModel == null)
        {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

        return ResponseEntity.status(HttpStatus.OK).body(userModel);
    }

    @GetMapping("/auth")
    public ResponseEntity<Object> authenticate(@RequestParam String username, @RequestParam String password) {
    UserModel userModel = this.userRepository.findByUsername(username);

    if (userModel == null) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
    }

    // Verify the provided password against the stored hashed password
    if (BCrypt.verifyer().verify(password.toCharArray(), userModel.getPassword()).verified) {
        // Password is correct
        return ResponseEntity.status(HttpStatus.OK).body(userModel);
    } else {
        // Password is incorrect
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Incorrect password");
    }
}}
