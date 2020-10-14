package com.example.taskmanagerproject.repository;

import android.content.Context;

import androidx.room.Room;

import com.example.taskmanagerproject.R;
import com.example.taskmanagerproject.controller.database.TaskDatabase;
import com.example.taskmanagerproject.controller.database.TaskDatabaseDAO;
import com.example.taskmanagerproject.model.State;
import com.example.taskmanagerproject.model.Task;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TaskDBRepository implements TaskDatabaseDAO {

    private static TaskDBRepository sInstance;
    private TaskDatabaseDAO mTaskDAO;
    private Context mContext;

    public static TaskDBRepository getInstance(Context context, int position) {
        if (sInstance == null)
            sInstance = new TaskDBRepository(context, position);

        return sInstance;
    }

    private TaskDBRepository(Context context, int position) {
        mContext = context.getApplicationContext();

        TaskDatabase taskDatabase = Room.databaseBuilder(
                mContext, TaskDatabase.class, "task.db").allowMainThreadQueries().build();

        mTaskDAO = taskDatabase.getTaskDAO();
        mTaskListMain = mTaskDAO.getTasks();
        mCurrentPosition = position;
    }

    private List<Task> mTaskListMain;
    private List<Task> mTodoTasks = new ArrayList<>();
    private List<Task> mDoingTasks = new ArrayList<>();
    private List<Task> mDoneTasks = new ArrayList<>();

    private int mCurrentPosition;

    private State mState;

    @Override
    public List<Task> getTaskStates(int position) {
        return mTaskDAO.getTaskStates(position);
    }

    public List<Task> getListWithPosition(int position) {
        mCurrentPosition = position;

        switch (position) {
            case 0:
                mState = State.TODO;
                return getTaskStates(0);
            case 1:
                mState = State.DOING;
                return getTaskStates(1);
            default:
                mState = State.DONE;
                return getTaskStates(2);
        }
    }

    @Override
    public List<Task> getTasks() {
        return mTaskDAO.getTasks();
    }

    @Override
    public Task getTask(UUID uuid) {
        return mTaskDAO.getTask(uuid);
    }

    @Override
    public void insertTask(Task task) {
        mTaskDAO.insertTask(task);
    }

    @Override
    public void deleteTask(Task task) {
        mTaskDAO.deleteTask(task);
    }

    @Override
    public void updateTask(Task task) {
        mTaskDAO.updateTask(task);
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

    public void updateLists(Task newTask) {
        switch (newTask.getPosition()) {
            case 0:
                mTodoTasks.add(mTaskDAO.getTask(newTask.getId()));
                break;
            case 1:
                mDoingTasks.add(mTaskDAO.getTask(newTask.getId()));
                break;
            case 2:
                mDoneTasks.add(mTaskDAO.getTask(newTask.getId()));
                break;
        }
    }

    public void deleteAll(){
        for (int i = 0; i < mTaskListMain.size(); i++) {
            mTaskDAO.deleteTask(mTaskListMain.get(i));
        }
    }
}
