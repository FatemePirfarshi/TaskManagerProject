package com.example.taskmanagerproject.controller.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.telephony.TelephonyScanManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.taskmanagerproject.R;
import com.example.taskmanagerproject.controller.activities.TaskPagerActivity;
import com.example.taskmanagerproject.model.User;
import com.example.taskmanagerproject.repository.TaskDBRepository;
import com.example.taskmanagerproject.repository.UserDBRepository;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class UserListFragment extends Fragment {

    private RecyclerView mRecyclerView;

    private UserDBRepository mRepository;
    private TaskDBRepository mTaskDBRepository;
    private List<User> mUserList = new ArrayList<>();
    private UserAdapter mUserAdapter;
    private UUID mUserId;

    public UserListFragment() {
        // Required empty public constructor
    }

    public static UserListFragment newInstance() {
        UserListFragment fragment = new UserListFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mRepository = UserDBRepository.getInstance(getActivity());
        mTaskDBRepository = TaskDBRepository.getInstance(getActivity(),0);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_list, container, false);
        findViews(view);
        initViews();
        return view;
    }

    private void initViews() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        updateUI();
    }

    private void updateUI() {
        mUserList = mRepository.getUsers();
        int[] result = mRepository.setUserTaskNumber();
        for (int i = 0; i < result.length; i++) {
            mUserList.get(i).setTaskNumber(result[i]);
        }
        if (mUserAdapter == null) {
            mUserAdapter = new UserAdapter(mUserList);
            mRecyclerView.setAdapter(mUserAdapter);
        } else {
            mUserAdapter.setUsers(mUserList);
            mUserAdapter.notifyDataSetChanged();
        }
    }

    private void findViews(View view) {
        mRecyclerView = view.findViewById(R.id.recycler_view_user_list);
    }

    class UserHolder extends RecyclerView.ViewHolder{

        private TextView mUserName;
        private TextView mNumberOfTask;
        private TextView mDate;
        private ImageButton mDelete;

        private User mUser;

        public UserHolder(@NonNull View itemView) {
            super(itemView);
            findViews(itemView);
            mDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mRepository.deleteUser(mUser);
                    updateUI();
                }
            });
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mTaskDBRepository.setLists(mUser.getId());
                    TaskPagerActivity.start(getActivity(), 0, mUser.getUserId());
                    getActivity().finish();
                }
            });
        }

        private void findViews(@NonNull View itemView) {
            mUserName = itemView.findViewById(R.id.txtview_username);
            mNumberOfTask = itemView.findViewById(R.id.txtview_task_number);
            mDelete = itemView.findViewById(R.id.btn_delete);
            mDate = itemView.findViewById(R.id.txtview_signup_date);
        }

        public void bindUser(User user){
            mUser = user;
            mUserName.setText(user.getUserName());
            mNumberOfTask.setText(user.getTaskNumber()+" Task");
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy-HH:mm:SS");
            mDate.setText(simpleDateFormat.format(user.getDate()));
        }
    }

    private class UserAdapter extends RecyclerView.Adapter<UserHolder>{

        private List<User> mUsers;

        public List<User> getUsers() {
            return mUsers;
        }

        public void setUsers(List<User> users) {
            mUsers = users;
        }

        public UserAdapter(List<User> users){
            mUsers = users;
        }

        @NonNull
        @Override
        public UserHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new UserHolder(LayoutInflater
                    .from(getActivity())
                    .inflate(R.layout.item_user_list, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull UserHolder holder, int position) {
            holder.bindUser(mUsers.get(position));
        }

        @Override
        public int getItemCount() {
            return mUsers.size();
        }
    }
}