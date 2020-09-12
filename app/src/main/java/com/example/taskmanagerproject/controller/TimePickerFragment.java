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
import android.widget.TimePicker;

import com.example.taskmanagerproject.R;

public class TimePickerFragment extends DialogFragment {

    private TimePicker mTimePicker;

    public TimePickerFragment() {
        // Required empty public constructor
    }


    public static TimePickerFragment newInstance() {
        TimePickerFragment fragment = new TimePickerFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_time_picker, container, false);
        return view;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity())
                .inflate(R.layout.fragment_time_picker,null);

        findViews(view);

        return new AlertDialog.Builder(getActivity())
                .setView(view)
                .setPositiveButton(android.R.string.ok , null)
                .setNegativeButton(android.R.string.cancel, null)
                .create();
    }

    private void findViews(View view) {
        mTimePicker = view.findViewById(R.id.time_picker_task);
    }
}