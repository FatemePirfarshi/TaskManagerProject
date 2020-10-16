package com.example.taskmanagerproject.controller.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.example.taskmanagerproject.model.Task;
import com.example.taskmanagerproject.model.User;
import com.example.taskmanagerproject.model.UserWithTasks;
import com.example.taskmanagerproject.repository.UserIRepository;

import java.util.List;
import java.util.UUID;

@Dao
public interface UserDatabaseDAO extends UserIRepository {

    @Insert
    void insertUser(User user);

    @Update
    void updateUser(User user);

    @Delete
    void deleteUser(User user);

    @Query("SELECT * FROM userTable")
    List<User> getUsers();

    @Query("SELECT * FROM userTable WHERE userName LIKE :userName AND passWord LIKE :passWord")
    User getUser(String userName, String passWord);

    @Query("SELECT * FROM userTable WHERE uuid = :uuid")
    User getUser(UUID uuid);

    @Query("SELECT * FROM taskTable WHERE userCreatorId = :userId")
    List<Task> getUserTasks(long userId);

    @Transaction
    @Query("SELECT * FROM userTable")
    List<UserWithTasks> getUsersWithTasks();

}