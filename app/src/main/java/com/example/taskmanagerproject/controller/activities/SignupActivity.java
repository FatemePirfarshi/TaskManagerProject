package com.example.taskmanagerproject.controller.activities;

import android.content.Context;
import android.content.Intent;

import androidx.fragment.app.Fragment;

import com.example.taskmanagerproject.controller.fragments.SignupFragment;

public class SignupActivity extends SingleFragmentActivity {

    public static final String USER_NAME_FROM_LOGIN = "userNameFromLogin";
    public static final String PASS_WORD_FROM_LOGIN = "passWordFromLogin";

    public static void start(Context context, String userName, String passWord) {
        Intent starter = new Intent(context, SignupActivity.class);
        starter.putExtra(USER_NAME_FROM_LOGIN, userName);
        starter.putExtra(PASS_WORD_FROM_LOGIN, passWord);
        context.startActivity(starter);
    }

    @Override
    public Fragment createFragment() {
        return SignupFragment.newInstance(
                getIntent().getStringExtra(USER_NAME_FROM_LOGIN),
                getIntent().getStringExtra(PASS_WORD_FROM_LOGIN)
        );
    }

}