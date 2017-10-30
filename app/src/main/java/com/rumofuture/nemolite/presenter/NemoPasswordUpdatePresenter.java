package com.rumofuture.nemolite.presenter;

import android.os.Handler;
import android.text.TextUtils;

import com.rumofuture.nemolite.R;
import com.rumofuture.nemolite.app.contract.NemoPasswordUpdateContract;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.bmob.v3.BmobSMS;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.UpdateListener;

public class NemoPasswordUpdatePresenter implements NemoPasswordUpdateContract.Presenter {

    private NemoPasswordUpdateContract.View mView;

    private Handler mHandler;
    private int requestedTime;

    public NemoPasswordUpdatePresenter(NemoPasswordUpdateContract.View view) {
        mView = view;
        start();
    }

    @Override
    public void start() {
        requestedTime = 59;
        mHandler = new Handler();
    }

    @Override
    public void requestSMSCode(String mobilePhoneNumber) {

        if (TextUtils.isEmpty(mobilePhoneNumber)) {
            mView.showMobilePhoneNumberError(R.string.prompt_field_required);
            return;
        } else if (!isMobilePhoneNumberValid(mobilePhoneNumber)) {
            mView.showMobilePhoneNumberError(R.string.prompt_invalid_user_mobile_phone_number);
            return;
        }

        BmobSMS.requestSMSCode(mobilePhoneNumber, "Nemo", new QueryListener<Integer>() {
            @Override
            public void done(Integer smsId, BmobException e) {
                if (e == null) {
                    mHandler.postDelayed(runnable, 1000);
                    if (mView.isActive())
                        mView.showRequestSMSCodeSuccess(smsId);
                } else {
                    if (mView.isActive())
                        mView.showRequestSMSCodeFailed(e);
                }
            }
        });
    }

    @Override
    public void modifyPassword(String newPassword, String smsCode) {

        if (TextUtils.isEmpty(newPassword)) {
            mView.showPasswordError(R.string.prompt_field_required);
            return;
        }

        if (!isPasswordValid(newPassword)) {
            mView.showPasswordError(R.string.prompt_incorrect_user_password);
            return;
        }

        mView.showProgressView(true);

        BmobUser.resetPasswordBySMSCode(smsCode, newPassword, new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    if (mView.isActive()) {
                        mView.showProgressView(false);
                        mView.showUserPasswordModifySuccess();
                    }
                } else {
                    if (mView.isActive()) {
                        mView.showProgressView(false);
                        mView.showUserPasswordModifyFailed(e);
                    }
                }
            }
        });
    }

    /**
     * 此方法用于验证用户输入的手机号格式是否正确
     *
     * @param mobilePhoneNumber 用户输入的手机号
     * @return 验证结果
     */
    private boolean isMobilePhoneNumberValid(String mobilePhoneNumber) {
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNumber = pattern.matcher(mobilePhoneNumber);
        return isNumber.matches() && (11 == mobilePhoneNumber.length());
    }

    /**
     * 此方法用于验证用户输入的密码格式是否正确
     *
     * @param password 用户输入的密码
     * @return 验证结果
     */
    private boolean isPasswordValid(String password) {
        return password.length() >= 6;
    }

    //新建一个线程，实现计时功能
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (requestedTime > 0) {
                requestedTime--;
                mView.showSMSCodeRequestTime(requestedTime + "s");
                mHandler.postDelayed(this, 1000);
            } else {
                requestedTime = 59;
                mView.showSMSCodeRequestTimeOut("重新获取");
                mHandler.removeCallbacks(runnable);
            }
        }
    };
}
