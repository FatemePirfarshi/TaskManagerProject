package com.example.taskmanagerproject.controller.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.example.taskmanagerproject.R;

import java.util.Calendar;
import java.util.Date;

public class TimePickerFragment extends DialogFragment {

    public static final String ARGS_TASK_DATE = "argsTaskDate";
    public static final String USER_SELECTED_TIME = "userSelecteedTime";

    private Date mTaskDate;
    private TimePicker mTimePicker;
    private Calendar mCalendar;

    public TimePickerFragment() {
        // Required empty public constructor
    }

    public static TimePickerFragment newInstance(Date taskDate) {
        TimePickerFragment fragment = new TimePickerFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARGS_TASK_DATE, taskDate);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mTaskDate = (Date) getArguments().getSerializable(ARGS_TASK_DATE);
        mCalendar = Calendar.getInstance();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity())
                .inflate(R.layout.fragment_time_picker, null);

        findViews(view);
        initViews();

        return new AlertDialog.Builder(getActivity())
                .setView(view)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        sendResult();
                    }
                })
                .setNegativeButton(android.R.string.cancel, null)
                .create();
    }

    private void findViews(View view) {
        mTimePicker = view.findViewById(R.id.time_picker_task);
    }

    private void initViews() {
        mCalendar.setTime(mTaskDate);
        int hour = mCalendar.get(Calendar.HOUR_OF_DAY);
        int minute = mCalendar.get(Calendar.MINUTE);
        mTimePicker.setHour(hour);
        mTimePicker.setMinute(minute);
    }

    private void sendResult() {

        int hour = mTimePicker.getCurrentHour();
        int minute = mTimePicker.getCurrentMinute();

        mTaskDate.setHours(hour);
        mTaskDate.setMinutes(minute);

        Fragment fragment = getTargetFragment();
        Intent intent = new Intent();
        intent.putExtra(USER_SELECTED_TIME, mTaskDate.getTime());
        fragment.onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, intent);
    }
}