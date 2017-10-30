package com.rumofuture.nemolite.presenter;

import android.text.TextUtils;

import com.rumofuture.nemolite.R;
import com.rumofuture.nemolite.app.contract.MyPasswordUpdateContract;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

public class MyPasswordUpdatePresenter implements MyPasswordUpdateContract.Presenter {

    private MyPasswordUpdateContract.View mView;

    public MyPasswordUpdatePresenter(MyPasswordUpdateContract.View view) {
        mView = view;
    }

    @Override
    public void start() {

    }

    @Override
    public void updatePassword(String oldPassword, final String newPassword) {

        if (TextUtils.isEmpty(oldPassword)) {
            if (mView.isActive())
                mView.showPasswordError(R.string.prompt_old_user_password_required);
            return;
        }

        if (!isPasswordValid(oldPassword)) {
            if (mView.isActive())
                mView.showPasswordError(R.string.prompt_invalid_old_user_password);
            return;
        }

        if (TextUtils.isEmpty(newPassword)) {
            if (mView.isActive())
                mView.showPasswordError(R.string.prompt_new_user_password_required);
            return;
        }

        if (!isPasswordValid(newPassword)) {
            if (mView.isActive())
                mView.showPasswordError(R.string.prompt_invalid_new_user_password);
            return;
        }

        BmobUser.updateCurrentUserPassword(oldPassword, newPassword, new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (null == e) {
                    if (mView.isActive()) {
                        mView.showUserPasswordUpdateSuccess();
                    }
                } else {
                    if (mView.isActive()) {
                        mView.showUserPasswordUpdateFailed(e);
                    }
                }
            }
        });
    }

    private boolean isPasswordValid(String password) {
        return password.length() >= 6;
    }
}
