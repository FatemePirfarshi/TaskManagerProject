package com.example.taskmanagerproject.controller.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.taskmanagerproject.R;
import com.example.taskmanagerproject.model.Task;
import com.example.taskmanagerproject.repository.TaskDBRepository;
import com.example.taskmanagerproject.utils.PictureUtils;
import com.squareup.picasso.Picasso;

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

    public static final String KEY_USER_SELECTED_DATE = "userSelectedDate";
    public static final String KEY_USER_SELECTED_TIME = "userSelectedTime";

    private EditText mEditTextTitle, mEditTextDescription;
    private Button mButtonDate, mButtonTime;
    private CheckBox mCheckBoxDone;
    private ImageView mImageViewPhoto;
    private RadioGroup mRadioGroupStates;
    private RadioButton mButtonTodo, mButtonDoing, mButtonDone;

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
                }).setNeutralButton("Cancle", null)
                .create();
    }

    private void findViews(View view) {
        mEditTextTitle = view.findViewById(R.id.edittxt_title);
        mEditTextDescription = view.findViewById(R.id.edittxt_description);
        mButtonDate = view.findViewById(R.id.btn_date);
        mButtonTime = view.findViewById(R.id.btn_time);
        mCheckBoxDone = view.findViewById(R.id.checkBox_done);
        mImageViewPhoto = view.findViewById(R.id.imgview_photo);
        mRadioGroupStates = view.findViewById(R.id.radiogp_states);
        mButtonTodo = view.findViewById(R.id.btn_todo);
        mButtonDoing = view.findViewById(R.id.btn_doing);
        mButtonDone = view.findViewById(R.id.btn_done);
    }

    private void initViews() {
        mEditTextTitle.setText(mTask.getTitle());
        mEditTextDescription.setText(mTask.getDiscription());
        mCheckBoxDone.setChecked(mTask.isDone());

        setPhotoView();

        mButtonDate.setText(new SimpleDateFormat("yyyy.MM.dd").format(mTask.getDate()));
        mButtonTime.setText(new SimpleDateFormat("HH:mm:ss").format(mTask.getDate()));

        switch (mPosition) {
            case 0:
                mButtonTodo.setChecked(true);
                break;
            case 1:
                mButtonDoing.setChecked(true);
                break;
            case 2:
                mButtonDone.setChecked(true);
                break;
        }
    }

    private void setPhotoView() {

        if (mTask.getPhotoPath() != null) {
//            File mPhotoFile = new File(mTask.getPhotoPath());
//            if (mPhotoFile == null || !mPhotoFile.exists())
//                return;
//            Bitmap bitmap = PictureUtils.getScaledBitmap(mPhotoFile.getAbsolutePath(), getActivity());
//            mImageViewPhoto.setImageBitmap(bitmap);
            Picasso.get()
                    .load(mTask.getPhotoPath())
                    .into(mImageViewPhoto);
            //mImageViewPhoto.setImageURI(Uri.fromFile(new File(mTask.getPhotoPath())));
        }
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

        mRadioGroupStates.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                switch (checkedId) {
                    case R.id.btn_todo:
                        mTask.setPosition(0);
                        break;
                    case R.id.btn_doing:
                        mTask.setPosition(1);
                        break;
                    case R.id.btn_done:
                        mTask.setPosition(2);
                        break;
                }
            }
        });
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        userSelectedDate = mTask.getDate();
        userSelectedTime = mTask.getTime();
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