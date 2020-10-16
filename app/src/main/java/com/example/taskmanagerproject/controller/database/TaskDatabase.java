package com.example.taskmanagerproject.controller.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.taskmanagerproject.model.Task;
import com.example.taskmanagerproject.model.User;

@Database(entities = {Task.class, User.class}, version = 1)
@TypeConverters({Converters.class})
public abstract class TaskDatabase extends RoomDatabase {

    public abstract TaskDatabaseDAO getTaskDAO();

    public abstract UserDatabaseDAO getUserDAO();

}
