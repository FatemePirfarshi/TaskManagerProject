package com.example.taskmanagerproject.controller.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.fragment.app.DialogFragment;

import com.example.taskmanagerproject.R;
import com.example.taskmanagerproject.controller.activities.TaskPagerActivity;
import com.example.taskmanagerproject.model.Task;
import com.example.taskmanagerproject.repository.TaskDBRepository;
import com.example.taskmanagerproject.utils.PictureUtils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class AddTaskFragment extends DialogFragment {

    public static final int REQUEST_CODE_IMAGE_CAPTURE = 2;
    private static final String TAG = "AddTaskFragment";
    public static final int REQUEST_CODE_DATE_PiCKER = 0;
    public static final int REQUEST_CODE_TIME_PICKER = 1;

    public static final String FRAGMENT_TAG_DATE_PICKER = "datePicker";
    public static final String FRAGMENT_TAG_TIME_PICKER = "timePicker";
    public static final String ARGS_LIST_POSITOIN = "listPositoin";

    public static final String EXTRA_NEW_TASK_position =
            "com.example.taskmanagerproject.AddTaskFragment_extra_new_task_position";
    public static final String KEY_USER_SELECTED_DATE = "userSelectedDate";
    public static final String KEY_USER_SELECTED_TIME = "userSelectedTime";
    public static final String ARGS_CURRENT_USER = "currentUser";
    public static final String AUTHORITY = "com.example.taskmanagerproject.fileProvider";

    private EditText mEditTextTitle;
    private EditText mEditTextDescription;
    private Button mButtonDate;
    private Button mButtonTime;
    private CheckBox mCheckBoxDone;

    private ImageView mImageViewPhoto;
    private Button mButtonTakePhoto;
    private Button mButtonChoosePhoto;

    private LinearLayout mRootLinearLayout;

    private TaskDBRepository mRepository;
    private List<Task> mCurrentList;
    private int mCurrentPosition;
    private long userId;
    private Task mTask = new Task();

    private File mPhotoFile;

    public Task getTask() {
        return mTask;
    }

    private Date userSelectedDate;
    private Long userSelectedTime;

    public AddTaskFragment() {
        // Required empty public constructor
    }

    public static AddTaskFragment newInstance(int position, long userId) {
        AddTaskFragment fragment = new AddTaskFragment();
        Bundle args = new Bundle();
        args.putInt(ARGS_LIST_POSITOIN, position);
        args.putLong(ARGS_CURRENT_USER, userId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCurrentPosition = getArguments().getInt(ARGS_LIST_POSITOIN);
        mRepository = TaskDBRepository.getInstance(getActivity(), mCurrentPosition);
        mCurrentList = mRepository.getListWithPosition(mCurrentPosition);
        userId = getArguments().getLong(ARGS_CURRENT_USER);
        mPhotoFile = mRepository.getPhotoFile(mTask);
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

                        if (mEditTextTitle.getText().toString().trim().isEmpty())
                            Toast.makeText(getActivity(),
                                    "Title field can't be blank!!", Toast.LENGTH_SHORT).show();
                        else {
                            mTask.setTitle(mEditTextTitle.getText().toString());
                            mTask.setDiscription(mEditTextDescription.getText().toString());
                            mTask.setDone(mCheckBoxDone.isChecked());
                            mTask.setPosition(mCurrentPosition);

                            mTask.setUserCreatorId(userId);
                            mRepository.insertTask(mTask);

                            Log.d(TAG, "onClick: mUserId" + mTask.getUserCreatorId());
                            mRepository.updateLists(mTask);

                            Intent intent = new Intent();
                            intent.putExtra(EXTRA_NEW_TASK_position, mTask.getPosition());

                            getTargetFragment().onActivityResult(
                                    TaskPagerActivity.REQUEST_CODE_ADD_TASK, Activity.RESULT_OK, intent);
                            dismiss();
                        }
                    }
                })
                .setNegativeButton(android.R.string.cancel, null)
                .create();
    }

    private void findViews(View view) {
        mEditTextTitle = view.findViewById(R.id.edittxt_title);
        mEditTextDescription = view.findViewById(R.id.edittxt_description);
        mButtonDate = view.findViewById(R.id.btn_date);
        mButtonTime = view.findViewById(R.id.btn_time);
        mCheckBoxDone = view.findViewById(R.id.checkBox_done);
        mRootLinearLayout = view.findViewById(R.id.root_linear_layout);
        mImageViewPhoto = view.findViewById(R.id.imgview_photo);
        mButtonTakePhoto = view.findViewById(R.id.btn_take_photo);
        mButtonChoosePhoto = view.findViewById(R.id.btn_choose_photo);
    }

    private void initViews() {
        mTask.setTitle(mEditTextTitle.getText().toString());
        mTask.setDiscription(mEditTextDescription.getText().toString());
        mTask.setDone(mCheckBoxDone.isChecked());

        mButtonDate.setText(new SimpleDateFormat("yyyy.MM.dd").format(mTask.getDate()));
        mButtonTime.setText(new SimpleDateFormat("HH:mm:ss").format(mTask.getDate()));
    }

    private void setListeners() {
        mButtonDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerFragment datePickerFragment = DatePickerFragment.newInstance(mTask.getDate());

                datePickerFragment.setTargetFragment(
                        AddTaskFragment.this, REQUEST_CODE_DATE_PiCKER);

                datePickerFragment.show(getActivity().getSupportFragmentManager(),
                        FRAGMENT_TAG_DATE_PICKER);
            }
        });

        mButtonTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerFragment timePickerFragment = TimePickerFragment.newInstance(mTask.getDate());

                timePickerFragment.setTargetFragment(
                        AddTaskFragment.this, REQUEST_CODE_TIME_PICKER);

                timePickerFragment.show(getActivity().getSupportFragmentManager(),
                        FRAGMENT_TAG_TIME_PICKER);
            }
        });

        mButtonTakePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                takePictureIntent();
            }
        });

        mButtonChoosePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //todo
            }
        });
    }

    private void takePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        try {
            if (mPhotoFile != null && takePictureIntent
                    .resolveActivity(getActivity().getPackageManager()) != null) {

                Uri photoURI = generateUriForPhotoFile();

                grantWriteUriToAllResolvedActivities(takePictureIntent, photoURI);

                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_CODE_IMAGE_CAPTURE);
            }
        } catch (ActivityNotFoundException e) {
            Log.e(TAG, e.getMessage(), e);
        }
    }

    private void grantWriteUriToAllResolvedActivities(Intent takePictureIntent, Uri photoURI) {
        List<ResolveInfo> activities = getActivity().getPackageManager()
                .queryIntentActivities(
                        takePictureIntent,
                        PackageManager.MATCH_DEFAULT_ONLY);
        for (ResolveInfo activity : activities) {
            getActivity().grantUriPermission(
                    activity.activityInfo.packageName,
                    photoURI,
                    Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        }
    }

    private Uri generateUriForPhotoFile() {
        return FileProvider.getUriForFile(
                getContext(),
                AUTHORITY,
                mPhotoFile);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if (resultCode != Activity.RESULT_OK || data == null)
            return;

        if (requestCode == REQUEST_CODE_DATE_PiCKER) {
            userSelectedDate =
                    (Date) data.getSerializableExtra(DatePickerFragment.USER_SELECTED_DATE);
            updateTaskDate(userSelectedDate);

        }else if (requestCode == REQUEST_CODE_TIME_PICKER) {
            userSelectedTime =
                    data.getLongExtra(TimePickerFragment.USER_SELECTED_TIME, 0);
            updateTaskTime(userSelectedTime);

        }else if (requestCode == REQUEST_CODE_IMAGE_CAPTURE) {
            Uri photoUri = generateUriForPhotoFile();
            getActivity().revokeUriPermission(photoUri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            updatePhotoView();
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        userSelectedDate = mTask.getDate();
        userSelectedTime = mTask.getTime();
        outState.putSerializable(KEY_USER_SELECTED_DATE, userSelectedDate);
        outState.putLong(KEY_USER_SELECTED_TIME, userSelectedTime);
    }

    public void updateTaskDate(Date userSelectedDate) {
        mTask.setDate(userSelectedDate);
        mButtonDate.setText(new SimpleDateFormat("yyyy.MM.dd").format(mTask.getDate()));
    }

    public void updateTaskTime(Long userSelectedTime) {
        mTask.getDate().setTime(userSelectedTime);
        mButtonTime.setText(new SimpleDateFormat("HH:mm:ss").format(mTask.getDate()));
    }

    private void updatePhotoView() {
        if (mPhotoFile == null || !mPhotoFile.exists())
            return;
        Bitmap bitmap = PictureUtils.getScaledBitmap(mPhotoFile.getAbsolutePath(), getActivity());
        mTask.setPhotoPath(mPhotoFile.getAbsolutePath());
        mImageViewPhoto.setImageBitmap(bitmap);
    }
}