package com.example.microservices.repository;
import com.example.microservices.model.User;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class UserRepository {

    private final List<User> users = new ArrayList<>();
    private final AtomicLong counter = new AtomicLong();

    public UserRepository() {
        users.add(new User(String.valueOf(counter.incrementAndGet()), "Alice Smith", "alice@example.com"));
        users.add(new User(String.valueOf(counter.incrementAndGet()), "Bob Johnson", "bob@example.com"));
    }

    public List<User> findAll()
    {
        return new ArrayList<>(users);
    }

    public Optional<User> findByID(String id)
    {
        return users.stream().filter(user -> user.getId().equals(id)).findFirst();
    }


    public User save(User user) {

        if(user.getId() == null || user.getId().isEmpty())
        {
            user.setId(String.valueOf(counter.incrementAndGet()));
            users.add(user);
            System.out.println("User created: " + user);
        } else {
            // Dacă utilizatorul are ID, încearcă să-l actualizezi
            boolean updated = false;
            for (int i = 0; i < users.size(); i++) {
                if (users.get(i).getId().equals(user.getId())) {
                    users.set(i, user); // Actualizează utilizatorul existent
                    updated = true;
                    System.out.println("User updated: " + user);
                    break;
                }
            }
            if (!updated) {
                // Dacă ID-ul există, dar nu a fost găsit (caz puțin probabil cu logica de mai sus),
                // îl adăugăm ca nou, deși în scenarii reale ar fi o eroare sau un upsert.
                users.add(user);
                System.out.println("User added (but ID existed and not found for update): " + user);
            }
        }

        return user;

    }

    public boolean deleteById(String id) {
        boolean removed = users.removeIf(user -> user.getId().equals(id));
        if (removed) {
            System.out.println("User with ID " + id + " deleted.");
        } else {
            System.out.println("User with ID " + id + " not found for deletion.");
        }
        return removed;
    }


}
