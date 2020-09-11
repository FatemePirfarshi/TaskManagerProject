package com.example.taskmanagerproject.controller;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.example.taskmanagerproject.R;
import com.example.taskmanagerproject.model.Task;
import com.example.taskmanagerproject.repository.IRepository;
import com.example.taskmanagerproject.repository.TaskRepository;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.List;

public class TaskPagerActivity extends AppCompatActivity {

    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, TaskPagerActivity.class);
        return intent;
    }

    public static IRepository mRepository;

    private TabLayout mTabLayout;
    private ViewPager2 mViewPager2;
    private ImageView mImageView;

    private List<Fragment> mFragmentList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRepository = TaskRepository.getINstance();

        findViews();
        initViews();
    }

    private void findViews() {
        mTabLayout = findViewById(R.id.tab_layout);
        mViewPager2 = findViewById(R.id.pager);
        mImageView = findViewById(R.id.imgview_task_list);
    }

    private void initViews() {

        mFragmentList.add(TaskListFragment.newInstance(mRepository.getTodoTAsk()));
        mFragmentList.add(TaskListFragment.newInstance(mRepository.getDoingTAsk()));
        mFragmentList.add(TaskListFragment.newInstance(mRepository.getDoneTAsk()));

        TaskPagerAdapter taskPagerAdapter = new TaskPagerAdapter(this);
        mViewPager2.setAdapter(taskPagerAdapter);

        final String[] tabText = {"TODO", "DOING", "DONE"};

        new TabLayoutMediator(mTabLayout, mViewPager2,
                new TabLayoutMediator.TabConfigurationStrategy() {
                    @Override public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                        tab.setText(tabText[position]);
                       // checkImageState(tab);
                    }
                }).attach();
    }

//    private void checkImageState(@NonNull TabLayout.Tab tab) {
//        if(tab.getText().equals("TODO"))
//            mImageView.setImageResource(R.drawable.ic_todo);
//        else if(tab.getText().equals("Doing"))
//            mImageView.setImageResource(R.drawable.ic_doing);
//        else
//            mImageView.setImageResource(R.drawable.ic_done);
//    }

    private class TaskPagerAdapter extends FragmentStateAdapter {

        public TaskPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
            super(fragmentActivity);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getItemCount() {
            return mFragmentList.size();
        }
    }
}