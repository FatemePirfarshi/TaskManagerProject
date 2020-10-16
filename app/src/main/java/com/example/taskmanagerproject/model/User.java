package com.example.taskmanagerproject.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.util.Date;
import java.util.UUID;

@Entity(tableName = "userTable")
public class User {

    @ForeignKey(entity = User.class, parentColumns = "userId", childColumns = "userCreatorId")
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "userId")
    private long userId;

    @ColumnInfo(name = "uuid")
    private UUID mId;

    @ColumnInfo(name = "userName")
    private String mUserName;

    @ColumnInfo(name = "passWord")
    private String mPassWord;

    @ColumnInfo(name = "date")
    private Date mDate;

  //   @ForeignKey(entity = User.class, parentColumns = "taskId", childColumns = "userCreatorId")
    @ColumnInfo(name = "taskId")
    private long taskId;

    public User() {
        mId = mId.randomUUID();
        mDate = new Date();
    }

    public long getTaskId() {
        return taskId;
    }

    public void setTaskId(long taskId) {
        this.taskId = taskId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public UUID getId() {
        return mId;
    }

    public void setId(UUID id) {
        mId = id;
    }

    public String getUserName() {
        return mUserName;
    }

    public void setUserName(String userName) {
        mUserName = userName;
    }

    public String getPassWord() {
        return mPassWord;
    }

    public void setPassWord(String passWord) {
        mPassWord = passWord;
    }

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date date) {
        mDate = date;
    }
}
