package com.rumofuture.nemolite.presenter;

import android.support.annotation.NonNull;

import com.rumofuture.nemolite.app.contract.NemoMainDiscoverContract;
import com.rumofuture.nemolite.model.entity.User;
import com.rumofuture.nemolite.model.source.UserDataSource;

import java.util.List;

import cn.bmob.v3.exception.BmobException;

public class NemoMainDiscoverPresenter implements NemoMainDiscoverContract.Presenter, UserDataSource.UserListGetCallback {

    private NemoMainDiscoverContract.View mView;
    private UserDataSource mUserRepository;

    public NemoMainDiscoverPresenter(
            @NonNull NemoMainDiscoverContract.View view,
            @NonNull UserDataSource userRepository
    ) {
        mView = view;
        mUserRepository = userRepository;
    }

    @Override
    public void start() {

    }

    @Override
    public void getAuthorList(int pageCode) {
        if (0 == pageCode) {
            mView.showProgressBar(true);
        }
        mUserRepository.getAuthorList(pageCode, this);
    }

    @Override
    public void onUserListGetSuccess(List<User> authorList) {
        if (mView.isActive()) {
            mView.showAuthorListGetSuccess(authorList);
            mView.showProgressBar(false);
        }
    }

    @Override
    public void onUserListGetFailed(BmobException e) {
        if (mView.isActive()) {
            mView.showAuthorListGetFailed(e);
            mView.showProgressBar(false);
        }
    }
}
