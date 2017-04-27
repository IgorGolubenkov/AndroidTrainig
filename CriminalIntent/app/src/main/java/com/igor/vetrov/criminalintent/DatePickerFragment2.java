package com.igor.vetrov.criminalintent;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DatePickerFragment2 extends DialogFragment {

    public static final String EXTRA_DATE = "com.igor.vetrov.criminalintent.crimne_date";
    public static final int DATE_REQUEST_CODE = 20;

    private static final String ARG_DATE = "date";
    private DatePicker mDatePicker;
    private Button mOk;

    public static DatePickerFragment2 newInstance(Date date) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_DATE, date);

        DatePickerFragment2 fragment = new DatePickerFragment2();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final Date date = (Date) getArguments().getSerializable(ARG_DATE);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        View v = inflater.inflate(R.layout.dialog_date, container, false);
        mDatePicker = (DatePicker) v.findViewById(R.id.dialog_date_date_picker);
        mDatePicker.init(year, month, day, null);
        mDatePicker.setOnClickListener(v1 -> {
                    int year2 = mDatePicker.getYear();
                    int month2 = mDatePicker.getMonth();
                    int day2 = mDatePicker.getDayOfMonth();
                    if (year != year2 || month != month2|| day != day2) {
                        mOk.setText(R.string.crime_date_changed);
                    }
                }
        );

        mOk = (Button)v.findViewById(R.id.dialog_date_ok);
        mOk.setText(R.string.crime_date_not_changed);
        mOk.setOnClickListener(v12 -> {
            int year1 = mDatePicker.getYear();
            int month1 = mDatePicker.getMonth();
            int day1 = mDatePicker.getDayOfMonth();
            Date date2 = new GregorianCalendar(year1, month1, day1, hour, minute).getTime();
            if (year == year1 & month == month1 & day == day1) {
                startActSendResult(Activity.RESULT_CANCELED, date2);
            } else {
                startActSendResult(Activity.RESULT_OK, date2);
            }
        });
        return v;
    }

    private void startActSendResult(int code, Date date) {
        Intent intent = new Intent(getActivity(), CrimePagerActivity.class);
        if (code == Activity.RESULT_CANCELED) {
            Toast.makeText(getActivity(), "Дата не изменилась", Toast.LENGTH_SHORT).show();
        } else if (code == Activity.RESULT_OK) {
            intent.putExtra(EXTRA_DATE, date);
            Toast.makeText(getActivity(), String.format("Дата изменена на: %s", date), Toast.LENGTH_SHORT).show();
        }
        getTargetFragment().onActivityResult(DATE_REQUEST_CODE, code, intent);
        startActivityForResult(intent, code);
    }
}
