package com.rumofuture.nemolite.view.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.rumofuture.nemolite.R;
import com.rumofuture.nemolite.app.contract.MyInfoUpdateContract;
import com.rumofuture.nemolite.model.entity.User;
import com.rumofuture.nemolite.view.activity.MyProfileEditActivity;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;

public class MyInfoEditFragment extends Fragment implements MyInfoUpdateContract.View {

    private static final String DIALOG_GENDER = "DialogGender";
    private static final String DIALOG_BIRTHDAY = "DialogBirthday";

    private static final int REQUEST_IMAGE = 291;
    private static final int REQUEST_GENDER = 701;
    private static final int REQUEST_BIRTHDAY = 702;
    private static final int REQUEST_PROFILE = 703;

    private User mUserCurrent;
    private User mUserToUpdate;

    private ImageView mAvatarView;
    private TextView mNameView;
    private EditText mMottoView;
    private TextView mGenderView;
    private TextView mBirthdayView;
    private TextView mMobilePhoneNumberView;
    private TextView mProfileView;

    private NemoProgressBarFragment mProgressBar;

    private MyInfoUpdateContract.Presenter mPresenter;

    public MyInfoEditFragment() {

    }

    public static MyInfoEditFragment newInstance() {
        return new MyInfoEditFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mUserCurrent = BmobUser.getCurrentUser(User.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mProgressBar = NemoProgressBarFragment.newInstance(getString(R.string.prompt_updating));
        View view = inflater.inflate(R.layout.fragment_my_info_edit, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        mAvatarView = view.findViewById(R.id.author_avatar_view);
        if (null != mUserCurrent.getAvatar()) {
            Glide.with(getActivity()).load(mUserCurrent.getAvatar().getUrl()).into(mAvatarView);
        }

        mNameView = view.findViewById(R.id.author_name_view);
        mNameView.setText(mUserCurrent.getName());

        mMottoView = view.findViewById(R.id.author_motto_view);
        if (null != mUserCurrent.getMotto()) {
            mMottoView.setText(mUserCurrent.getMotto());
        }

        mMobilePhoneNumberView = view.findViewById(R.id.user_mobile_phone_number_view);
        mMobilePhoneNumberView.setText(mUserCurrent.getMobilePhoneNumber());

        mGenderView = view.findViewById(R.id.user_sex_view);
        if (null != mUserCurrent.getGender()) {
            mGenderView.setText(mUserCurrent.getGender());
        }
        mGenderView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NemoGenderPickerFragment sexPickerDialog = NemoGenderPickerFragment.newInstance(mGenderView.getText().toString().trim());
                sexPickerDialog.setTargetFragment(MyInfoEditFragment.this, REQUEST_GENDER);
                sexPickerDialog.show(getFragmentManager(), DIALOG_GENDER);
            }
        });

        mBirthdayView = view.findViewById(R.id.user_birthday_view);
        if (null != mUserCurrent.getBirthday()) {
            mBirthdayView.setText(mUserCurrent.getBirthday());
        }

        mBirthdayView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NemoDatePickerFragment datePickerDialog
                        = NemoDatePickerFragment.newInstance(mBirthdayView.getText().toString().trim());
                // 获取Fragment的操作不会失败，所以不需要进行空值检查
                datePickerDialog.setTargetFragment(MyInfoEditFragment.this, REQUEST_BIRTHDAY);
                datePickerDialog.show(getFragmentManager(), DIALOG_BIRTHDAY);
            }
        });

        mProfileView = view.findViewById(R.id.my_profile_view);
        if (null != mUserCurrent.getProfile()) {
            mProfileView.setText(mUserCurrent.getProfile());
        }

        mProfileView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 此过程直接启动了一个新的Activity，所以回调数据会返回到此Fragment所在的Activity
                MyProfileEditActivity.actionStart(
                        MyInfoEditFragment.this, mProfileView.getText().toString(), REQUEST_PROFILE);
            }
        });

        FloatingActionButton fab = getActivity().findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mUserToUpdate = new User();
                mUserToUpdate.setObjectId(mUserCurrent.getObjectId());
                mUserToUpdate.setMotto(mMottoView.getText().toString().trim());
                mUserToUpdate.setGender(mGenderView.getText().toString().trim());
                mUserToUpdate.setBirthday(mBirthdayView.getText().toString().trim());
                mPresenter.updateUserInfo(mUserToUpdate);
            }
        });
    }

    @Override
    public void setPresenter(MyInfoUpdateContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showProgressBar(boolean show) {
        if (show) {
            mProgressBar.show(getFragmentManager(), null);
        } else {
            mProgressBar.dismiss();
        }
    }

    @Override
    public void showUserAvatarUpdateSuccess(BmobFile avatar) {
        Toast.makeText(getActivity(), R.string.prompt_update_success, Toast.LENGTH_LONG).show();
        Glide.with(getActivity()).load(avatar.getUrl()).into(mAvatarView);
    }

    @Override
    public void showUserAvatarUpdateFailed(BmobException e) {
        Toast.makeText(getActivity(), R.string.prompt_update_failed, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showUserPortraitUpdateSuccess(BmobFile portrait) {
        Toast.makeText(getActivity(), R.string.prompt_update_success, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showUserPortraitUpdateFailed(BmobException e) {
        Toast.makeText(getActivity(), R.string.prompt_update_failed, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showUserInfoUpdateSuccess() {
        Toast.makeText(getActivity(), R.string.prompt_update_success, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showUserInfoUpdateFailed(BmobException e) {
        Toast.makeText(getActivity(), R.string.prompt_update_failed, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        if (REQUEST_IMAGE == requestCode) {
            mPresenter.submitChoice(requestCode, data);
        } else if (REQUEST_GENDER == requestCode) {
            String sex = (String) data.getSerializableExtra(NemoGenderPickerFragment.EXTRA_GENDER);
            mGenderView.setText(sex);
        } else if (REQUEST_BIRTHDAY == requestCode) {
            String dateString = (String) data.getSerializableExtra(NemoDatePickerFragment.EXTRA_DATE_STRING);
            mBirthdayView.setText(dateString);
        } else if (REQUEST_PROFILE == requestCode) {
            // 由于此过程Fragment通过自己直接启动了一个新的Activity
            // 所以另一个Activity销毁的时候直接调用了此Fragment的onActivityResult方法
            String profile = (String) data.getSerializableExtra(MyProfileEditFragment.EXTRA_PROFILE);
            mProfileView.setText(profile);
            Toast.makeText(getActivity(), R.string.prompt_edit_success, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public boolean isActive() {
        return isAdded();
    }

    public void updateUserAvatar() {
        mPresenter.updateUserAvatar();
    }

    public void updateUserPortrait() {
        mPresenter.updateUserPortrait();
    }
}
