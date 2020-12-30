package com.example.taskmanagerproject.controller.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.taskmanagerproject.model.Task;
import com.example.taskmanagerproject.repository.IRepository;

import java.util.List;
import java.util.UUID;

@Dao
public interface TaskDatabaseDAO extends IRepository {

    @Insert
    void insertTask(Task task);

    @Delete
    void deleteTask(Task task);

    @Update
    void updateTask(Task task);

    @Query("SELECT * FROM taskTable")
    List<Task> getTasks();

    @Query("SELECT * FROM taskTable WHERE uuid = :uuid")
    Task getTask(UUID uuid);

    @Query("SELECT * FROM taskTable WHERE position = :position & userCreatorId = :userId")
    List<Task> getTasksWithState(int position, long userId);

    @Query("DELETE FROM taskTable WHERE userCreatorId = :userId")
    void deleteAll(long userId);

//    @Query("SELECT * FROM taskTable WHERE title LIKE :title" +
//            " OR discription LIKE :discription" +
//    " OR date = :date" + " OR time = :time")
//    List<Task> getTasksSearch(String title, String discription, Date date, long time);

}
