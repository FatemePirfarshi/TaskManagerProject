package com.example.taskmanagerproject.model;

import java.util.Date;
import java.util.UUID;

public class User {

    private UUID mId;
    private String mUserName;
    private String mPassWord;
    private Date mDate;

    public User(){
        mId = mId.randomUUID();
        mDate = new Date();
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
}
