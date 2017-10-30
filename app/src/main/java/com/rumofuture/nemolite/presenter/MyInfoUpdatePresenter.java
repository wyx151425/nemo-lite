package com.rumofuture.nemolite.presenter;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

import com.rumofuture.nemolite.app.contract.MyInfoUpdateContract;
import com.rumofuture.nemolite.app.manager.ImageChooseManager;
import com.rumofuture.nemolite.model.entity.User;
import com.rumofuture.nemolite.model.source.UserDataSource;
import com.rumofuture.nemolite.view.fragment.MyInfoEditFragment;
import com.smile.filechoose.api.ChosenImage;

import java.io.File;

import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;

public class MyInfoUpdatePresenter implements MyInfoUpdateContract.Presenter, UserDataSource.UserInfoUpdateCallback, UserDataSource.UserImageUpdateCallback {

    private static final int NO_REQUEST_CODE = 0;
    private static final int AVATAR_UPDATE_REQUEST_CODE = 1;
    private static final int PORTRAIT_UPDATE_REQUEST_CODE = 2;

    private MyInfoUpdateContract.View mView;
    private UserDataSource mUserRepository;

    private ImageChooseManager mImageChooseManager;
    private ChosenImage mAvatarImage;
    private ChosenImage mPortraitImage;

    private int requestCode = NO_REQUEST_CODE;

    public MyInfoUpdatePresenter(
            @NonNull MyInfoUpdateContract.View view,
            @NonNull UserDataSource userRepository
    ) {
        mView = view;
        mUserRepository = userRepository;
    }

    @Override
    public void start() {
        mImageChooseManager = new ImageChooseManager((Fragment) mView, this);
    }

    @Override
    public void updateUserAvatar() {
        start();
        requestCode = AVATAR_UPDATE_REQUEST_CODE;
        chooseImage();
    }

    @Override
    public void updateUserPortrait() {
        start();
        requestCode = PORTRAIT_UPDATE_REQUEST_CODE;
        chooseImage();
    }

    @Override
    public void updateUserInfo(User user) {
        mView.showProgressBar(true);
        mUserRepository.updateUserInfo(user, this);
    }

    @Override
    public void onUserInfoUpdateSuccess() {
        if (mView.isActive()) {
            mView.showUserInfoUpdateSuccess();
            mView.showProgressBar(false);
        }
    }

    @Override
    public void onUserInfoUpdateFailed(BmobException e) {
        if (mView.isActive()) {
            mView.showUserInfoUpdateFailed(e);
            mView.showProgressBar(false);
        }
    }

    @Override
    public void chooseImage() {
        mImageChooseManager.chooseImage();
    }

    @Override
    public void releaseImageChooseManager() {
        start();
        if (AVATAR_UPDATE_REQUEST_CODE == requestCode) {
            mAvatarImage = null;
        } else if (PORTRAIT_UPDATE_REQUEST_CODE == requestCode) {
            mPortraitImage = null;
        }
        requestCode = NO_REQUEST_CODE;
    }

    @Override
    public void submitChoice(int requestCode, Intent data) {
        mImageChooseManager.submitChoice(requestCode, data);
    }

    @Override
    public void setChosenImage(final ChosenImage chosenImage) {
        ((MyInfoEditFragment) mView).getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (AVATAR_UPDATE_REQUEST_CODE == requestCode) {
                    mAvatarImage = chosenImage;
                    if (mAvatarImage != null) {
                        mView.showProgressBar(true);
                        mUserRepository.updateUserAvatar(
                                new BmobFile(new File(mAvatarImage.getFilePathOriginal())),
                                MyInfoUpdatePresenter.this
                        );
                    }
                } else if (PORTRAIT_UPDATE_REQUEST_CODE == requestCode) {
                    mPortraitImage = chosenImage;
                    if (mPortraitImage != null) {
                        mView.showProgressBar(true);
                        mUserRepository.updateUserPortrait(
                                new BmobFile(new File(mPortraitImage.getFilePathOriginal())),
                                MyInfoUpdatePresenter.this
                        );
                    }
                }
            }
        });
    }

    @Override
    public void onUserImageUpdateSuccess(BmobFile image) {
        if (AVATAR_UPDATE_REQUEST_CODE == requestCode) {
            mView.showUserAvatarUpdateSuccess(image);
        } else if (PORTRAIT_UPDATE_REQUEST_CODE == requestCode) {
            mView.showUserPortraitUpdateSuccess(image);
        }
        mView.showProgressBar(false);
        releaseImageChooseManager();
    }

    @Override
    public void onUserImageUpdateFailed(BmobException e) {
        if (AVATAR_UPDATE_REQUEST_CODE == requestCode) {
            mView.showUserAvatarUpdateFailed(e);
        } else if (PORTRAIT_UPDATE_REQUEST_CODE == requestCode) {
            mView.showUserPortraitUpdateFailed(e);
        }
        mView.showProgressBar(false);
        releaseImageChooseManager();
    }
}
