package com.example.taskmanagerproject.controller.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.taskmanagerproject.R;
import com.example.taskmanagerproject.model.Task;
import com.example.taskmanagerproject.repository.TaskDBRepository;
import com.example.taskmanagerproject.utils.PictureUtils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class ShowDetailFragment extends DialogFragment {

    public static final int REQUEST_CODE_DATE_PiCKER = 0;
    public static final int REQUEST_CODE_TIME_PICKER = 1;

    public static final String EXTRA_EDIT_TASK =
            "com.example.taskmanagerproject.ShowDetailFragment_extra_edit_task";
    public static final String EXTRA_NEW_TASK =
            "com.example.taskmanagerproject.ShowDetailFragment_extra_new_task";
    public static final String EXTRA_TASK_DELETED_CURRENT_POSITION =
            "com.example.taskmanagerproject.taskDeletedCurrentPosition";
    public static final String EXTRA_TASK_EDITED_CURRENT_POSITION =
            "com.example.taskmanagerproject.taskEditedCurrentPosition";

    public static final String FRAGMENT_TAG_DATE_PICKER = "datePicker";
    public static final String FRAGMENT_TAG_TIME_PICKER = "timePicker";
    public static final String ARGS_TASK_ID = "argsTaskId";
    public static final String ARGS_TASK_POSITION = "argsTaskPosition";
    public static final int REQUEST_CODE_STATE = 2;
    public static final String FRAGMENT_TAG_CHANGED_STATE = "changedState";

    public static final String KEY_USER_SELECTED_DATE = "userSelectedDate";
    public static final String KEY_USER_SELECTED_TIME = "userSelectedTime";

    private EditText mEditTextTitle;
    private EditText mEditTextDescription;
    private Button mButtonDate;
    private Button mButtonTime;
    private CheckBox mCheckBoxDone;
    private ImageView mImageViewPhoto;

    private Task mTask;
    private List<Task> mTasks;
    private UUID mTaskId;
    private int mPosition;
    private TaskDBRepository mRepository;

    private Date userSelectedDate;
    private Long userSelectedTime;

    public ShowDetailFragment() {
        // Required empty public constructor
    }

    public static ShowDetailFragment newInstance(UUID id, int position) {
        ShowDetailFragment fragment = new ShowDetailFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARGS_TASK_ID, id);
        args.putInt(ARGS_TASK_POSITION, position);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mTaskId = (UUID) getArguments().getSerializable(ARGS_TASK_ID);
        mPosition = getArguments().getInt(ARGS_TASK_POSITION);
        mRepository = TaskDBRepository.getInstance(getActivity(), mPosition);
        mTask = mRepository.getTask(mTaskId);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
        View view = layoutInflater.inflate(R.layout.fragment_show_detail, null);

        findViews(view);

        if (savedInstanceState != null) {
            userSelectedDate = (Date) savedInstanceState.getSerializable(KEY_USER_SELECTED_DATE);
            userSelectedTime = savedInstanceState.getLong(KEY_USER_SELECTED_TIME);
            mTask.setDate(userSelectedDate);
            mTask.getDate().setTime(userSelectedTime);
        }

        initViews();
        setListeners();

        return new AlertDialog.Builder(getActivity())
                .setView(view)
                .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        mTask.setTitle(mEditTextTitle.getText().toString());
                        mTask.setDiscription(mEditTextDescription.getText().toString());
                        mTask.setDone(mCheckBoxDone.isChecked());
//                        mRepository.insertTask(mTask);
                        Intent intent = new Intent();
                        intent.putExtra(EXTRA_EDIT_TASK, true);
                        intent.putExtra(EXTRA_NEW_TASK, mTask);
                        getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, intent);
                        dismiss();
                    }
                })
                .setNegativeButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        mRepository.deleteTask(mTask);
                        Intent intent = new Intent();
                        intent.putExtra(EXTRA_TASK_DELETED_CURRENT_POSITION, mTask.getPosition());
                        getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, intent);
                        dismiss();
                    }
                })
                .setNeutralButton("STATES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ChangeStateFragment changeStateFragment =
                                ChangeStateFragment.newInstance(mTask.getPosition());

                        changeStateFragment.setTargetFragment(
                                ShowDetailFragment.this, REQUEST_CODE_STATE);

                        changeStateFragment.show(getActivity().getSupportFragmentManager()
                                , FRAGMENT_TAG_CHANGED_STATE);
                    }
                })
                .create();
    }

    private void findViews(View view) {
        mEditTextTitle = view.findViewById(R.id.edittxt_title);
        mEditTextDescription = view.findViewById(R.id.edittxt_description);
        mButtonDate = view.findViewById(R.id.btn_date);
        mButtonTime = view.findViewById(R.id.btn_time);
        mCheckBoxDone = view.findViewById(R.id.checkBox_done);
        mImageViewPhoto = view.findViewById(R.id.imgview_photo);
    }

    private void initViews() {
        mEditTextTitle.setText(mTask.getTitle());
        mEditTextDescription.setText(mTask.getDiscription());
        mCheckBoxDone.setChecked(mTask.isDone());

        setPhotoView();

        mButtonDate.setText(new SimpleDateFormat("yyyy.MM.dd").format(mTask.getDate()));
        mButtonTime.setText(new SimpleDateFormat("HH:mm:ss").format(mTask.getDate()));
    }

    private void setPhotoView() {
//        File photoFile = new File(mTask.getPhotoPath());
//        if (photoFile == null || !photoFile.exists())
//            return;
//
//        Bitmap bitmap = PictureUtils.getScaledBitmap(photoFile.getAbsolutePath(), getActivity());
//        mImageViewPhoto.setImageBitmap(bitmap);
        //        File mPhotoFile = mRepository.getPhotoFile(mTask);
//        if (mPhotoFile == null || !mPhotoFile.exists())
//            return;
//        Bitmap bitmap = PictureUtils.getScaledBitmap(mPhotoFile.getAbsolutePath(), getActivity());
//        mImageViewPhoto.setImageBitmap(bitmap);
    }

    private void setListeners() {
        mButtonDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerFragment datePickerFragment =
                        DatePickerFragment.newInstance(mTask.getDate());

                datePickerFragment.setTargetFragment(
                        ShowDetailFragment.this, REQUEST_CODE_DATE_PiCKER);

                datePickerFragment.show(getActivity().getSupportFragmentManager(),
                        FRAGMENT_TAG_DATE_PICKER);
            }
        });

        mButtonTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerFragment timePickerFragment =
                        TimePickerFragment.newInstance(mTask.getDate());

                timePickerFragment.setTargetFragment(
                        ShowDetailFragment.this, REQUEST_CODE_TIME_PICKER);

                timePickerFragment.show(getActivity().getSupportFragmentManager(),
                        FRAGMENT_TAG_TIME_PICKER);
            }
        });
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(KEY_USER_SELECTED_DATE, userSelectedDate);
        outState.putLong(KEY_USER_SELECTED_TIME, userSelectedTime);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if (resultCode != Activity.RESULT_OK || data == null)
            return;

        if (requestCode == REQUEST_CODE_DATE_PiCKER) {
            userSelectedDate =
                    (Date) data.getSerializableExtra(DatePickerFragment.USER_SELECTED_DATE);
            updateTaskDate(userSelectedDate);
        }

        if (requestCode == REQUEST_CODE_TIME_PICKER) {
            userSelectedTime =
                    data.getLongExtra(TimePickerFragment.USER_SELECTED_TIME, 0);
            updateTaskTime(userSelectedTime);
        }

        if (requestCode == REQUEST_CODE_STATE) {
            int newPosition =
                    data.getIntExtra(ChangeStateFragment.USER_SELECTED_POSITION, 0);

            //  mRepository.updateTask(mTask);
            Intent intent = new Intent();
            intent.putExtra(EXTRA_TASK_EDITED_CURRENT_POSITION, mTask.getPosition());
            mTask.setPosition(newPosition);
            intent.putExtra(EXTRA_NEW_TASK, mTask);
            getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, intent);
        }
    }

    public void updateTaskDate(Date userSelectedDate) {
        mTask.setDate(userSelectedDate);
        mButtonDate.setText(new SimpleDateFormat("yyyy.MM.dd").format(mTask.getDate()));
    }

    public void updateTaskTime(Long userSelectedTime) {
        mTask.getDate().setTime(userSelectedTime);
        mButtonTime.setText(new SimpleDateFormat("HH:mm:ss").format(mTask.getDate()));
    }
}