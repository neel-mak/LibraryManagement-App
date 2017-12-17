package com.librarymanagement.siddharth.snaplibrary;


import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import com.librarymanagement.siddharth.snaplibrary.helper.CallGetCurrentTime;
import com.librarymanagement.siddharth.snaplibrary.helper.Constants;
import com.librarymanagement.siddharth.snaplibrary.helper.LogHelper;
import com.librarymanagement.siddharth.snaplibrary.helper.RequestClass;
import com.librarymanagement.siddharth.snaplibrary.helper.SharedData;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import static java.util.Calendar.AM;


public class TestingAssistanceFragment extends Fragment {

    Calendar calendar = Calendar.getInstance();
    public static TextView currentDateTime;
    Button dateButton;
    Button timeButton;
    Button doneButton;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_testing_assistance, container, false);
        currentDateTime = (TextView)view.findViewById(R.id.current_date_time);
        dateButton = (Button)view.findViewById(R.id.testing_date_btn);
        timeButton = (Button)view.findViewById(R.id.testing_time_btn);
        doneButton = (Button)view.findViewById(R.id.testing_done_btn);
        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    attemptUpdateTime();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });
        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateDate();

            }
        });

        timeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateTime();
            }
        });

        try {
            JSONObject jsonObject = new JSONObject();

            HashMap<String, Object> params = new HashMap<String, Object>();
            params.put(Constants.REQUEST_JSON, jsonObject);
            params.put(Constants.ACTION, Constants.ACTION_GET_CURRENT_TIME);
            params.put(Constants.ACTIVITY, this.getActivity());
            params.put(Constants.FRAGMENT, this);
            params.put(Constants.VIEW, this.getView());
            params.put(Constants.CONTEXT, this.getContext());

            RequestClass.startRequestQueue();
            new CallGetCurrentTime().process(params);
        }catch (JSONException e){
            e.printStackTrace();
        }
        getActivity().setTitle("Testing Assistance");
        return view;
    }

    private void updateDate(){
        new DatePickerDialog(getContext(),d,calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    private void updateTime(){
        new TimePickerDialog(getContext(),t,calendar.get(Calendar.HOUR_OF_DAY),calendar.get(Calendar.MINUTE),true).show();
    }

    DatePickerDialog.OnDateSetListener d = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            calendar.set(Calendar.YEAR,year);
            calendar.set(Calendar.MONTH,month);
            calendar.set(Calendar.DAY_OF_MONTH,dayOfMonth);
        }
    };

    TimePickerDialog.OnTimeSetListener t = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            calendar.set(Calendar.HOUR_OF_DAY,hourOfDay);
            calendar.set(Calendar.MINUTE,minute);
        }
    };

    private void attemptUpdateTime() throws ParseException {

        try {
            JSONObject jsonObject = new JSONObject();

            HashMap<String, Object> params = new HashMap<String, Object>();
            params.put(Constants.REQUEST_JSON, jsonObject);
            params.put(Constants.ACTION, Constants.ACTION_SET_NEW_TIME);
            params.put(Constants.ACTIVITY, this.getActivity());
            params.put(Constants.FRAGMENT, this);
            params.put(Constants.VIEW, this.getView());
            params.put(Constants.CONTEXT, this.getContext());
            params.put("calendar", calendar);

            RequestClass.startRequestQueue();
            new CallGetCurrentTime().process(params);
        }catch (JSONException e){
            e.printStackTrace();
        }

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        if(activity != null && activity.getCurrentFocus() != null && activity.getCurrentFocus().getWindowToken() != null) {
            final InputMethodManager inputManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), false ? InputMethodManager.SHOW_FORCED : InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

}
