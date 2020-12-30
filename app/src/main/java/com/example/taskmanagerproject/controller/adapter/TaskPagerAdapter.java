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

public class TaskPagerAdapter extends FragmentStateAdapter implements TaskListFragment.Callbacks  {

    private Context mContext;
    private long mUserId;

    private TaskDBRepository mRepository = TaskDBRepository.getInstance(mContext, 0);

    private List<TaskListFragment> mFragments = new ArrayList<TaskListFragment>() {{
        add(TaskListFragment.newInstance(0, mUserId));
        add(TaskListFragment.newInstance(1, mUserId));
        add(TaskListFragment.newInstance(2, mUserId));
    }};

    public TaskPagerAdapter(@NonNull FragmentActivity fragmentActivity, Context context, long userId) {
        super(fragmentActivity);
        mContext = context;
        mUserId =userId;
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

    @Override
    public void onTaskListUpdated(int position) {
       mFragments.get(position).updateUI(position);
    }
}
