package com.example.taskmanagerproject.controller;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;

import com.example.taskmanagerproject.R;


public class DatePickerFragment extends DialogFragment {

    private DatePicker mDatePicker;

    public DatePickerFragment() {
        // Required empty public constructor
    }

    public static DatePickerFragment newInstance() {
        DatePickerFragment fragment = new DatePickerFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        View view = LayoutInflater.from(getActivity())
                .inflate(R.layout.fragment_date_picker, null);

        findViews(view);

        return new AlertDialog.Builder(getActivity())
                .setView(view)
                .setPositiveButton(android.R.string.ok,null)
                .setNegativeButton(android.R.string.cancel, null)
                .create();
    }

    private void findViews(View view) {
        mDatePicker = view.findViewById(R.id.date_picker_task);
    }
}