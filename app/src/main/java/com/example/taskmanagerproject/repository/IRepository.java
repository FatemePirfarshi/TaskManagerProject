package com.example.taskmanagerproject.repository;

import com.example.taskmanagerproject.model.State;
import com.example.taskmanagerproject.model.Task;

import java.util.List;
import java.util.UUID;

public interface IRepository {
    List<Task> getTodoTask();
    List<Task> getDoingTask();
    List<Task> getDoneTask();
    List<Task> getListWithPosition(int position);
    int checkImageState(int position);

    void insertTask(Task task, int position);
    void deleteTask(UUID taskId);
    void changeState(Task task,State state);
    void editTask(Task task);
    void updateTask(Task task);
    Task getTask(UUID id);
}
