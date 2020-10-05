package com.example.taskmanagerproject.controller.activities;

import android.content.Context;
import android.content.Intent;

import androidx.fragment.app.Fragment;

import com.example.taskmanagerproject.controller.fragments.LoginFragment;

public class LoginActivity extends SingleFragmentActivity {

    public static final String USER_NAME_FROM_SIGNUP = "userNameFromSignup";
    public static final String PASS_WORD_FROM_SIGNUP = "passWordFromSignup";

    public static void start(Context context, String userName, String passWord) {
        Intent starter = new Intent(context,LoginActivity.class);
        starter.putExtra(USER_NAME_FROM_SIGNUP, userName);
        starter.putExtra(PASS_WORD_FROM_SIGNUP, passWord);
        context.startActivity(starter);
    }

    @Override
    public Fragment createFragment() {
        return LoginFragment.newInstance(
                getIntent().getStringExtra(USER_NAME_FROM_SIGNUP),
                getIntent().getStringExtra(PASS_WORD_FROM_SIGNUP)
        );
    }

}