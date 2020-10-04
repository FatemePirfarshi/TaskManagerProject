package com.example.taskmanagerproject.controller;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.example.taskmanagerproject.R;
import com.example.taskmanagerproject.repository.IRepository;
import com.example.taskmanagerproject.repository.TaskRepository;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.List;

public class TaskPagerActivity extends AppCompatActivity {

    public static final String EXTRA_TASK_ID = "com.example.taskmanagerproject.extraTaskId";
    public static final String FRAGMENT_TAG_ADD_TASK = "fragmentTagAddTask";
    public static final String EXTRA_CURRENT_POSITION =
            "com.example.taskmanagerproject.extraCurrentPosition";

    private int mCurrentPosition;

    public static void start(Context context, int position) {
        Intent starter = new Intent(context, TaskPagerActivity.class);
        //starter.putExtra(EXTRA_LIST_OF_TASK, (Serializable) tasks);
        starter.putExtra(EXTRA_CURRENT_POSITION, position);
        context.startActivity(starter);
    }

    public static IRepository mRepository;

    private TabLayout mTabLayout;
    private ViewPager2 mViewPager2;
    private FloatingActionButton mAddButton;

    private List<Fragment> mFragmentList = new ArrayList<>();
    private TaskListFragment mTaskListFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mCurrentPosition = getIntent().getIntExtra(EXTRA_CURRENT_POSITION, 0);
        mRepository = TaskRepository.getInstance(mCurrentPosition);

        findViews();
        initViews();
        setListeners();
    }

    private void findViews() {
        mTabLayout = findViewById(R.id.tab_layout);
        mViewPager2 = findViewById(R.id.pager);
        mAddButton = findViewById(R.id.btn_add);
    }

    private void initViews() {

        mFragmentList.add(TaskListFragment.newInstance(mRepository.getTodoTask(), 0));
        mFragmentList.add(TaskListFragment.newInstance(mRepository.getDoingTask(), 1));
        mFragmentList.add(TaskListFragment.newInstance(mRepository.getDoneTask(), 2));

        TaskPagerAdapter taskPagerAdapter = new TaskPagerAdapter(this);
        mViewPager2.setAdapter(taskPagerAdapter);
        mViewPager2.setCurrentItem(mCurrentPosition);

        final String[] tabText = {"TODO", "DOING", "DONE"};

        new TabLayoutMediator(mTabLayout, mViewPager2,
                new TabLayoutMediator.TabConfigurationStrategy() {
                    @Override
                    public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                        tab.setText(tabText[position]);
                    }
                }).attach();
    }

    private void setListeners() {

        mAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCurrentPosition = mTabLayout.getSelectedTabPosition();
                AddTaskFragment.newInstance(mCurrentPosition)
                        .show(getSupportFragmentManager(), FRAGMENT_TAG_ADD_TASK);

            }
        });
    }

    private class TaskPagerAdapter extends FragmentStateAdapter {

        public TaskPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
            super(fragmentActivity);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            mTaskListFragment =
                    TaskListFragment.newInstance(
                            mRepository.getListWithPosition(position), position);
            return mTaskListFragment;
        }

        @Override
        public int getItemCount() {
            return mFragmentList.size();
        }

    }
}