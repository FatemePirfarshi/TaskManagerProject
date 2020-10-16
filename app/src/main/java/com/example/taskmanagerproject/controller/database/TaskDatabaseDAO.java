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

    @Query("SELECT * FROM taskTable WHERE position = :position")
    List<Task> getTaskStates(int position);
//
//    @ForeignKey(entity = Task.class, parentColumns = "userCreatorId", childColumns = "userId")
//    @Query("SELECT * FROM taskTable WHERE userCreatorId = :userId")
//    List<Task> getUserTAsks(long userId);

//    @Query("DROP TABLE taskTable")
//    List<Task> getTasks();
//    @Transaction
//    @Query("SELECT * FROM taskTable"+
    //     " JOIN userTable ON userCreatorId = :userId" +
//
//            " WHERE userCreatorId = :userId")
//    List<Task> userTasks(long userId);

//    @Transaction
//    @Query("SELECT * FROM userTable")
//    List<UserWithTasks> userTasks();
//
//    @Query("SELECT * FROM taskTable WHERE title LIKE :title" +
//            " OR discription LIKE :discription" +
//    " OR date = :date" + " time = :time")
//    List<Task> getTaskWithTitle(String title, String discription, Date date, long time);
//
//    @Query("SELECT * FROM taskTable WHERE discription = :discription")
//    List<Task> getTaskWithDiscription(String discription);
//
//    @Query("SELECT * FROM taskTable WHERE date = :date")
//    List<Task> getTaskWithDate(Date date);
//
//    @Query("SELECT * FROM taskTable WHERE time = :time")
//    List<Task> getTaskWithTitle(long time);
}
