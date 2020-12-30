package com.example.taskmanagerproject.repository;

import android.content.Context;
import android.util.Log;

import androidx.room.Room;

import com.example.taskmanagerproject.R;
import com.example.taskmanagerproject.controller.database.TaskDatabase;
import com.example.taskmanagerproject.controller.database.TaskDatabaseDAO;
import com.example.taskmanagerproject.controller.database.UserDatabaseDAO;
import com.example.taskmanagerproject.model.State;
import com.example.taskmanagerproject.model.Task;
import com.example.taskmanagerproject.model.UserWithTasks;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class TaskDBRepository implements TaskDatabaseDAO {

    public static final String TAG = "TaskDBRepository";
    private static TaskDBRepository sInstance;
    private TaskDatabaseDAO mTaskDAO;
    private UserDatabaseDAO mUserDAO;
    private Context mContext;
    private long mUserID;

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
        mUserDAO = taskDatabase.getUserDAO();

        mCurrentPosition = position;
    }

    private List<Task> mTodoTasks = new ArrayList<>();
    private List<Task> mDoingTasks = new ArrayList<>();
    private List<Task> mDoneTasks = new ArrayList<>();
    List<Task> tasks;

    private int mCurrentPosition;

    public void setLists(long userId) {
        tasks = new ArrayList<>();
        mUserID = userId;
        mTodoTasks.clear();
        mDoingTasks.clear();
        mDoneTasks.clear();

        for (int i = 0; i < getTasks().size(); i++) {
            if (getTasks().get(i).getUserCreatorId() == mUserID) {
                tasks.add(getTasks().get(i));
//                Log.d("TaskDBRepository", "user creator id: " +
//                        getTasks().get(i).getUserCreatorId());
//                Log.d("TaskDBRepository", "user id: " + mUserID);
            }
        }

        for (int i = 0; i < tasks.size(); i++) {
            switch (tasks.get(i).getPosition()) {
                case 0:
                    mTodoTasks.add(tasks.get(i));
                    break;
                case 1:
                    mDoingTasks.add(tasks.get(i));
                    break;
                case 2:
                    mDoneTasks.add(tasks.get(i));
                    break;
            }
        }
    }

    public List<Task> getListWithPosition(int position) {
        mCurrentPosition = position;
//        Log.d(TAG, "tasks size: " + getTasks().size());
        switch (position) {
            case 0:
                return mTodoTasks;
               // Log.d(TAG, "tasks size: " + getTasksWithState(0, mUserID).size());
//                return getTasksWithState(0, mUserID);
            case 1:
                return mDoingTasks;
//                return getTasksWithState(1, mUserID);
            default:
                return mDoneTasks;
//                return getTasksWithState(2, mUserID);
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
    public List<Task> getTasksWithState(int position, long userId) {
        return mTaskDAO.getTasksWithState(position, userId);
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
            case 2:
                return R.drawable.ic_done;
            default:
                return R.drawable.ic_search_list;
        }
    }

    public void updateList() {

        tasks = new ArrayList<>();
        mTodoTasks.clear();
        mDoingTasks.clear();
        mDoneTasks.clear();
        for (int i = 0; i < getTasks().size(); i++) {
            if (getTasks().get(i).getUserCreatorId() == mUserID) {
                tasks.add(getTasks().get(i));
                Log.d(TAG,"position: " + getTasks().get(i).getPosition());
            }
        }

        for (int i = 0; i < tasks.size(); i++) {
            switch (tasks.get(i).getPosition()) {
                case 0:
                    mTodoTasks.add(tasks.get(i));
                    break;
                case 1:
                    mDoingTasks.add(tasks.get(i));
                    break;
                case 2:
                    mDoneTasks.add(tasks.get(i));
                    break;
            }
        }
    }

    public void updateList(Task task){
        switch (task.getPosition()){
            case 0:
                mTodoTasks.add(task);
                break;
            case 1:
                mDoingTasks.add(task);
                break;
            case 2:
                mDoneTasks.add(task);
                break;
        }
    }

    @Override
    public void deleteAll(long userID) {
        mTaskDAO.deleteAll(mUserID);
    }

    public File getPhotoFile(Task task) {
        File filesDir = mContext.getFilesDir();
        File photoFile = new File(filesDir, task.getPhotoFileName());
        return photoFile;
    }
}
