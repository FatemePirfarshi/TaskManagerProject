package com.example.taskmanagerproject.controller.adapter;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.taskmanagerproject.controller.fragments.TaskListFragment;
import com.example.taskmanagerproject.repository.TaskDBRepository;

import java.util.ArrayList;
import java.util.List;

public class TaskPagerAdapter extends FragmentStateAdapter {

    private Context mContext;

    private TaskDBRepository mRepository = TaskDBRepository.getInstance(mContext, 0);
    private List<TaskListFragment> mFragments = new ArrayList<TaskListFragment>() {{
        add(TaskListFragment.newInstance(mRepository.getListWithPosition(0), 0));
        add(TaskListFragment.newInstance(mRepository.getListWithPosition(1), 1));
        add(TaskListFragment.newInstance(mRepository.getListWithPosition(2), 2));
    }};

    public TaskPagerAdapter(@NonNull FragmentActivity fragmentActivity, Context context) {
        super(fragmentActivity);
        mContext = context;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getItemCount() {
        return mFragments.size();
    }

    public TaskListFragment getFragments(int position) {
        return mFragments.get(position);
    }
}
