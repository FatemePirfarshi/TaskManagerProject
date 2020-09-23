package com.example.taskmanagerproject.controller;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.taskmanagerproject.R;
import com.example.taskmanagerproject.model.Task;
import com.example.taskmanagerproject.repository.TaskRepository;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class TaskListFragment extends Fragment {

    public static final int REQUEST_CODE_SHOW_DETAIL = 0;
    public static final String FRAGMENT_TAG_SHOW_DETAIL = "ShowDetail";

    private RecyclerView mRecyclerView;
    private ImageView mImageView;
    private LinearLayout mLayoutDefault;

    private int mPosition = 0;

    private static List<Task> mTaskList = new ArrayList<>();
    private TaskRepository mRepository;
    private TaskAdapter mTaskAdapter;

    public TaskListFragment(List<Task> tasks, int position) {
        mTaskList = tasks;
        if (tasks.size() == 0)
            mPosition = position;
        mRepository = TaskRepository.getInstance(mPosition);
        // Required empty public constructor
    }

    public List<Task> getTaskList() {
        return mTaskList;
    }

    public static TaskListFragment newInstance(List<Task> tasks, int position) {
        TaskListFragment fragment = new TaskListFragment(tasks, position);
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
        mImageView = view.findViewById(R.id.imgview_task_list);
        mLayoutDefault = view.findViewById(R.id.default_pic_layout);
    }

    private void initViews() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        if (mTaskList.size() != 0)
            mLayoutDefault.setVisibility(View.GONE);

        mRecyclerView.setAdapter(new TaskAdapter(mTaskList));
        mTaskAdapter = new TaskAdapter(mTaskList);
        mTaskAdapter.notifyItemInserted(mTaskList.size());

        int imageRes = mRepository.checkImageState(mPosition);

        mImageView.setImageResource(imageRes);
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
                    ShowDetailFragment showDetailFragment =
                            ShowDetailFragment.newInstance(mTask.getId(), mPosition);

                    showDetailFragment.setTargetFragment(
                            TaskListFragment.this, REQUEST_CODE_SHOW_DETAIL);

                    showDetailFragment.show(
                            getActivity().getSupportFragmentManager(),FRAGMENT_TAG_SHOW_DETAIL);
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
}