package com.example.taskmanagerproject.repository;

import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.example.taskmanagerproject.R;
import com.example.taskmanagerproject.model.State;
import com.example.taskmanagerproject.model.Task;

import java.util.ArrayList;
import java.util.List;

public class TaskRepository implements IRepository {

    public static TaskRepository sInstance;

    private List<Task> mTodoTasks;
    private List<Task> mDoingTasks;
    private List<Task> mDoneTasks;

    private int mCurrentPosition;

    private int mImageRes;

    public int getImageRes() {
        return mImageRes;
    }

    public void setImageRes(int imageRes) {
        mImageRes = imageRes;
    }

    private List[] arrayTaskList = {mTodoTasks, mDoingTasks, mDoneTasks};

    public void setTodoTasks(List<Task> todoTasks) {
        mTodoTasks = todoTasks;
    }

    public void setDoingTask(List<Task> doingTask) {
        mDoingTasks = doingTask;
    }

    public void setDoneTask(List<Task> doneTask) {
        mDoneTasks = doneTask;
    }

    public static TaskRepository getInstance(int position) {
        if (sInstance == null)
            sInstance = new TaskRepository(position);

        return sInstance;
    }

    private TaskRepository(int position) {
        for (int i = 0; i < arrayTaskList.length; i++) {
            arrayTaskList[i] = new ArrayList<>();
        }
        mCurrentPosition = position;
    }

    @Override
    public List<Task> getTodoTAsk() {
        return arrayTaskList[0];
    }

    @Override
    public List<Task> getDoingTAsk() {
        return arrayTaskList[1];
    }

    @Override
    public List<Task> getDoneTAsk() {
        return arrayTaskList[2];
    }

    @Override
    public List<Task> getListWithPosition(int position) {
        switch (position) {
            case 0:
                return arrayTaskList[0];
            case 1:
                return arrayTaskList[1];
            default:
                return arrayTaskList[2];
        }
    }

    @Override
    public void insertTask(Task task, int position) {
        arrayTaskList[position].add(task);
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

    @Override
    public int checkImageState(int position) {
        switch (position) {
            case 0:
                return R.drawable.ic_todo;
            case 1:
                return R.drawable.ic_doing;
            default:
                return R.drawable.ic_done;
        }
    }
}
