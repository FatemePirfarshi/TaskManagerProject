package com.example.taskmanagerproject.repository;

import com.example.taskmanagerproject.model.State;
import com.example.taskmanagerproject.model.Task;

import java.util.ArrayList;
import java.util.List;

public class TaskRepository implements IRepository {

    public static TaskRepository sInstance;

    private List<Task> mTodoTasks;
    private List<Task> mDoingTasks;
    private List<Task> mDoneTasks;
//    private int imageRes;


    public void setTodoTasks(List<Task> todoTasks) {
        mTodoTasks = todoTasks;
    }

    public void setDoingTask(List<Task> doingTask) {
        mDoingTasks = doingTask;
    }

    public void setDoneTask(List<Task> doneTask) {
        mDoneTasks = doneTask;
    }

    public static TaskRepository getINstance() {
        if (sInstance == null)
            sInstance = new TaskRepository();

        return sInstance;
    }

    private TaskRepository() {
        mTodoTasks = new ArrayList<>();
        mDoingTasks = new ArrayList<>();
        mDoneTasks = new ArrayList<>();
    }

    @Override
    public List<Task> getTodoTAsk() {
       // imageRes = R.drawable.ic_todo;
        return mTodoTasks;
    }

    @Override
    public List<Task> getDoingTAsk() {
       // imageRes = R.drawable.ic_doing;
        return mDoingTasks;
    }

    @Override
    public List<Task> getDoneTAsk() {
        //imageRes = R.drawable.ic_done;
        return mDoneTasks;
    }

    @Override
    public void insertTask(Task task) {

    }

    @Override
    public void deleteTask(Task task) {

    }

    @Override
    public void changeState(Task task, State state) {

    }

    @Override
    public void editTask(Task task) {

    }
}
