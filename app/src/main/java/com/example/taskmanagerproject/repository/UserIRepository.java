package com.example.taskmanagerproject.repository;

import com.example.taskmanagerproject.model.User;

import java.util.List;
import java.util.UUID;

public interface UserIRepository {

    List<User> getUsers();
    User getUser(UUID userId);
    void insertUser(User user);
    void updateUser(User user);
    void deleteUser(User user);

}
