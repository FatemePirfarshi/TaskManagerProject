package com.example.taskmanagerproject.controller.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;

import com.example.taskmanagerproject.R;
import com.example.taskmanagerproject.controller.activities.SignupActivity;
import com.example.taskmanagerproject.controller.activities.TaskPagerActivity;
import com.google.android.material.textfield.TextInputLayout;

public class LoginFragment extends Fragment {

    public static final String ARGS_SIGNUP_USER_NAME = "signupUserName";
    public static final String ARGS_SIGNUP_PASS_WORD = "signupPassWord";
    private TextInputLayout mEditTextUserName;
    private TextInputLayout mEditTextPassword;
    private Button mButtonLogIn;
    private Button mButtonSignUp;

    private String signupUsername;
    private String signupPassword;

    public LoginFragment() {
        // Required empty public constructor
    }

    public static LoginFragment newInstance(String userName, String password) {
        LoginFragment fragment = new LoginFragment();
        Bundle args = new Bundle();
        args.putString(ARGS_SIGNUP_USER_NAME, userName);
        args.putString(ARGS_SIGNUP_PASS_WORD, password);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        signupUsername = getArguments().getString(ARGS_SIGNUP_USER_NAME);
        signupPassword = getArguments().getString(ARGS_SIGNUP_PASS_WORD);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        findViews(view);
        initViews();
        setListeners();

        return view;
    }

    private void findViews(View view) {
        mEditTextUserName = view.findViewById(R.id.edittxt_username);
        mEditTextPassword = view.findViewById(R.id.edittxt_password);
        mButtonLogIn = view.findViewById(R.id.btn_log_in);
        mButtonSignUp = view.findViewById(R.id.btn_sign_up);
    }

    private void initViews(){
        mEditTextUserName.getEditText().setText(signupUsername);
        mEditTextPassword.getEditText().setText(signupPassword);
    }

    private void setListeners() {

        mButtonLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TaskPagerActivity.start(getActivity(), 0);
            }
        });

        mButtonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SignupActivity.start(getActivity(),
                        mEditTextUserName.getEditText().getText().toString(),
                        mEditTextPassword.getEditText().getText().toString()
                        );
            }
        });
    }
}