package com.example.taskmanagerproject.controller.fragments;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taskmanagerproject.R;
import com.example.taskmanagerproject.controller.activities.LoginActivity;
import com.example.taskmanagerproject.controller.activities.TaskPagerActivity;
import com.example.taskmanagerproject.model.Task;
import com.example.taskmanagerproject.repository.TaskDBRepository;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
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

    public static TaskListFragment newInstance(int position) {
        TaskListFragment fragment = new TaskListFragment();
        Bundle args = new Bundle();
        args.putInt(TASK_LIST_POSITION, position);
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
        mTaskList = mRepository.getListWithPosition(mPosition);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_task_list, menu);
        MenuItem searchItem = menu.findItem(R.id.app_bar_search);

        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));

        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                mTaskAdapter.getFilter().filter(s);
                return false;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.item_log_out:
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                return true;

            case R.id.item_delete_all:

                DeleteAllFragment deleteAllFragment = DeleteAllFragment.newInstance(0);
                deleteAllFragment.setTargetFragment(
                        TaskListFragment.this, REQUEST_CODE_DELETE_ALL);
                DeleteAllFragment.newInstance(0).show(
                        getActivity().getSupportFragmentManager(), FRAGMENT_TAG_DELETE_ALL);

                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
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
        mRepository.updateList();
        mTaskList = mRepository.getListWithPosition(mPosition);
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

    private void updateUI(Task task, int position) {
        mRepository.updateTask(task);
        mRepository.updateList();
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

        private TextView mTitle, mDate, mStartTitle;
        private RelativeLayout mRootLayout;
        private ImageView mShare;

        private Task mTask;

        public TaskHolder(@NonNull View itemView) {
            super(itemView);
            findViews(itemView);

            mShare.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    shareReportIntent();
                }
            });

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

        private void findViews(@NonNull View itemView) {
            mTitle = itemView.findViewById(R.id.txtview_title);
            mDate = itemView.findViewById(R.id.txtview_date);
            mStartTitle = itemView.findViewById(R.id.task_title_start);
            mRootLayout = itemView.findViewById(R.id.row_root_layout);
            mShare = itemView.findViewById(R.id.imgview_share);
        }

        private String getReport(){

            String discriptionString = mTask.getDiscription().trim().isEmpty() ?
                    getString(R.string.task_without_discription) :
                    mTask.getDiscription();

            String stateString;
            switch (mPosition){
                case 0:
                    stateString = "TODO";
                    break;
                case 1:
                    stateString = "DOING";
                    break;
                default:
                    stateString = "DONE";
                    break;
            }

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy-HH:mm:SS");
            String dateString = simpleDateFormat.format(mTask.getDate());

            String report = getString(
                    R.string.task_report,
                    mTask.getTitle(),
                    discriptionString,
                    stateString,
                    dateString);

            return report;
        }

        private void shareReportIntent() {
            Intent sendIntent = new Intent(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, getReport());
            sendIntent.setType("text/plain");

            Intent shareIntent =
                    Intent.createChooser(sendIntent, getString(R.string.send_report));

            if(sendIntent.resolveActivity(getActivity().getPackageManager()) != null)
                startActivity(shareIntent);
        }

        public void bindTask(Task task) {
            mTask = task;
            mTitle.setText(task.getTitle());
            mStartTitle.setText(Character.toString(task.getTitle().charAt(0)));
            mDate.setText(task.getSimpleDate() + " " + task.getSimpleTime());
        }
    }

    public class TaskAdapter extends RecyclerView.Adapter<TaskHolder> implements Filterable {
        private List<Task> mTasks;
        private List<Task> mTasksFull;

        public List<Task> getTasks() {
            return mTasks;
        }

        public void setTasks(List<Task> tasks) {
            mTasks = tasks;
            mTasksFull = new ArrayList<>(tasks);
            notifyDataSetChanged();
        }

        public TaskAdapter(List<Task> tasks) {
            mTasks = tasks;
            mTasksFull = new ArrayList<>(tasks);
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

        @Override
        public Filter getFilter() {
            return tasksFilter;
        }

        private Filter tasksFilter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {

                List<Task> filteredList = new ArrayList<>();

                if (charSequence == null || charSequence.length() == 0)
                    filteredList.addAll(mTasksFull);
                else {
                    String filter = charSequence.toString().toLowerCase().trim();
                    for (Task task : mTasksFull) {

                        if (task.getTitle().toLowerCase().contains(filter) ||
                                task.getDiscription().toLowerCase().contains(filter) ||
                                task.getSimpleDate().toLowerCase().contains(filter) ||
                                task.getSimpleTime().toLowerCase().contains(filter))
                            filteredList.add(task);
                    }
                }
                FilterResults results = new FilterResults();
                results.values = filteredList;

                return results;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                if (mTasks != null)
                    mTasks.clear();
                else
                    mTasks = new ArrayList<>();

                if (filterResults != null)
                    mTasks.addAll((Collection<? extends Task>) filterResults.values);
                notifyDataSetChanged();
            }
        };
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