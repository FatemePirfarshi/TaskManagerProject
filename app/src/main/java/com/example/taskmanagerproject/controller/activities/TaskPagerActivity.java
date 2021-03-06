package com.example.taskmanagerproject.controller.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.example.taskmanagerproject.R;
import com.example.taskmanagerproject.controller.adapter.TaskPagerAdapter;
import com.example.taskmanagerproject.controller.fragments.AddTaskFragment;
import com.example.taskmanagerproject.controller.fragments.TaskListFragment;
import com.example.taskmanagerproject.repository.IRepository;
import com.example.taskmanagerproject.repository.TaskDBRepository;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class TaskPagerActivity extends AppCompatActivity{

    public static final String FRAGMENT_TAG_ADD_TASK = "fragmentTagAddTask";
    public static final String EXTRA_CURRENT_POSITION =
            "com.example.taskmanagerproject.extraCurrentPosition";
    public static final int REQUEST_CODE_ADD_TASK = 100;
    public static final int REQUEST_CODE_DELETE_ALL = 200;
    public static final String FRAGMENT_TAG_DELETE_ALL = "deleteAll";
    public static final String EXTRA_CURRENT_USER_ID = "currentUserId";
    public static final String TAG = "TaskPagerActivity";

    public static void start(Context context, int position, long userId) {
        Intent starter = new Intent(context, TaskPagerActivity.class);
        starter.putExtra(EXTRA_CURRENT_POSITION, position);
        starter.putExtra(EXTRA_CURRENT_USER_ID, userId);
        context.startActivity(starter);
    }

    private int mCurrentPosition;
    public static IRepository mRepository;
    private FloatingActionButton mAddButton;

    private TabLayout mTabLayout;
    private ViewPager2 mViewPager2;
    private TaskPagerAdapter mTaskPagerAdapter;
    private long mUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mCurrentPosition = getIntent().getIntExtra(EXTRA_CURRENT_POSITION, 0);
        mUserId = getIntent().getLongExtra(EXTRA_CURRENT_USER_ID, 0);
        mRepository = TaskDBRepository.getInstance(this, mCurrentPosition);

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

        mTaskPagerAdapter = new TaskPagerAdapter(this, this, mUserId);
        // mViewPager2.setOffscreenPageLimit(0);
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
                AddTaskFragment fragment = AddTaskFragment.newInstance(mCurrentPosition, mUserId);
                fragment.setTargetFragment(
                        mTaskPagerAdapter.getFragments(mCurrentPosition), REQUEST_CODE_ADD_TASK);
                fragment.show(getSupportFragmentManager(), FRAGMENT_TAG_ADD_TASK);
            }
        });
    }

//    @Override
//    public void onTaskListUpdated(int position) {
//        if (findViewById(R.id.fragment_container) != null) {
//            TaskListFragment taskListFragment = (TaskListFragment) getSupportFragmentManager()
//                    .findFragmentById(R.id.fragment_container);
//            taskListFragment.updateUI(position);
//            Log.d(TAG,"Position" + position);
//        }
//    }
}