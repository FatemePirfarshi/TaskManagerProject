package com.example.taskmanagerproject.controller.activities;

import androidx.fragment.app.Fragment;

import com.example.taskmanagerproject.controller.fragments.SearchFragment;

public class SearchActivity extends SingleFragmentActivity{

    @Override
    public Fragment createFragment() {
        return SearchFragment.newInstance();
    }

}