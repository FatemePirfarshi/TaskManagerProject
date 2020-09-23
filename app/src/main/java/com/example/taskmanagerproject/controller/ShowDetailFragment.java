package com.example.taskmanagerproject.controller;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.example.taskmanagerproject.R;
import com.example.taskmanagerproject.model.Task;
import com.example.taskmanagerproject.repository.TaskRepository;

import java.text.SimpleDateFormat;
import java.util.UUID;

public class ShowDetailFragment extends DialogFragment {

    public static final int REQUEST_CODE_DATE_PiCKER = 0;
    public static final int REQUEST_CODE_TIME_PICKER = 1;

    public static final String FRAGMENT_TAG_DATE_PICKER = "datePicker";
    public static final String FRAGMENT_TAG_TIME_PICKER = "timePicker";
    public static final String ARGS_TASK_ID = "argsTaskId";
    public static final String ARGS_TASK_POSITION = "argsTaskPosition";

    private EditText mEditTextTitle;
    private EditText mEditTextDescription;
    private Button mButtonDate;
    private Button mButtonTime;
    private CheckBox mCheckBoxDone;

    private Task mTask;
    private UUID mTaskId;
    private int mPosition;
    private TaskRepository mRepository;

    public ShowDetailFragment() {
        // Required empty public constructor
    }

    public static ShowDetailFragment newInstance(UUID id, int position) {
        ShowDetailFragment fragment = new ShowDetailFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARGS_TASK_ID, id);
        args.putInt(ARGS_TASK_POSITION, position);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mTaskId = (UUID) getArguments().getSerializable(ARGS_TASK_ID);
        mPosition = getArguments().getInt(ARGS_TASK_POSITION);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
        View view = layoutInflater.inflate(R.layout.fragment_show_detail, null);

        findViews(view);
        initViews();
        setListeners();

        return new AlertDialog.Builder(getActivity())
                .setView(view)
                .setPositiveButton("Save", null)
                .setPositiveButton("Edit", null)
                .setNegativeButton("Delete", null)
                .create();
    }

    private void findViews(View view) {
        mEditTextTitle = view.findViewById(R.id.edittxt_title);
        mEditTextDescription = view.findViewById(R.id.edittxt_description);
        mButtonDate = view.findViewById(R.id.btn_date);
        mButtonTime = view.findViewById(R.id.btn_time);
        mCheckBoxDone = view.findViewById(R.id.checkBox_done);
    }

    private void initViews() {
        mRepository = TaskRepository.getInstance(mPosition);
        mTask = mRepository.getTask(mTaskId);

        mEditTextTitle.setText(mTask.getTitle());
        mEditTextDescription.setText(mTask.getDiscription());
        mCheckBoxDone.setChecked(mTask.isDone());

        mButtonDate.setText(new SimpleDateFormat("yyyy.MM.dd").format(mTask.getDate()));
        mButtonTime.setText(new SimpleDateFormat("HH:mm:ss").format(mTask.getDate()));
    }

    private void setListeners() {
        mButtonDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerFragment datePickerFragment =
                        DatePickerFragment.newInstance(mTask.getDate());

                datePickerFragment.setTargetFragment(
                        ShowDetailFragment.this, REQUEST_CODE_DATE_PiCKER);

                datePickerFragment.show(getActivity().getSupportFragmentManager(),
                        FRAGMENT_TAG_DATE_PICKER);
            }
        });

        mButtonTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerFragment timePickerFragment =
                        TimePickerFragment.newInstance(mTask.getDate());

                timePickerFragment.setTargetFragment(
                        ShowDetailFragment.this, REQUEST_CODE_TIME_PICKER);

                timePickerFragment.show(getActivity().getSupportFragmentManager(),
                        FRAGMENT_TAG_TIME_PICKER);
            }
        });
    }
}