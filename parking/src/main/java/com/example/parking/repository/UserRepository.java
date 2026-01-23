package com.example.parking.repository;

import com.example.parking.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User,String> {
    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);
}
