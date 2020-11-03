package com.example.taskmanagerproject.controller.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.taskmanagerproject.R;
import com.example.taskmanagerproject.controller.activities.LoginActivity;
import com.example.taskmanagerproject.model.User;
import com.example.taskmanagerproject.repository.UserDBRepository;
import com.google.android.material.textfield.TextInputLayout;


public class SignupFragment extends Fragment {

    public static final String BUNDLE_SIGNUP_USERNAME = "signupUsername";
    public static final String BUNDLE_SIGNUP_PASSWORD = "signupPassword";

    public static final String ARGS_USER_NAME = "userName";
    public static final String ARGS_PASS_WORD = "passWord";
    private TextInputLayout mEditTextSignUpUserName;
    private TextInputLayout mEditTextSignUpPassword;
    private Button mButtonSignup;
    private UserDBRepository mRepository;
    private CheckBox mIsAdmin;

    private String userName;
    private String passWord;
    //  private User mUser = new User();

    public SignupFragment() {
        // Required empty public constructor
    }

    public static SignupFragment newInstance(String userName, String passWord) {
        SignupFragment fragment = new SignupFragment();
        Bundle args = new Bundle();

        args.putString(ARGS_USER_NAME, userName);
        args.putString(ARGS_PASS_WORD, passWord);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        userName = getArguments().getString(ARGS_USER_NAME);
        passWord = getArguments().getString(ARGS_PASS_WORD);

        mRepository = UserDBRepository.getInstance(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_signup, container, false);

        findViews(view);

        if (savedInstanceState != null) {
            userName = savedInstanceState.getString(BUNDLE_SIGNUP_USERNAME);
            passWord = savedInstanceState.getString(BUNDLE_SIGNUP_PASSWORD);
        }

        initViews();
        setListneres();

        return view;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString(BUNDLE_SIGNUP_USERNAME, mEditTextSignUpUserName.getEditText().getText().toString());
        outState.putString(BUNDLE_SIGNUP_PASSWORD, mEditTextSignUpPassword.getEditText().getText().toString());
    }

    private void findViews(View view) {
        mEditTextSignUpUserName = view.findViewById(R.id.edittxt_signup_username);
        mEditTextSignUpPassword = view.findViewById(R.id.edittxt_signup_password);
        mButtonSignup = view.findViewById(R.id.btn_signup);
        mIsAdmin = view.findViewById(R.id.checkBox_admin);
    }

    private void initViews() {
        mEditTextSignUpUserName.getEditText().setText(userName);
        mEditTextSignUpPassword.getEditText().setText(passWord);
    }

    private void setListneres() {
        mButtonSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validateInput()) {
                    String signUpUserName = mEditTextSignUpUserName.getEditText().getText().toString();
                    String signUpPassWord = mEditTextSignUpPassword.getEditText().getText().toString();
                    User user = mRepository.getUser(signUpUserName, signUpPassWord);
                    if (user == null) {
                        user = new User();
                        user.setUserName(signUpUserName);
                        user.setPassWord(signUpPassWord);
                        user.setAdmin(mIsAdmin.isChecked());

                        mRepository.insertUser(user);
                    }
                    LoginActivity.start(getActivity(), user.getId());
                    getActivity().finish();
                }
            }
        });
    }

    private boolean validateInput() {
        if (mEditTextSignUpUserName.getEditText().getText().toString().trim().isEmpty()) {
            mEditTextSignUpUserName.setErrorEnabled(true);
            mEditTextSignUpUserName.setError("Field cannot be empty!");
            return false;
        }
        mEditTextSignUpUserName.setErrorEnabled(false);

        if (mEditTextSignUpPassword.getEditText().getText().toString().trim().isEmpty()) {
            mEditTextSignUpUserName.setErrorEnabled(false);
            mEditTextSignUpPassword.setErrorEnabled(true);
            mEditTextSignUpPassword.setError("Field cannot be empty!");
            return false;
        }
        mEditTextSignUpPassword.setErrorEnabled(false);
        return true;
    }
}