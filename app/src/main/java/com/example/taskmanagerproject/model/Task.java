package com.example.taskmanagerproject.model;

import java.util.Date;
import java.util.UUID;

public class Task{

    private UUID mId;
    private String mDiscription;
    private String mTitle;
    private Date mDate;
    private long mTime;
    private State mState;
    private boolean mDone;

    public UUID getId() {
        return mId;
    }

    public Task(){
        mId = mId.randomUUID();
        mDate = new Date();
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

    public State getState() {
        return mState;
    }

    public void setState(State state) {
        mState = state;
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
