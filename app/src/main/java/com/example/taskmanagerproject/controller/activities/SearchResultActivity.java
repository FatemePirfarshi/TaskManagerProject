package com.example.taskmanagerproject.controller.activities;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.example.taskmanagerproject.repository.TaskDBRepository;

import java.util.Date;

public class SearchResultActivity extends Activity {

    private TaskDBRepository mTaskDBRepository;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        handleIntent(getIntent());
        mTaskDBRepository = TaskDBRepository.getInstance(this, 0);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {

        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            Date date = (Date) intent.getSerializableExtra(SearchManager.QUERY);
            long time = intent.getLongExtra(SearchManager.QUERY, 0);
            mTaskDBRepository.getTasksSearch(query, query, date, time);
        }
    }
}
