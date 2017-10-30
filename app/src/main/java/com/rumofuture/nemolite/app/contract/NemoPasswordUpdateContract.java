package com.rumofuture.nemolite.app.contract;

import com.rumofuture.nemolite.app.NemoPresenter;
import com.rumofuture.nemolite.app.NemoView;

import cn.bmob.v3.exception.BmobException;

public interface NemoPasswordUpdateContract {

    interface View extends NemoView<NemoPasswordUpdateContract.Presenter> {
        void showProgressView(boolean show);

        void showUserPasswordModifySuccess();
        void showUserPasswordModifyFailed(BmobException e);

        void showMobilePhoneNumberError(int stringId);
        void showPasswordError(int stringId);

        void showRequestSMSCodeSuccess(Integer smsId);
        void showRequestSMSCodeFailed(BmobException e);

        void showSMSCodeRequestTime(String time);
        void showSMSCodeRequestTimeOut(String message);

        boolean isActive();
    }

    interface Presenter extends NemoPresenter {
        void requestSMSCode(String mobilePhoneNumber);
        void modifyPassword(String newPassword, String smsCode);
    }
}
