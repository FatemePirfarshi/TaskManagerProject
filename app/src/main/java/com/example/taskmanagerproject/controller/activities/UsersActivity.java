package com.example.taskmanagerproject.controller.activities;

import android.content.Context;
import android.content.Intent;

import androidx.fragment.app.Fragment;

import com.example.taskmanagerproject.controller.fragments.UserListFragment;

public class UsersActivity extends SingleFragmentActivity {

    public static void start(Context context){
        Intent starter = new Intent(context, UsersActivity.class);
        context.startActivity(starter);
    }

    @Override
    public Fragment createFragment() {
        return UserListFragment.newInstance();
    }
}