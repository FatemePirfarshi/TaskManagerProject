package com.example.taskmanagerproject.repository;

import com.example.taskmanagerproject.R;
import com.example.taskmanagerproject.model.State;
import com.example.taskmanagerproject.model.Task;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
    public List<Task> getTodoTask() {
        return arrayTaskList[0];
    }

    @Override
    public List<Task> getDoingTask() {
        return arrayTaskList[1];
    }

    @Override
    public List<Task> getDoneTask() {
        return arrayTaskList[2];
    }

    @Override
    public List<Task> getListWithPosition(int position) {
        mCurrentPosition = position;

        switch (position) {
            case 0:
                return getTodoTask();
            case 1:
                return getDoingTask();
            default:
                return getDoneTask();
        }
    }

    public int getCurrentPosition() {
        return mCurrentPosition;
    }

    public void setCurrentPosition(int currentPosition) {
        mCurrentPosition = currentPosition;
    }

    @Override
    public Task getTask(UUID id) {
        for (int i = 0; i < 3; i++) {
            for (Task t :getListWithPosition(i)) {
                if (t.getId().equals(id)) {
                    mCurrentPosition = i;
                    return t;
                }
            }
        }
        return null;
    }

    @Override
    public void insertTask(Task task, int position) {
        arrayTaskList[position].add(task);
    }

    @Override
    public void deleteTask(UUID taskId) {

        Task task = getTask(taskId);
        getListWithPosition(mCurrentPosition).remove(task);
//        for (Task t: getListWithPosition(mCurrentPosition)) {
//            if(t.getId().equals(taskId))
//                getListWithPosition(mCurrentPosition).remove(t);
//        }
    }

    @Override
    public void changeState(Task task, State state) {

    }

    @Override
    public void editTask(Task task) {

    }

    @Override
    public void updateTask(Task task) {
//        Task addedTask = getTask(id);
//        Task findTask = getTask(task.getId());
//        findTask.setTitle(task.getTitle());
//        findTask.setDiscription(task.getDiscription());
//        findTask.setDone(task.isDone());
//       findTask.setDate(task.getDate());
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
