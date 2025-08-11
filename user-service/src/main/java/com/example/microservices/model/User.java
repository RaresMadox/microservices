package com.example.microservices.model;


import jakarta.persistence.*;
import lombok.*;


@Table(name = "users")
@NoArgsConstructor // Generates a no-argument constructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;
    private String name;
    private String email;


    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

}
