package com.example.microservices.service;

import com.example.microservices.model.User;
import com.example.microservices.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service // Marchează clasa ca un component de tip Service
public class UserService {

    private final UserRepository userRepository; // Injectăm UserRepository

    @Autowired // Injectează instanța de UserRepository în constructor
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Metodă pentru a obține toți utilizatorii
    public List<User> getAllUsers() {
        // Aici am putea adăuga logici de business suplimentare,
        // cum ar fi filtrarea, sortarea, sau verificări de permisiuni,
        // înainte de a apela repository-ul.
        System.out.println("Fetching all users from service layer.");
        return userRepository.findAll();
    }

    // Metodă pentru a obține un utilizator după ID
    public Optional<User> getUserById(String id) {
        System.out.println("Fetching user with ID: " + id + " from service layer.");
        return userRepository.findByID(id);
    }

    // Metodă pentru a crea un utilizator nou
    public User createUser(User user) {
        // Aici am putea adăuga validări complexe (ex: email unic, format corect),
        // sau logici de business (ex: trimitere email de bun venit).
        System.out.println("Creating user: " + user.getEmail() + " in service layer.");
        return userRepository.save(user);
    }

    // Metodă pentru a actualiza un utilizator existent
    public Optional<User> updateUser(String id, User userDetails) {
        System.out.println("Updating user with ID: " + id + " in service layer.");
        return userRepository.findByID(id)
                .map(existingUser -> {
                    // Actualizăm doar câmpurile relevante
                    existingUser.setName(userDetails.getName());
                    existingUser.setEmail(userDetails.getEmail());
                    // Aici am putea adăuga logici suplimentare înainte de salvare
                    return userRepository.save(existingUser);
                });
    }

    // Metodă pentru a șterge un utilizator
    public boolean deleteUser(String id) {
        System.out.println("Deleting user with ID: " + id + " from service layer.");
        return userRepository.deleteById(id);
    }
}