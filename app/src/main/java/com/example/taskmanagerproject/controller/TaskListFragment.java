package com.example.taskmanagerproject.controller;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.taskmanagerproject.R;
import com.example.taskmanagerproject.model.Task;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class TaskListFragment extends Fragment {

    public static final String ARGS_TASK_LIST = "argsTaskList";
    private RecyclerView mRecyclerView;
    private static List<Task> mTaskList = new ArrayList<>();

    public TaskListFragment(List<Task> tasks) {
        mTaskList = tasks;
        // Required empty public constructor
    }

    public List<Task> getTaskList() {
        return mTaskList;
    }

    public static TaskListFragment newInstance(List<Task> tasks) {
        TaskListFragment fragment = new TaskListFragment(tasks);
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
        View view = inflater.inflate(R.layout.fragment_task_list, container, false);
        findViews(view);
        initViews();
        return view;
    }

    private void findViews(View view) {
        mRecyclerView = view.findViewById(R.id.recycler_view_task_list);
    }

    private void initViews() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mRecyclerView.setAdapter(new TaskAdapter(mTaskList));
    }

    class TaskHolder extends RecyclerView.ViewHolder {

        private TextView mTitle;
        private TextView mDate;
        private TextView mStartTitle;

        private Task mTask;

        public TaskHolder(@NonNull View itemView) {
            super(itemView);
            mTitle = itemView.findViewById(R.id.txtview_title);
            mDate = itemView.findViewById(R.id.txtview_date);
            mStartTitle = itemView.findViewById(R.id.task_title_start);
        }

        public void bindTask(Task task) {
            mTask = task;

            mTitle.setText(task.getTitle());
            mDate.setText(task.getDate().toString());
            mStartTitle.setText(task.getTitle().charAt(0));
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
}