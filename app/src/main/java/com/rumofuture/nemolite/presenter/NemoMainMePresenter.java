package com.rumofuture.nemolite.presenter;

import android.support.annotation.NonNull;

import com.rumofuture.nemolite.app.contract.NemoMainMeContract;
import com.rumofuture.nemolite.model.entity.User;
import com.rumofuture.nemolite.model.source.UserDataSource;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;

public class NemoMainMePresenter implements NemoMainMeContract.Presenter, UserDataSource.UserInfoUpdateCallback {

    private UserDataSource mUserRepository;

    public NemoMainMePresenter(
            @NonNull UserDataSource userRepository
    ) {
        mUserRepository = userRepository;
    }

    @Override
    public void start() {

    }

    @Override
    public void getAuthorization() {
        User user = BmobUser.getCurrentUser(User.class);
        user.setAuthorize(true);
        mUserRepository.updateUserInfo(user, this);
    }

    @Override
    public void onUserInfoUpdateSuccess() {

    }

    @Override
    public void onUserInfoUpdateFailed(BmobException e) {
        BmobUser.getCurrentUser(User.class).setAuthorize(false);
    }
}
