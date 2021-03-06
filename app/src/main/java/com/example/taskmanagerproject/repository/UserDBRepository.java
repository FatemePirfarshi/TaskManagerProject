package com.example.taskmanagerproject.repository;

import android.content.Context;

import androidx.room.Room;

import com.example.taskmanagerproject.controller.database.TaskDatabase;
import com.example.taskmanagerproject.controller.database.UserDatabaseDAO;
import com.example.taskmanagerproject.model.Task;
import com.example.taskmanagerproject.model.User;
import com.example.taskmanagerproject.model.UserWithTasks;

import java.util.List;
import java.util.UUID;

public class UserDBRepository implements UserDatabaseDAO {

    private static UserDBRepository sInstance;
    private UserDatabaseDAO mUserDAO;
    private Context mContext;

    public static UserDBRepository getInstance(Context context) {
        if (sInstance == null)
            sInstance = new UserDBRepository(context);
        return sInstance;
    }

    private UserDBRepository(Context context) {
        mContext = context.getApplicationContext();

        TaskDatabase taskDatabase =
                Room.databaseBuilder(mContext, TaskDatabase.class, "task.db")
                        .allowMainThreadQueries()
                        .build();

        mUserDAO = taskDatabase.getUserDAO();
    }

    @Override
    public List<User> getUsers() {
        return mUserDAO.getUsers();
    }

    @Override
    public User getUser(String userName, String passWord) {
        return mUserDAO.getUser(userName, passWord);
    }

    @Override
    public User getUser(UUID uuid) {
        return mUserDAO.getUser(uuid);
    }

    @Override
    public List<Task> getUserTasks(long userId) {
        return mUserDAO.getUserTasks(userId);
    }

    @Override
    public List<UserWithTasks> getUsersWithTasks() {
        return mUserDAO.getUsersWithTasks();
    }

//    @Override
//    public List<UserWithTasks> getUsersWithTasks() {
//        return mUserDAO.getUsersWithTasks();
//    }

    @Override
    public void insertUser(User user) {
        mUserDAO.insertUser(user);
    }

    @Override
    public void updateUser(User user) {
        mUserDAO.updateUser(user);
    }

    @Override
    public void deleteUser(User user) {
        mUserDAO.deleteUser(user);
    }

    public int[] setUserTaskNumber(){
        List<UserWithTasks> userTasksList = mUserDAO.getUsersWithTasks();
        int[] result = new int[userTasksList.size()];
        for (int i = 0; i < userTasksList.size(); i++) {
           result[i] = userTasksList.get(i).tasks.size();
        }
        return result;
    }
}
