package com.radekgrzywacz.kafka_ws_demo.service;

import com.radekgrzywacz.kafka_ws_demo.dto.CreateUserDto;
import com.radekgrzywacz.kafka_ws_demo.entity.User;
import com.radekgrzywacz.kafka_ws_demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public ResponseEntity<User> save(CreateUserDto userDto) {
        Optional<User> optionalUser = userRepository.findByUsername(userDto.getUsername());

        // Check if user is present
        if (optionalUser.isPresent()) {
            // User already exists, return bad request
            return ResponseEntity.badRequest().body(null);
        } else {
            // User does not exist, create and save new user
            User user = new User(userDto.getUsername());
            userRepository.save(user);
            return ResponseEntity.ok(user); // Return the newly created user
        }
    }
}
