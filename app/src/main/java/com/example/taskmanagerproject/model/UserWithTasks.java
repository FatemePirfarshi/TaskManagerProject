package com.example.taskmanagerproject.model;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class UserWithTasks {

    @Embedded
    public User user;

    @Relation(
            parentColumn = "userId",
            entityColumn = "userCreatorId"
    )
    public List<Task> tasks;
}
