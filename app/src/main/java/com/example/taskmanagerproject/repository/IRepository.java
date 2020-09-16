package com.example.taskmanagerproject.repository;

import android.widget.ImageView;

import com.example.taskmanagerproject.model.State;
import com.example.taskmanagerproject.model.Task;

import java.util.List;

public interface IRepository {
    List<Task> getTodoTAsk();
    List<Task> getDoingTAsk();
    List<Task> getDoneTAsk();
    List<Task> getListWithPosition(int position);
    int checkImageState(int position);

    void insertTask(Task task, int position);
    void deleteTask(Task task);
    void changeState(Task task,State state);
    void editTask(Task task);
}
