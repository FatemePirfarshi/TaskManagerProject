package com.example.taskmanagerproject.controller.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taskmanagerproject.R;
import com.example.taskmanagerproject.controller.activities.TaskPagerActivity;
import com.example.taskmanagerproject.model.Task;
import com.example.taskmanagerproject.repository.TaskDBRepository;

import java.util.ArrayList;
import java.util.List;

public class TaskListFragment extends Fragment {

    public static final int REQUEST_CODE_SHOW_DETAIL = 0;
    public static final String FRAGMENT_TAG_SHOW_DETAIL = "ShowDetail";
    public static final String TASK_LIST_POSITION = "taskListPosition";
    public static final int REQUEST_CODE_DELETE_ALL = 1;
    public static final String FRAGMENT_TAG_DELETE_ALL = "deleteAll";

    private RecyclerView mRecyclerView;
    private ImageView mImageView;
    private LinearLayout mLayoutDefault;

    private int mPosition;

    private List<Task> mTaskList = new ArrayList<>();
    private TaskDBRepository mRepository;
    private TaskAdapter mTaskAdapter;

    public TaskListFragment() {
        // Required empty public constructor
    }

    public TaskListFragment(List<Task> tasks) {
        mTaskList = tasks;
    }

    public static TaskListFragment newInstance(List<Task> tasks, int position) {
        TaskListFragment fragment = new TaskListFragment(tasks);
        Bundle args = new Bundle();
        args.putInt(TASK_LIST_POSITION, position);
        //  mTaskList = tasks;

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
        if (mTaskList.size() == 0)
            mPosition = getArguments().getInt(TASK_LIST_POSITION);

        mRepository = TaskDBRepository.getInstance(getActivity(), mPosition);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_task_list, container, false);
        findViews(view);
        initViews();

        return view;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_task_list, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_log_out:
                //todo
                return true;
            case R.id.item_delete_all:

                DeleteAllFragment deleteAllFragment = DeleteAllFragment.newInstance(mPosition);
                deleteAllFragment.setTargetFragment(
                        TaskListFragment.this, REQUEST_CODE_DELETE_ALL);
                deleteAllFragment.show(
                        getActivity().getSupportFragmentManager(), FRAGMENT_TAG_DELETE_ALL);
                updateUI();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void findViews(View view) {
        mRecyclerView = view.findViewById(R.id.recycler_view_task_list);
        mImageView = view.findViewById(R.id.imgview_task_list);
        mLayoutDefault = view.findViewById(R.id.default_pic_layout);
    }

    private void initViews() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mImageView.setImageResource(mRepository.checkImageState(mPosition));
        updateUI();
        setEmptyList();
    }

    private void updateUI() {
//  mTaskAdapter.notifyItemInserted(mTaskList.size() - 1);
        if (mTaskAdapter == null) {
            mTaskAdapter = new TaskAdapter(mTaskList);
            mRecyclerView.setAdapter(mTaskAdapter);
        } else {
            mTaskAdapter.setTasks(mTaskList);
            mTaskAdapter.notifyDataSetChanged();
        }
        setEmptyList();
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    private void updateUI(Task task, int position) {
        mRepository.updateTask(task);
        mTaskList = mRepository.getListWithPosition(position);
        mTaskAdapter.setTasks(mTaskList);
        mTaskAdapter.notifyDataSetChanged();
        setEmptyList();
    }

    private void setEmptyList() {

        if (mTaskList.size() != 0)
            mLayoutDefault.setVisibility(View.GONE);
        else if (mLayoutDefault != null) {
            mImageView.setImageResource(mRepository.checkImageState(mPosition));
            mLayoutDefault.setVisibility(View.VISIBLE);
        }
    }

    class TaskHolder extends RecyclerView.ViewHolder {

        private TextView mTitle;
        private TextView mDate;
        private TextView mStartTitle;
        private RelativeLayout mRootLayout;

        private Task mTask;

        public TaskHolder(@NonNull View itemView) {
            super(itemView);
            mTitle = itemView.findViewById(R.id.txtview_title);
            mDate = itemView.findViewById(R.id.txtview_date);
            mStartTitle = itemView.findViewById(R.id.task_title_start);
            mRootLayout = itemView.findViewById(R.id.row_root_layout);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //  mPosition = mRepository.getCurrentPosition();
                    ShowDetailFragment showDetailFragment =
                            ShowDetailFragment.newInstance(mTask.getId(), mPosition);

                    showDetailFragment.setTargetFragment(
                            TaskListFragment.this, REQUEST_CODE_SHOW_DETAIL);

                    showDetailFragment.show(
                            getActivity().getSupportFragmentManager(), FRAGMENT_TAG_SHOW_DETAIL);
                }
            });
        }

        public void bindTask(Task task) {
            mTask = task;
            mTitle.setText(task.getTitle());
            mStartTitle.setText(Character.toString(task.getTitle().charAt(0)));
            mDate.setText(task.getDate().toString());
        }
    }

    public class TaskAdapter extends RecyclerView.Adapter<TaskHolder> {
        private List<Task> mTasks;

        public List<Task> getTasks() {
            return mTasks;
        }

        public void setTasks(List<Task> tasks) {
            mTasks = tasks;
        }

        public TaskAdapter(List<Task> tasks) {
            mTasks = tasks;
        }

        @NonNull
        @Override
        public TaskHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.
                    from(getActivity()).
                    inflate(R.layout.item_task_list, parent, false);
            TaskHolder taskHolder = new TaskHolder(view);
            return taskHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull TaskHolder holder, int position) {
            Task task = mTasks.get(position);
            holder.bindTask(task);
        }

        @Override
        public int getItemCount() {
            return mTasks.size();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_OK || data == null)
            return;

        if (requestCode == TaskPagerActivity.REQUEST_CODE_ADD_TASK) {
            mPosition = data.getIntExtra(AddTaskFragment.EXTRA_NEW_TASK_position, 0);
            mTaskList = mRepository.getListWithPosition(mPosition);
            updateUI();

        } else if (requestCode == REQUEST_CODE_SHOW_DETAIL) {
            int position;
            //  int position = data.getIntExtra(ShowDetailFragment.EXTRA_CURRENT_POSITION, 0);
            Task newTask = (Task) data.getSerializableExtra(ShowDetailFragment.EXTRA_NEW_TASK);

            if (data.getBooleanExtra(ShowDetailFragment.EXTRA_EDIT_TASK, false))
                position = newTask.getPosition();
            else
                position = data.getIntExtra(ShowDetailFragment.EXTRA_TASK_EDITED_CURRENT_POSITION, 0);

            if (newTask != null) {
                updateUI(newTask, position);
                mPosition = position;
            } else {
                int deletedTaskPosition = data.getIntExtra(
                        ShowDetailFragment.EXTRA_TASK_DELETED_CURRENT_POSITION, 0);
                mTaskList = mRepository.getListWithPosition(deletedTaskPosition);
                int imageRes = mRepository.checkImageState(deletedTaskPosition);
                mImageView.setImageResource(imageRes);
                updateUI();
            }
        }
    }
}