package com.example.taskmanagerproject.controller.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.taskmanagerproject.repository.TaskDBRepository;

public class DeleteAllFragment extends DialogFragment {

    public static final String ARGS_CURRENT_POSITION = "currentPosition";
    private int mPosition;
    private TaskDBRepository mRepository;

    public DeleteAllFragment() {
        // Required empty public constructor
    }

    public static DeleteAllFragment newInstance(int position) {
        DeleteAllFragment fragment = new DeleteAllFragment();
        Bundle args = new Bundle();
        args.putInt(ARGS_CURRENT_POSITION, position);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mPosition = getArguments().getInt(ARGS_CURRENT_POSITION);
        mRepository = TaskDBRepository.getInstance(getActivity(), mPosition);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        return new AlertDialog.Builder(getActivity())
                .setTitle("Are you sure want to clear all tasks?")
                .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        mRepository.deleteAll();
                        dismiss();
                    }
                })
                .setNegativeButton("NO", null)
                .create();
    }
}