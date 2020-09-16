package com.example.taskmanagerproject.model;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

public class Task{

    private UUID mId;
    private String mDiscription;
    private String mTitle;
    private Date mDate;
    private State mState;

    public UUID getId() {
        return mId;
    }

    public Task(){
        mId = mId.randomUUID();
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
}
