package com.radekgrzywacz.kafka_ws_demo.repository;

import com.radekgrzywacz.kafka_ws_demo.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {

    Optional<User> findByUsername(String username);
}
