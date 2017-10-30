package com.rumofuture.nemolite.view.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.NumberPicker;

import com.rumofuture.nemolite.R;

public class NemoGenderPickerFragment extends DialogFragment {

    public static final String EXTRA_GENDER = "com.rumofuture.nemo.view.fragment.NemoDatePickerFragment.gender";
    private static final String ARG_GENDER = "com.rumofuture.nemo.view.fragment.NemoDatePickerFragment.gender";

    private NumberPicker mNumberPicker;

    public NemoGenderPickerFragment() {

    }

    public static NemoGenderPickerFragment newInstance(String gender) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_GENDER, gender);
        NemoGenderPickerFragment fragment = new NemoGenderPickerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        String gender = (String) getArguments().getSerializable(ARG_GENDER);

        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_my_gender_picker, null);
        mNumberPicker = view.findViewById(R.id.dialog_sex_picker);
        String[] genderArray = {"男", "女", "保密"};
        mNumberPicker.setDisplayedValues(genderArray);
        mNumberPicker.setMinValue(0);
        mNumberPicker.setMaxValue(genderArray.length - 1);
        int value = 0;
        for (int index = 0; index < genderArray.length; index++) {
            if (null != gender && gender.equals(genderArray[index])) {
                value = index;
            }
        }
        mNumberPicker.setValue(value);

        return new AlertDialog.Builder(getActivity())
                .setView(view)
                .setTitle(R.string.prompt_user_gender_pick)
                .setPositiveButton(R.string.prompt_ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int value = mNumberPicker.getValue();
                        String gender = null;
                        if (0 == value) {
                            gender = getString(R.string.prompt_user_sex_male);
                        } else if (1 == value) {
                            gender = getString(R.string.prompt_user_sex_female);
                        } else if (2 == value) {
                            gender = getString(R.string.prompt_user_sex_secrecy);
                        }
                        sendResult(Activity.RESULT_OK, gender);
                    }
                })
                .setNegativeButton(R.string.prompt_cancel, null)
                .create();
    }

    private void sendResult(int resultCode, String gender) {
        if (null == getTargetFragment()) {
            return;
        }
        Intent intent = new Intent();
        intent.putExtra(EXTRA_GENDER, gender);
        getTargetFragment()
                .onActivityResult(getTargetRequestCode(), resultCode, intent);
    }
}
