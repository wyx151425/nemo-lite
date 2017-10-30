package com.rumofuture.nemolite.model.source;

import com.rumofuture.nemolite.model.entity.User;

import java.util.List;

import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;

/**
 * Created by WangZhenqi on 2017/1/29.
 */

public interface UserDataSource {

    int PAGE_LIMIT = 32;

    void logIn(User user, UserLogInCallback callback);
    void signUp(User user, UserSignUpCallback callback);

    void updateUserAvatar(BmobFile newAvatar, UserImageUpdateCallback callback);
    void updateUserPortrait(BmobFile newPortrait, UserImageUpdateCallback callback);
    void updateUserInfo(User user, UserInfoUpdateCallback callback);

    void getAuthorList(int pageCode, UserListGetCallback callback);

    interface UserLogInCallback {
        void onUserLogInSuccess(User user);
        void onUserLogInFailed(BmobException e);
    }

    interface UserSignUpCallback {
        void onUserSignUpSuccess(User user);
        void onUserSignUpFailed(BmobException e);
    }

    interface UserInfoUpdateCallback {
        void onUserInfoUpdateSuccess();
        void onUserInfoUpdateFailed(BmobException e);
    }

    interface UserImageUpdateCallback {
        void onUserImageUpdateSuccess(BmobFile image);
        void onUserImageUpdateFailed(BmobException e);
    }

    interface UserListGetCallback {
        void onUserListGetSuccess(List<User> userList);
        void onUserListGetFailed(BmobException e);
    }

    interface TotalGetCallback {
        void onTotalGetSuccess(Integer total);
        void onTotalGetFailed(BmobException e);
    }
}
