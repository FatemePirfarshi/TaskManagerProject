package com.example.taskmanagerproject.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

@Entity(tableName = "taskTable"
//        ,foreignKeys = @ForeignKey(entity = User.class,
//        parentColumns = "userId",
//        childColumns = "userCreatedId",
//        onDelete = CASCADE)
)

public class Task implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "taskId")
    private long primaryId;

    @ColumnInfo(name = "uuid")
    private UUID mId;

    @ColumnInfo(name = "discription")
    private String mDiscription;

    @ColumnInfo(name = "title")
    private String mTitle;

    @ColumnInfo(name = "date")
    private Date mDate;

    @ColumnInfo(name = "time")
    private long mTime;

    @ColumnInfo(name = "done")
    private boolean mDone;

    @ColumnInfo(name = "position")
    private int mPosition;

    @ForeignKey(entity = User.class, parentColumns = "userId", childColumns = "userCreatorId")
    @ColumnInfo(name = "userCreatorId")
    private long mUserCreatorId;

    public long getUserCreatorId() {
        return mUserCreatorId;
    }

    public void setUserCreatorId(long userCreatorId) {
        mUserCreatorId = userCreatorId;
    }

    public Task() {
        this(UUID.randomUUID());
    }

    public Task(UUID id) {
        mId = id;
        mDate = new Date();
    }

    public Task(UUID id, String discription, String title, Date date, long time, boolean done,
                int position, long userCreatorId) {
        mId = id;
        mDiscription = discription;
        mTitle = title;
        mDate = date;
        mDone = done;
        mTime = time;
        mPosition = position;
        mUserCreatorId = userCreatorId;
    }

    public long getPrimaryId() {
        return primaryId;
    }

    public void setPrimaryId(long primaryId) {
        this.primaryId = primaryId;
    }

    public int getPosition() {
        return mPosition;
    }

    public void setPosition(int position) {
        mPosition = position;
    }

    public void setId(UUID id) {
        mId = id;
    }

    public UUID getId() {
        return mId;
    }

    public String getDiscription() {
        return mDiscription;
    }

    public void setDiscription(String discription) {
        mDiscription = discription;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date date) {
        mDate = date;
    }

    public boolean isDone() {
        return mDone;
    }

    public void setDone(boolean done) {
        mDone = done;
    }

    public long getTime() {
        return mTime;
    }

    public void setTime(long time) {
        mTime = time;
    }
}
