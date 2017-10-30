package com.rumofuture.nemolite.model.source;

import android.support.annotation.NonNull;

import com.rumofuture.nemolite.model.entity.User;

import cn.bmob.v3.datatype.BmobFile;

public class UserRepository implements UserDataSource {

    private static UserRepository sInstance;
    private final UserDataSource mUserLocalDataSource;
    private final UserDataSource mUserRemoteDataSource;

    public static UserRepository getInstance(
            @NonNull UserDataSource userLocalDataSource,
            @NonNull UserDataSource userRemoteDataSource
    ) {
        if (null == sInstance) {
            sInstance = new UserRepository(userLocalDataSource, userRemoteDataSource);
        }
        return sInstance;
    }

    private UserRepository(
            @NonNull UserDataSource userLocalDataSource,
            @NonNull UserDataSource userRemoteDataSource
    ) {
        mUserLocalDataSource = userLocalDataSource;
        mUserRemoteDataSource = userRemoteDataSource;
    }

    @Override
    public void logIn(User user, UserLogInCallback callback) {
        mUserRemoteDataSource.logIn(user, callback);
    }

    @Override
    public void signUp(User user, UserSignUpCallback callback) {
        mUserRemoteDataSource.signUp(user, callback);
    }

    @Override
    public void updateUserAvatar(BmobFile newAvatar, UserImageUpdateCallback callback) {
        mUserRemoteDataSource.updateUserAvatar(newAvatar, callback);
    }

    @Override
    public void updateUserPortrait(BmobFile newPortrait, UserImageUpdateCallback callback) {
        mUserRemoteDataSource.updateUserPortrait(newPortrait, callback);
    }

    @Override
    public void updateUserInfo(User user, UserInfoUpdateCallback callback) {
        mUserRemoteDataSource.updateUserInfo(user, callback);
    }

    @Override
    public void getAuthorList(int pageCode, UserListGetCallback callback) {
        mUserRemoteDataSource.getAuthorList(pageCode, callback);
    }
}
