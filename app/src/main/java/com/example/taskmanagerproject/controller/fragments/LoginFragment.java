package com.example.taskmanagerproject.controller.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.taskmanagerproject.R;
import com.example.taskmanagerproject.controller.activities.SignupActivity;
import com.example.taskmanagerproject.controller.activities.TaskPagerActivity;
import com.example.taskmanagerproject.model.User;
import com.example.taskmanagerproject.repository.TaskDBRepository;
import com.example.taskmanagerproject.repository.UserDBRepository;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;

import java.util.UUID;

public class LoginFragment extends Fragment {

    private static final String TAG = "LoginFragment";
    public static final String BUNDLE_LOGIN_USERNAME = "loginUsername";
    public static final String BUNDLE_LOGIN_PASSWORD = "loginPassword";
    public static final String ARGS_SIGN_UP_USER_ID = "signUpUserID";

    private TextInputLayout mEditTextUserName;
    private TextInputLayout mEditTextPassword;
    private Button mButtonLogIn;
    private Button mButtonSignUp;
    private FrameLayout mFrameLayout;

    private String signupUsername;
    private String signupPassword;
    private UUID mUserId;

    private TaskDBRepository mTaskDBRepository;
    private UserDBRepository mUserRepository;

    public LoginFragment() {
        // Required empty public constructor
    }

    public static LoginFragment newInstance(UUID uuid) {
        LoginFragment fragment = new LoginFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARGS_SIGN_UP_USER_ID, uuid);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        mTaskDBRepository = TaskDBRepository.getInstance(getActivity(), 0);
        mUserRepository = UserDBRepository.getInstance(getActivity());

        mUserId = (UUID) getArguments().getSerializable(ARGS_SIGN_UP_USER_ID);
        if (mUserId != null) {
            User user = mUserRepository.getUser(mUserId);
            signupUsername = user.getUserName();
            signupPassword = user.getPassWord();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        findViews(view);
        if (savedInstanceState != null) {
            signupUsername = savedInstanceState.getString(BUNDLE_LOGIN_USERNAME);
            signupPassword = savedInstanceState.getString(BUNDLE_LOGIN_PASSWORD);
        }
        initViews();
        setListeners();

        return view;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(BUNDLE_LOGIN_USERNAME, mEditTextUserName.getEditText().getText().toString());
        outState.putString(BUNDLE_LOGIN_PASSWORD, mEditTextPassword.getEditText().getText().toString());
    }

    private void findViews(View view) {
        mEditTextUserName = view.findViewById(R.id.edittxt_username);
        mEditTextPassword = view.findViewById(R.id.edittxt_password);
        mButtonLogIn = view.findViewById(R.id.btn_log_in);
        mButtonSignUp = view.findViewById(R.id.btn_sign_up);
        mFrameLayout = view.findViewById(R.id.root_layout_login);
    }

    private void initViews() {
        mEditTextUserName.getEditText().setText(signupUsername);
        mEditTextPassword.getEditText().setText(signupPassword);
        mUserRepository = UserDBRepository.getInstance(getActivity());
        mTaskDBRepository = TaskDBRepository.getInstance(getActivity(), 0);
    }

    private void setListeners() {

        mButtonLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateInput();

                User user = mUserRepository.getUser(mEditTextUserName.getEditText().getText().toString(),
                        mEditTextPassword.getEditText().getText().toString());

                if (user != null) {
                    Log.d(TAG, "onClick: mUserId" + user.getUserId());
                    mTaskDBRepository.setLists(user.getId());
                    TaskPagerActivity.start(getActivity(), 0, user.getUserId());
                    getActivity().finish();

                } else if (user == null)
                    Snackbar.make(mFrameLayout,
                            "please click SIGN UP!!", Snackbar.LENGTH_LONG).show();

                else if (mEditTextUserName.getEditText().getText().toString().equals(signupUsername) &&
                        mEditTextPassword.getEditText().getText().toString().equals(signupPassword)) {

                    mTaskDBRepository.setLists(mUserId);
                    TaskPagerActivity.start(getActivity(), 0, user.getUserId());
                    getActivity().finish();
                } else
                    Snackbar.make(mFrameLayout,
                            "Your information are not valid!!", Snackbar.LENGTH_LONG).show();
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

    private boolean validateInput() {
        if (mEditTextUserName.getEditText().getText().toString().trim().isEmpty()) {
            mEditTextUserName.setErrorEnabled(true);
            mEditTextUserName.setError("Field cannot be empty!");
            return false;
        }
        mEditTextUserName.setErrorEnabled(false);
        return true;
    }
}