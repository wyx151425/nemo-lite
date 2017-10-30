package com.rumofuture.nemolite.view.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.rumofuture.nemolite.R;
import com.rumofuture.nemolite.app.contract.NemoPasswordUpdateContract;

import cn.bmob.v3.exception.BmobException;

public class NemoPasswordUpdateFragment extends Fragment implements NemoPasswordUpdateContract.View {

    private NemoPasswordUpdateContract.Presenter mPresenter;

    private EditText mMobilePhoneNumberView;
    private EditText mPasswordView;
    private EditText mSMSCodeView;

    private TextView mSMSCodeRequestView;

    private NemoProgressBarFragment mProgressBar;

    public NemoPasswordUpdateFragment() {

    }

    public static NemoPasswordUpdateFragment newInstance() {
        return new NemoPasswordUpdateFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mProgressBar = NemoProgressBarFragment.newInstance(getString(R.string.prompt_updating));

        View view = inflater.inflate(R.layout.fragment_nemo_password_update, container, false);

        mMobilePhoneNumberView = (EditText) view.findViewById(R.id.user_mobile_phone_number_view);
        mPasswordView = (EditText) view.findViewById(R.id.new_password_view);
        mSMSCodeView = (EditText) view.findViewById(R.id.sms_code_view);

        mSMSCodeRequestView = (TextView) view.findViewById(R.id.sms_code_request_view);
        mSMSCodeRequestView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.requestSMSCode(mMobilePhoneNumberView.getText().toString());
            }
        });

        Button passwordModifyButton = (Button) view.findViewById(R.id.password_update_button);
        passwordModifyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.modifyPassword(
                        mPasswordView.getText().toString(),
                        mSMSCodeView.getText().toString()
                );
            }
        });

        return view;
    }

    @Override
    public void setPresenter(NemoPasswordUpdateContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showUserPasswordModifySuccess() {
        Toast.makeText(getActivity(), "修改成功", Toast.LENGTH_LONG).show();
    }

    @Override
    public void showUserPasswordModifyFailed(BmobException e) {
        Toast.makeText(getActivity(), "修改失败", Toast.LENGTH_LONG).show();
    }

    @Override
    public void showMobilePhoneNumberError(int stringId) {
        new AlertDialog.Builder(getActivity())
                .setTitle("验证码获取失败")
                .setMessage(getString(stringId))
                .setCancelable(true)
                .setPositiveButton("确定", null)
                .show();
    }

    @Override
    public void showPasswordError(int stringId) {
        new AlertDialog.Builder(getActivity())
                .setTitle("密码修改失败")
                .setMessage(getString(stringId))
                .setCancelable(true)
                .setPositiveButton("确定", null)
                .show();
    }

    @Override
    public void showRequestSMSCodeSuccess(Integer smsId) {
        Toast.makeText(getActivity(), "验证码已发送", Toast.LENGTH_LONG).show();
    }

    @Override
    public void showRequestSMSCodeFailed(BmobException e) {
        Toast.makeText(getActivity(), "验证码发送失败", Toast.LENGTH_LONG).show();
    }

    @Override
    public void showSMSCodeRequestTime(String time) {
        mSMSCodeRequestView.setText(time);
        mSMSCodeRequestView.setClickable(false);
    }

    @Override
    public void showSMSCodeRequestTimeOut(String message) {
        mSMSCodeRequestView.setText(message);
        mSMSCodeRequestView.setClickable(true);
    }

    @Override
    public boolean isActive() {
        return isAdded();
    }

    @Override
    public void showProgressView(boolean show) {
        if (show) {
            mProgressBar.show(getFragmentManager(), null);
        } else {
            mProgressBar.dismiss();
        }
    }
}
