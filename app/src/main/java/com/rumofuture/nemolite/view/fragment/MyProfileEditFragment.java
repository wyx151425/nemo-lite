package com.rumofuture.nemolite.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.rumofuture.nemolite.R;
import com.rumofuture.nemolite.app.contract.MyInfoUpdateContract;
import com.rumofuture.nemolite.model.entity.User;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;

import static android.app.Activity.RESULT_OK;

public class MyProfileEditFragment extends Fragment implements MyInfoUpdateContract.View {

    private static final String ARG_PROFILE = "com.rumofuture.nemolite.view.fragment.MyProfileEditFragment.profile";
    public static final String EXTRA_PROFILE = "com.rumofuture.nemolite.view.fragment.MyProfileEditFragment.profile";

    private MyInfoUpdateContract.Presenter mPresenter;

    private NemoProgressBarFragment mProgressBar;

    private EditText mProfileView;

    private User mUserToUpdate;
    private String mProfile;

    public MyProfileEditFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (null != getArguments()) {
            mProfile = (String) getArguments().getSerializable(ARG_PROFILE);
        }
        mUserToUpdate = new User();
        mUserToUpdate.setObjectId(BmobUser.getCurrentUser(User.class).getObjectId());
    }

    public static MyProfileEditFragment newInstance(String profile) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_PROFILE, profile);
        MyProfileEditFragment fragment = new MyProfileEditFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mProgressBar = NemoProgressBarFragment.newInstance(getString(R.string.prompt_updating));

        View view = inflater.inflate(R.layout.fragment_my_profile_edit, container, false);
        mProfileView = view.findViewById(R.id.my_profile_view);
        mProfileView.setText(mProfile);

        FloatingActionButton fab = getActivity().findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mUserToUpdate.setProfile(mProfileView.getText().toString());
                mPresenter.updateUserInfo(mUserToUpdate);
            }
        });
        return view;
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

    }

    @Override
    public void showUserAvatarUpdateFailed(BmobException e) {

    }

    @Override
    public void showUserPortraitUpdateSuccess(BmobFile portrait) {

    }

    @Override
    public void showUserPortraitUpdateFailed(BmobException e) {

    }

    @Override
    public void showUserInfoUpdateSuccess() {
        Intent intent = new Intent();
        intent.putExtra(EXTRA_PROFILE, mProfileView.getText().toString());
        getActivity().setResult(RESULT_OK, intent);
        getActivity().finish();
    }

    @Override
    public void showUserInfoUpdateFailed(BmobException e) {
        Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean isActive() {
        return isAdded();
    }
}
