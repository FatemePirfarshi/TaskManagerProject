package com.example.taskmanagerproject.repository;

import android.content.Context;

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

    private static TaskDBRepository sInstance;
    private TaskDatabaseDAO mTaskDAO;
    private UserDatabaseDAO mUserDAO;
    private Context mContext;
    private UUID mUserID;

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

        mTaskListMain = mTaskDAO.getTasks();
        mCurrentPosition = position;
    }

    private List<Task> mTaskListMain;
    private List<Task> mTodoTasks = new ArrayList<>();
    private List<Task> mDoingTasks = new ArrayList<>();
    private List<Task> mDoneTasks = new ArrayList<>();
    List<Task> tasks = new ArrayList<>();

    private int mCurrentPosition;

    private State mState;

    @Override
    public List<Task> getTaskStates(int position) {
        return mTaskDAO.getTaskStates(position);
    }

    public void setLists(UUID userId) {
        mUserID = userId;
        mTodoTasks.clear();
        mDoingTasks.clear();
        mDoneTasks.clear();

        List<UserWithTasks> userTasksList = mUserDAO.getUsersWithTasks();
        for (int i = 0; i < userTasksList.size(); i++) {
            if (userTasksList.get(i).user.getId().equals(userId))
                tasks = userTasksList.get(i).tasks;
//            userTasksList.get(i).user.setTaskNumber(userTasksList.get(i).tasks.size());
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
        switch (position) {
            case 0:
                mState = State.TODO;
                return mTodoTasks;
            //    return mTaskDAO.getTaskStates(0);
            case 1:
                mState = State.DOING;
                return mDoingTasks;
            default:
                mState = State.DONE;
                return mDoneTasks;
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
            case 2:
                return R.drawable.ic_done;
            default:
                return R.drawable.ic_search_list;
        }
    }

    public void updateList(){
        List<UserWithTasks> userTasksList = mUserDAO.getUsersWithTasks();
        for (int i = 0; i < userTasksList.size(); i++) {
            if (userTasksList.get(i).user.getId().equals(mUserID))
                tasks = userTasksList.get(i).tasks;
        }

        mTodoTasks.clear();
        mDoingTasks.clear();
        mDoneTasks.clear();
        for (int i = 0; i < tasks.size(); i++) {
            switch (tasks.get(i).getPosition()){
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

    public void deleteAll() {
        for (int i = 0; i < mTaskListMain.size(); i++) {
            mTaskDAO.deleteTask(mTaskListMain.get(i));
        }
    }

    public File getPhotoFile(Task task){
        File filesDir = mContext.getFilesDir();
        File photoFile = new File(filesDir, task.getPhotoFileName());
        return photoFile;
    }
}
