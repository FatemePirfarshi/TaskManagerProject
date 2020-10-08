package com.example.taskmanagerproject.controller.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.example.taskmanagerproject.R;
import com.example.taskmanagerproject.controller.adapter.TaskPagerAdapter;
import com.example.taskmanagerproject.controller.fragments.AddTaskFragment;
import com.example.taskmanagerproject.controller.fragments.TaskListFragment;
import com.example.taskmanagerproject.repository.IRepository;
import com.example.taskmanagerproject.repository.TaskRepository;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.List;

public class TaskPagerActivity extends AppCompatActivity {

   // public static final String EXTRA_TASK_ID = "com.example.taskmanagerproject.extraTaskId";
    public static final String FRAGMENT_TAG_ADD_TASK = "fragmentTagAddTask";
    public static final String EXTRA_CURRENT_POSITION =
            "com.example.taskmanagerproject.extraCurrentPosition";
    public static final int REQUEST_CODE_ADD_TASK = 100;

    public static void start(Context context, int position) {
        Intent starter = new Intent(context, TaskPagerActivity.class);
        //starter.putExtra(EXTRA_LIST_OF_TASK, (Serializable) tasks);
        starter.putExtra(EXTRA_CURRENT_POSITION, position);
        context.startActivity(starter);
    }

    private int mCurrentPosition;
    public static IRepository mRepository;
    private FloatingActionButton mAddButton;

    private TabLayout mTabLayout;
    private ViewPager2 mViewPager2;
    private TaskPagerAdapter mTaskPagerAdapter;

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

        mTaskPagerAdapter = new TaskPagerAdapter(this);
        mViewPager2.setOffscreenPageLimit(1);
        mViewPager2.setAdapter(mTaskPagerAdapter);
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
                AddTaskFragment fragment = AddTaskFragment.newInstance(mCurrentPosition);
                fragment.setTargetFragment(mTaskPagerAdapter.getFragments(mCurrentPosition), REQUEST_CODE_ADD_TASK);
                fragment.show(getSupportFragmentManager(), FRAGMENT_TAG_ADD_TASK);
            }
        });
    }
}