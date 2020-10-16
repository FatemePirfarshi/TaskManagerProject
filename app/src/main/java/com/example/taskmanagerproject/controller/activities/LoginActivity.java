package com.example.taskmanagerproject.controller.activities;

import android.content.Context;
import android.content.Intent;

import androidx.fragment.app.Fragment;

import com.example.taskmanagerproject.controller.fragments.LoginFragment;

import java.util.UUID;

public class LoginActivity extends SingleFragmentActivity {

    public static final String USER_ID_SIGN_UP = "userIdSignUp";

    public static void start(Context context, UUID uuid) {
        Intent starter = new Intent(context, LoginActivity.class);
        starter.putExtra(USER_ID_SIGN_UP, uuid);
        context.startActivity(starter);
    }

    @Override
    public Fragment createFragment() {
        return LoginFragment.newInstance((UUID) getIntent().getSerializableExtra(USER_ID_SIGN_UP));
    }

}