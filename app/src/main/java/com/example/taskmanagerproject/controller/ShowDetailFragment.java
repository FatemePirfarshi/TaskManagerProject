package com.example.taskmanagerproject.controller;

import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.taskmanagerproject.R;

public class ShowDetailFragment extends DialogFragment {

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
}