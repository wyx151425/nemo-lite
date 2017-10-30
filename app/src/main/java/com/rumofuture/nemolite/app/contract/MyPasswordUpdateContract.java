package com.rumofuture.nemolite.app.contract;

import com.rumofuture.nemolite.app.NemoPresenter;
import com.rumofuture.nemolite.app.NemoView;

import cn.bmob.v3.exception.BmobException;

public interface MyPasswordUpdateContract {

    interface View extends NemoView<MyPasswordUpdateContract.Presenter> {
        void showUserPasswordUpdateSuccess();
        void showUserPasswordUpdateFailed(BmobException e);

        void showPasswordError(int stringId);

        boolean isActive();
    }

    interface Presenter extends NemoPresenter {
        void updatePassword(String oldPassword, String newPassword);
    }
}
