package com.example.taskmanagerproject.controller.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.taskmanagerproject.R;
import com.example.taskmanagerproject.controller.activities.TaskPagerActivity;
import com.example.taskmanagerproject.model.Task;
import com.example.taskmanagerproject.repository.TaskDBRepository;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class AddTaskFragment extends DialogFragment {

    public static final int REQUEST_CODE_DATE_PiCKER = 0;
    public static final int REQUEST_CODE_TIME_PICKER = 1;

    public static final String FRAGMENT_TAG_DATE_PICKER = "datePicker";
    public static final String FRAGMENT_TAG_TIME_PICKER = "timePicker";
    public static final String ARGS_LIST_POSITOIN = "listPositoin";

    public static final String EXTRA_NEW_TASK_position =
            "com.example.taskmanagerproject.AddTaskFragment_extra_new_task_position";
    public static final String KEY_USER_SELECTED_DATE = "userSelectedDate";
    public static final String KEY_USER_SELECTED_TIME = "userSelectedTime";

    private EditText mEditTextTitle;
    private EditText mEditTextDescription;
    private Button mButtonDate;
    private Button mButtonTime;
    private CheckBox mCheckBoxDone;

    private LinearLayout mRootLinearLayout;

    private TaskDBRepository mRepository;
    private List<Task> mCurrentList;
    private int mCurrentPosition;

    private Task mTask = new Task();

    public Task getTask() {
        return mTask;
    }

    private Date userSelectedDate;
    private Long userSelectedTime;

    public AddTaskFragment() {
        // Required empty public constructor
    }

    public static AddTaskFragment newInstance(int position) {
        AddTaskFragment fragment = new AddTaskFragment();
        Bundle args = new Bundle();
        args.putInt(ARGS_LIST_POSITOIN, position);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCurrentPosition = getArguments().getInt(ARGS_LIST_POSITOIN);
        mRepository = TaskDBRepository.getInstance(getActivity(), mCurrentPosition);
        mCurrentList = mRepository.getListWithPosition(mCurrentPosition);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
        View view = layoutInflater.inflate(R.layout.fragment_show_detail, null);

        findViews(view);

        if (savedInstanceState != null) {
            userSelectedDate = (Date) savedInstanceState.getSerializable(KEY_USER_SELECTED_DATE);
            userSelectedTime = savedInstanceState.getLong(KEY_USER_SELECTED_TIME);
            mTask.setDate(userSelectedDate);
            mTask.getDate().setTime(userSelectedTime);
        }

        initViews();
        setListeners();

        return new AlertDialog.Builder(getActivity())
                .setView(view)
                .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        if (mEditTextTitle.getText().toString().trim().isEmpty())
                            Toast.makeText(getActivity(),
                                    "Title field can't be blank!!", Toast.LENGTH_SHORT).show();
                        else {
                            mTask.setTitle(mEditTextTitle.getText().toString());
                            mTask.setDiscription(mEditTextDescription.getText().toString());
                            mTask.setDone(mCheckBoxDone.isChecked());
                            mTask.setPosition(mCurrentPosition);

                            mRepository.insertTask(mTask);
                            mRepository.updateLists(mTask);

                            Intent intent = new Intent();
                            intent.putExtra(EXTRA_NEW_TASK_position, mTask.getPosition());

                            getTargetFragment().onActivityResult(
                                    TaskPagerActivity.REQUEST_CODE_ADD_TASK, Activity.RESULT_OK, intent);
                            dismiss();
                        }
                    }
                })
                .setNegativeButton(android.R.string.cancel, null)
                .create();

    }

    private void findViews(View view) {
        mEditTextTitle = view.findViewById(R.id.edittxt_title);
        mEditTextDescription = view.findViewById(R.id.edittxt_description);
        mButtonDate = view.findViewById(R.id.btn_date);
        mButtonTime = view.findViewById(R.id.btn_time);
        mCheckBoxDone = view.findViewById(R.id.checkBox_done);
        mRootLinearLayout = view.findViewById(R.id.root_linear_layout);
    }

    private void initViews() {
        mTask.setTitle(mEditTextTitle.getText().toString());
        mTask.setDiscription(mEditTextDescription.getText().toString());
        mTask.setDone(mCheckBoxDone.isChecked());

        mButtonDate.setText(new SimpleDateFormat("yyyy.MM.dd").format(mTask.getDate()));
        mButtonTime.setText(new SimpleDateFormat("HH:mm:ss").format(mTask.getDate()));
    }

    private void setListeners() {
        mButtonDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerFragment datePickerFragment = DatePickerFragment.newInstance(mTask.getDate());

                datePickerFragment.setTargetFragment(
                        AddTaskFragment.this, REQUEST_CODE_DATE_PiCKER);

                datePickerFragment.show(getActivity().getSupportFragmentManager(),
                        FRAGMENT_TAG_DATE_PICKER);
            }
        });

        mButtonTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerFragment timePickerFragment = TimePickerFragment.newInstance(mTask.getDate());

                timePickerFragment.setTargetFragment(
                        AddTaskFragment.this, REQUEST_CODE_TIME_PICKER);

                timePickerFragment.show(getActivity().getSupportFragmentManager(),
                        FRAGMENT_TAG_TIME_PICKER);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if (resultCode != Activity.RESULT_OK || data == null)
            return;

        if (requestCode == REQUEST_CODE_DATE_PiCKER) {
            userSelectedDate =
                    (Date) data.getSerializableExtra(DatePickerFragment.USER_SELECTED_DATE);
            updateTaskDate(userSelectedDate);
        }

        if (requestCode == REQUEST_CODE_TIME_PICKER) {
            userSelectedTime =
                    data.getLongExtra(TimePickerFragment.USER_SELECTED_TIME, 0);
            updateTaskTime(userSelectedTime);
        }

    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(KEY_USER_SELECTED_DATE, userSelectedDate);
        outState.putLong(KEY_USER_SELECTED_TIME, userSelectedTime);
    }

    public void updateTaskDate(Date userSelectedDate) {
        mTask.setDate(userSelectedDate);
        mButtonDate.setText(new SimpleDateFormat("yyyy.MM.dd").format(mTask.getDate()));
    }

    public void updateTaskTime(Long userSelectedTime) {
        mTask.getDate().setTime(userSelectedTime);
        mButtonTime.setText(new SimpleDateFormat("HH:mm:ss").format(mTask.getDate()));
    }
}