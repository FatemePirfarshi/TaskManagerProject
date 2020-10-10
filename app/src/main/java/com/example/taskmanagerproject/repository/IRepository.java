package com.example.taskmanagerproject.repository;

import com.example.taskmanagerproject.model.Task;

import java.util.List;
import java.util.UUID;

public interface IRepository {
//    List<Task> getTodoTask();
//    List<Task> getDoingTask();
//    List<Task> getDoneTask();
//  void changeState(Task task,State state);
//List<Task> getTaskList();
   // List<Task> getListWithPosition(int position);

    List<Task> getTasks();
    Task getTask(UUID id);
    void insertTask(Task task);
    void deleteTask(Task task);
    void updateTask(Task task);
}
