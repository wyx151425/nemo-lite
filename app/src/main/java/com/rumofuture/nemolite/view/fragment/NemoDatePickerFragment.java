package com.rumofuture.nemolite.view.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;

import com.rumofuture.nemolite.R;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

public class NemoDatePickerFragment extends DialogFragment {

    public static final String EXTRA_DATE_STRING = "com.rumofuture.nemolite.view.fragment.NemoDatePickerFragment.dateString";
    private static final String ARG_DATE_STRING = "com.rumofuture.nemolite.view.fragment.NemoDatePickerFragment.dateString";

    private Date mDate;
    private DateFormat mDateFormat;
    private DatePicker mDatePicker;

    public static NemoDatePickerFragment newInstance(String dateString) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_DATE_STRING, dateString);
        NemoDatePickerFragment fragment = new NemoDatePickerFragment();
        fragment.setArguments(args);
        return fragment;
    }


    public NemoDatePickerFragment() {

    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        String dateString = (String) getArguments().getSerializable(ARG_DATE_STRING);
        mDateFormat = new SimpleDateFormat(getString(R.string.format_date), Locale.SIMPLIFIED_CHINESE);
        if (dateString != null && !dateString.equals("")) {
            try {
                mDate = mDateFormat.parse(dateString);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_nemo_date_picker, null);

        mDatePicker = view.findViewById(R.id.dialog_date_picker);
        if (null != mDate) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(mDate);
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            mDatePicker.init(year, month, day, null);
        }

        return new AlertDialog.Builder(getActivity())
                .setView(view)
                .setPositiveButton(R.string.prompt_ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int year = mDatePicker.getYear();
                        int month = mDatePicker.getMonth();
                        int day = mDatePicker.getDayOfMonth();
                        Date date = new GregorianCalendar(year, month, day).getTime();
                        String dateString = mDateFormat.format(date);
                        sendResult(Activity.RESULT_OK, dateString);
                    }
                })
                .setNegativeButton(R.string.prompt_cancel, null)
                .create();
    }

    private void sendResult(int resultCode, String dateString) {
        if (getTargetFragment() == null) {
            return;
        }
        Intent intent = new Intent();
        intent.putExtra(EXTRA_DATE_STRING, dateString);
        getTargetFragment()
                .onActivityResult(getTargetRequestCode(), resultCode, intent);
    }
}
