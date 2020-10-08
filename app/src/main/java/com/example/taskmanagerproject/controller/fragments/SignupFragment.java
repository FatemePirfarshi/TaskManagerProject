package com.example.taskmanagerproject.controller.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.taskmanagerproject.R;
import com.example.taskmanagerproject.controller.activities.LoginActivity;
import com.example.taskmanagerproject.model.User;
import com.google.android.material.textfield.TextInputLayout;


public class SignupFragment extends Fragment {

    public static final String ARGS_USER_NAME = "userName";
    public static final String ARGS_PASS_WORD = "passWord";
    private TextInputLayout mEditTextSignUpUserName;
    private TextInputLayout mEditTextSignUpPassword;
    private Button mButtonSignup;

    String userName;
    String passWord;

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

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_signup, container, false);

        findViews(view);
        initViews();
        setListneres();

        return view;
    }

    private void findViews(View view) {
        mEditTextSignUpUserName = view.findViewById(R.id.edittxt_signup_username);
        mEditTextSignUpPassword = view.findViewById(R.id.edittxt_signup_password);
        mButtonSignup = view.findViewById(R.id.btn_signup);
    }

    private void initViews(){
        mEditTextSignUpUserName.getEditText().setText(userName);
        mEditTextSignUpPassword.getEditText().setText(passWord);
    }

    private void setListneres() {
        mButtonSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validateInput()) {
                    String signUpUserName = mEditTextSignUpUserName.getEditText().getText().toString();
                    String signUpPassWord = mEditTextSignUpPassword.getEditText().getText().toString();

                    User user = new User();
                    user.setUserName(signUpUserName);
                    user.setPassWord(signUpPassWord);

                    LoginActivity.start(getActivity(), signUpUserName, signUpPassWord);
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