package com.rumofuture.nemolite.view.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.rumofuture.nemolite.R;
import com.rumofuture.nemolite.app.contract.MyPasswordUpdateContract;

import cn.bmob.v3.exception.BmobException;

public class MyPasswordUpdateFragment extends Fragment implements MyPasswordUpdateContract.View {

    private MyPasswordUpdateContract.Presenter mPresenter;

    public MyPasswordUpdateFragment() {

    }

    public static MyPasswordUpdateFragment newInstance() {
        return new MyPasswordUpdateFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_my_password_update, container, false);

        final EditText oldPasswordView = view.findViewById(R.id.old_password_view);
        final EditText newPasswordView = view.findViewById(R.id.new_password_view);

        Button passwordUpdateButton = view.findViewById(R.id.password_update_button);
        passwordUpdateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.updatePassword(
                        oldPasswordView.getText().toString(),
                        newPasswordView.getText().toString()
                );
            }
        });

        return view;
    }

    @Override
    public void setPresenter(MyPasswordUpdateContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showUserPasswordUpdateSuccess() {
        Toast.makeText(getActivity(), R.string.prompt_update_success, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showUserPasswordUpdateFailed(BmobException e) {
        Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void showPasswordError(int stringId) {
        new AlertDialog.Builder(getActivity())
                .setTitle(R.string.prompt_update_failed)
                .setMessage(getString(stringId))
                .setCancelable(true)
                .setPositiveButton(R.string.prompt_ok, null)
                .show();
    }

    @Override
    public boolean isActive() {
        return isAdded();
    }
}
