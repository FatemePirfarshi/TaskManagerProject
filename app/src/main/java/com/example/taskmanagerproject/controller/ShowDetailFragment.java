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

public class ShowDetailFragment extends DialogFragment {

    public static final int REQUEST_CODE_DATE_PiCKER = 0;
    public static final int REQUEST_CODE_TIME_PICKER = 1;

    public static final String FRAGMENT_TAG_DATE_PICKER = "datePicker";
    public static final String FRAGMENT_TAG_TIME_PICKER = "timePicker";

    private EditText mEditTextTitle;
    private EditText mEditTextDescription;
    private Button mButtonDate;
    private Button mButtonTime;
    private CheckBox mCheckBoxDone;

    public ShowDetailFragment() {
        // Required empty public constructor
    }

    public static ShowDetailFragment newInstance() {
        ShowDetailFragment fragment = new ShowDetailFragment();
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
        View view = inflater.inflate(R.layout.fragment_show_detail, container, false);
        return view;
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
    }

    private void initViews() {
        mButtonDate.setText("Date");
        mButtonTime.setText("Time");
//        mButtonDate.setText(new SimpleDateFormat("yyyy.MM.dd").format(getDate));
//        mButtonTime.setText(new SimpleDateFormat("HH:mm:ss").format(mCrime.getDate()));
    }

    private void setListeners() {
//        mButtonDate.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                DatePickerFragment datePickerFragment = DatePickerFragment.newInstance();
//
//                datePickerFragment.setTargetFragment(
//                        ShowDetailFragment.this, REQUEST_CODE_DATE_PiCKER);
//
//                datePickerFragment.show( getActivity().getSupportFragmentManager(),
//                        FRAGMENT_TAG_DATE_PICKER);
//            }
//        });
//        mButtonTime.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                TimePickerFragment timePickerFragment = TimePickerFragment.newInstance();
//
//                timePickerFragment.setTargetFragment(
//                        ShowDetailFragment.this , REQUEST_CODE_TIME_PICKER);
//
//                timePickerFragment.show(getActivity().getSupportFragmentManager(),
//                        FRAGMENT_TAG_TIME_PICKER);
//            }
//        });
    }
}