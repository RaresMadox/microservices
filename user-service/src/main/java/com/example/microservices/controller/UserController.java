package com.example.microservices.controller;


import com.example.microservices.model.User;
import com.example.microservices.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController // Marchează clasa ca un Controller REST
@RequestMapping("/users") // Definește calea de bază pentru toate endpoint-urile din acest controller
public class UserController {

    private final UserRepository userRepository; // Injectăm UserRepository

    @Autowired // Injectează instanța de UserRepository
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Endpoint pentru a verifica dacă serviciul rulează (opțional, dar util)
    @GetMapping("/status") // Ex: GET http://localhost:3001/users/status
    public String getStatus() {
        return "User Service is running!";
    }

    // Endpoint pentru a obține toți utilizatorii
    @GetMapping // Ex: GET http://localhost:3001/users
    public ResponseEntity<List<User>> getAllUsers() {
        System.out.println("GET /users request received");
        List<User> users = userRepository.findAll();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    // Endpoint pentru a obține un utilizator după ID
    @GetMapping("/{id}") // Ex: GET http://localhost:3001/users/1
    public ResponseEntity<User> getUserById(@PathVariable String id) {
        System.out.println("GET /users/" + id + " request received");
        return userRepository.findByID(id)
                .map(user -> new ResponseEntity<>(user, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Endpoint pentru a crea un utilizator nou
    @PostMapping // Ex: POST http://localhost:3001/users
    public ResponseEntity<User> createUser(@RequestBody User user) {
        System.out.println("POST /users request received. New user data: " + user);
        User savedUser = userRepository.save(user);
        return new ResponseEntity<>(savedUser, HttpStatus.CREATED); // 201 Created
    }

    // Endpoint pentru a actualiza un utilizator existent
    @PutMapping("/{id}") // Ex: PUT http://localhost:3001/users/1
    public ResponseEntity<User> updateUser(@PathVariable String id, @RequestBody User user) {
        System.out.println("PUT /users/" + id + " request received. Updated user data: " + user);
        // Asigurăm că ID-ul din path este folosit pentru actualizare
        user.setId(id);
        if (userRepository.findByID(id).isPresent()) {
            User updatedUser = userRepository.save(user);
            return new ResponseEntity<>(updatedUser, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Endpoint pentru a șterge un utilizator
    @DeleteMapping("/{id}") // Ex: DELETE http://localhost:3001/users/1
    public ResponseEntity<Void> deleteUser(@PathVariable String id) {
        System.out.println("DELETE /users/" + id + " request received");
        if (userRepository.deleteById(id)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); // 204 No Content
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}