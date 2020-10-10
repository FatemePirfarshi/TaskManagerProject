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

    private State mState;
    private List[] arrayTaskList = {mTodoTasks, mDoingTasks, mDoneTasks};

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

    public List<Task> getTodoTask() {
        return arrayTaskList[0];
    }

    public List<Task> getDoingTask() {
        return arrayTaskList[1];
    }

    public List<Task> getDoneTask() {
        return arrayTaskList[2];
    }

    //    public List<Task> getTaskList(){
//       return getListWithPosition(mCurrentPosition);
//    }
    //@Override
    public List<Task> getListWithPosition(int position) {
        mCurrentPosition = position;

        switch (position) {
            case 0:
                mState = State.TODO;
                return getTodoTask();
            case 1:
                mState = State.DOING;
                return getDoingTask();
            default:
                mState = State.DONE;
                return getDoneTask();
        }
    }

    public int getCurrentPosition() {
        return mCurrentPosition;
    }

    @Override
    public List<Task> getTasks() {
        return null;
    }

    @Override
    public Task getTask(UUID id) {
        for (int i = 0; i < 3; i++) {
            for (Task t : getListWithPosition(i)) {
                if (t.getId().equals(id)) {
                    mCurrentPosition = i;
                    return t;
                }
            }
        }
        return null;
    }

    @Override
    public void insertTask(Task task) {
        arrayTaskList[mCurrentPosition].add(task);
    }

    @Override
    public void deleteTask(Task task) {
        //Task task = getTask(task.getId());
        getListWithPosition(mCurrentPosition).remove(task);
    }

    public void changeState(Task task, State state) {

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
