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
import com.rumofuture.nemolite.app.contract.NemoSignUpContract;
import com.rumofuture.nemolite.model.entity.User;

import cn.bmob.v3.exception.BmobException;

public class NemoSignUpFragment extends Fragment implements NemoSignUpContract.View {

    private NemoSignUpContract.Presenter mPresenter;

    private EditText mNameView;
    private EditText mMobilePhoneNumberView;
    private EditText mPasswordView;
    private EditText mSMSCodeView;

    private TextView mSMSCodeRequestView;

    private NemoProgressBarFragment mProgressBar;

    public NemoSignUpFragment() {

    }

    public static NemoSignUpFragment newInstance() {
        return new NemoSignUpFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mProgressBar = NemoProgressBarFragment.newInstance(getString(R.string.prompt_signing_up));
        View view = inflater.inflate(R.layout.fragment_nemo_sign_up, container, false);

        mNameView = view.findViewById(R.id.author_name_view);
        mMobilePhoneNumberView = view.findViewById(R.id.user_mobile_phone_number_view);
        mPasswordView = view.findViewById(R.id.user_password_view);
        mSMSCodeView = view.findViewById(R.id.sms_code_view);

        mSMSCodeRequestView = view.findViewById(R.id.sms_code_request_view);
        mSMSCodeRequestView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.requestSMSCode(mMobilePhoneNumberView.getText().toString());
            }
        });

        Button mobilePhoneNumberSignUpButton = view.findViewById(R.id.sign_up_button);
        mobilePhoneNumberSignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.signUp(
                        mNameView.getText().toString(),
                        mMobilePhoneNumberView.getText().toString(),
                        mPasswordView.getText().toString(),
                        mSMSCodeView.getText().toString()
                );
            }
        });

        return view;
    }

    @Override
    public void setPresenter(NemoSignUpContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showNameError(int stringId) {
        new AlertDialog.Builder(getActivity())
                .setTitle(R.string.prompt_sign_up_failed)
                .setMessage(getString(stringId))
                .setCancelable(true)
                .setPositiveButton(R.string.prompt_ok, null)
                .show();
    }

    @Override
    public void showMobilePhoneNumberError(int stringId) {
        new AlertDialog.Builder(getActivity())
                .setTitle(R.string.prompt_sign_up_failed)
                .setMessage(getString(stringId))
                .setCancelable(true)
                .setPositiveButton(R.string.prompt_ok, null)
                .show();
    }

    @Override
    public void showPasswordError(int stringId) {
        new AlertDialog.Builder(getActivity())
                .setTitle(R.string.prompt_sign_up_failed)
                .setMessage(getString(stringId))
                .setCancelable(true)
                .setPositiveButton(R.string.prompt_ok, null)
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
    public void showSignUpSuccess(User user) {
        Toast.makeText(getActivity(), R.string.prompt_sign_up_success, Toast.LENGTH_LONG).show();
        getActivity().finish();
    }

    @Override
    public void showSignUpFailed(BmobException e) {
        Toast.makeText(getActivity(), R.string.prompt_sign_up_failed, Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean isActive() {
        return isAdded();
    }

    @Override
    public void showProgressBar(final boolean show) {
        if (show) {
            mProgressBar.show(getFragmentManager(), null);
        } else {
            mProgressBar.dismiss();
        }
    }
}
