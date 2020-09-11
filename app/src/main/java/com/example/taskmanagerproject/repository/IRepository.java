package com.example.taskmanagerproject.repository;

import com.example.taskmanagerproject.model.State;
import com.example.taskmanagerproject.model.Task;

import java.util.List;

public interface IRepository {
    List<Task> getTodoTAsk();
    List<Task> getDoingTAsk();
    List<Task> getDoneTAsk();

    void insertTask(Task task);
    void deleteTask(Task task);
    void changeState(Task task,State state);
    void editTask(Task task);
}
