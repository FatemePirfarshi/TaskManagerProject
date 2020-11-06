package com.example.taskmanagerproject.controller.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.example.taskmanagerproject.R;

public class ChangeStateFragment extends DialogFragment {

    public static final String ARGS_TASK_STATE = "taskState";
    public static final String USER_SELECTED_POSITION = "userSelectedPosition";

    private RadioGroup mRadioGroupStates;
    private RadioButton mButtonTodo, mButtonDoing, mButtonDone;

    private int mCurrentPosition;

    public ChangeStateFragment() {
        // Required empty public constructor
    }

    public static ChangeStateFragment newInstance(int position) {
        ChangeStateFragment fragment = new ChangeStateFragment();
        Bundle args = new Bundle();

        args.putInt(ARGS_TASK_STATE, position);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mCurrentPosition = getArguments().getInt(ARGS_TASK_STATE);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_change_state, null);

        findViews(view);
        initViews();
        setListeners();

        return new AlertDialog.Builder(getActivity())
                .setView(view)
                .create();
    }

    private void findViews(View view) {
        mRadioGroupStates = view.findViewById(R.id.radiogp_states);
        mButtonTodo = view.findViewById(R.id.btn_todo);
        mButtonDoing = view.findViewById(R.id.btn_doing);
        mButtonDone = view.findViewById(R.id.btn_done);
    }

    private void initViews() {
        switch (mCurrentPosition) {
            case 0:
                mButtonTodo.setChecked(true);
                break;
            case 1:
                mButtonDoing.setChecked(true);
                break;
            case 2:
                mButtonDone.setChecked(true);
                break;
        }
    }

    private void setListeners() {
        mRadioGroupStates.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                switch (checkedId) {
                    case R.id.btn_todo:
                        sendResult(0);
                        dismiss();
                        break;
                    case R.id.btn_doing:
                        sendResult(1);
                        dismiss();
                        break;
                    case R.id.btn_done:
                        sendResult(2);
                        dismiss();
                        break;
                }
            }
        });
    }

    private void sendResult(int userSelectedState) {
        Fragment fragment = getTargetFragment();
        Intent intent = new Intent();
        intent.putExtra(USER_SELECTED_POSITION, userSelectedState);
        fragment.onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, intent);
    }
}