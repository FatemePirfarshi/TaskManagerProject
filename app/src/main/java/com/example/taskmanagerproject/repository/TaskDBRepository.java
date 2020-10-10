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

    public static TaskDBRepository getInstance(Context context, int position){
        if(sInstance == null)
            sInstance = new TaskDBRepository(context, position);

        return sInstance;
    }

    private TaskDBRepository(Context context, int position){
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

    public List<Task> getTodoTask() {

        for (Task t: mTaskListMain) {
            if(t.getPosition() == 0)
                mTodoTasks.add(t);
        }
        return mTodoTasks;
    }

    public List<Task> getDoingTask() {

        for (Task t: mTaskListMain) {
            if(t.getPosition() == 1)
                mTodoTasks.add(t);
        }
        return mDoingTasks;
    }

    public List<Task> getDoneTask() {
        for (Task t: mTaskListMain) {
            if(t.getPosition() == 2)
                mTodoTasks.add(t);
        }
        return mDoneTasks;
    }

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

    @Override
    public List<Task> getTasks() {
//        mTaskListMain = mTaskDAO.getTasks();
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
//    @Override
//    public void deleteTask(Task task) {
//        getListWithPosition(mCurrentPosition).remove(task);
//       // mTaskDAO.deleteTask(task);
//    }

//
//    @Override
//    public Task getTask(UUID id) {
//        for (int i = 0; i < 3; i++) {
//            for (Task t : getListWithPosition(i)) {
//                if (t.getId().equals(id)) {
//                    mCurrentPosition = i;
//                    return t;
//                }
//            }
//        }
//        return null;
//    }

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

    public int getCurrentPosition() {
        return mCurrentPosition;
    }

    public void updateLists(Task newTask){
        switch (newTask.getPosition()){
            case 0:
                mTodoTasks.add(newTask);
                break;
            case 1:
                mDoingTasks.add(newTask);
                break;
            case 2:
                mDoneTasks.add(newTask);
                break;
        }
    }


}
