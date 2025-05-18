package com.example.pafbackend.controllers;

import com.example.pafbackend.models.UserConnection;
import com.example.pafbackend.repositories.UserConnectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/userConnections")
public class UserConnectionController {

    private final UserConnectionRepository userConnectionRepository;

    // Constructor-based dependency injection
    @Autowired
    public UserConnectionController(UserConnectionRepository userConnectionRepository) {
        this.userConnectionRepository = userConnectionRepository;
    }

    /**
     * GET endpoint to fetch user connection data by userId
     * @param userId the ID of the user
     * @return UserConnection data if found, otherwise 404 NOT FOUND
     */
    @GetMapping("/{userId}")
    public ResponseEntity<UserConnection> getUserConnections(@PathVariable String userId) {
        UserConnection userConnection = userConnectionRepository.findByUserId(userId);
        if (userConnection != null) {
            return new ResponseEntity<>(userConnection, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * POST endpoint to create or update a user's connections
     * - If the user already has a connection record, append the new friendIds
     * - If not, create a new record with the provided userId and friendIds
     * @param userConnection the user connection data to be created or updated
     * @return ResponseEntity with the created/updated UserConnection and status code
     */
    @PostMapping
    public ResponseEntity<UserConnection> createUserConnection(@RequestBody UserConnection userConnection) {
        // Check if a document with the userId already exists
        UserConnection existingConnection = userConnectionRepository.findByUserId(userConnection.getUserId());
        if (existingConnection != null) {
            // Update the existing document by adding new friendIds
            List<String> currentFriendIds = existingConnection.getFriendIds();
            List<String> newFriendIds = userConnection.getFriendIds();
            currentFriendIds.addAll(newFriendIds); // Append new friend IDs
            existingConnection.setFriendIds(currentFriendIds);
            UserConnection updatedConnection = userConnectionRepository.save(existingConnection);
            return new ResponseEntity<>(updatedConnection, HttpStatus.OK);
        } else {
            // No existing document, create a new one
            UserConnection savedUserConnection = userConnectionRepository.save(userConnection);
            return new ResponseEntity<>(savedUserConnection, HttpStatus.CREATED);
        }
    }

    /**
     * DELETE endpoint to remove a friend connection from a user's friend list
     * @param userId the user ID
     * @param friendId the friend ID to be removed
     * @return 204 NO CONTENT if successful, 404 NOT FOUND if user not found
     */
    @DeleteMapping("/{userId}/friends/{friendId}")
    public ResponseEntity<Void> unfriend(@PathVariable String userId, @PathVariable String friendId) {
        // Check if a document with the userId exists
        UserConnection existingConnection = userConnectionRepository.findByUserId(userId);
        if (existingConnection != null) {
            // Remove the friendId from the list of friendIds
            List<String> currentFriendIds = existingConnection.getFriendIds();
            currentFriendIds.remove(friendId); // Remove the specified friend
            existingConnection.setFriendIds(currentFriendIds);
            userConnectionRepository.save(existingConnection); // Save the updated connection
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            // No existing document, return 404
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
