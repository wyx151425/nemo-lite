package com.rumofuture.nemolite.app.contract;

import com.rumofuture.nemolite.app.NemoPresenter;
import com.rumofuture.nemolite.app.NemoView;
import com.rumofuture.nemolite.model.entity.User;

import cn.bmob.v3.exception.BmobException;

public interface NemoLogInContract {

    interface View extends NemoView<NemoLogInContract.Presenter> {
        void showProgressBar(boolean show);

        void showMobilePhoneNumberError(Integer stringId);
        void showPasswordError(Integer stringId);

        void showLogInSuccess(User user);
        void showLogInFailed(BmobException e);

        boolean isActive();
    }

    interface Presenter extends NemoPresenter {
        void logIn(String mobilePhoneNumber, String password);
    }
}
