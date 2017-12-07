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

import com.librarymanagement.siddharth.snaplibrary.helper.SharedData;

import java.util.Calendar;

import static java.util.Calendar.AM;


public class TestingAssistanceFragment extends Fragment {

    Calendar calendar = Calendar.getInstance();
    private TextView date;
    private TextView time;
    Button dateButton;
    Button timeButton;
    Button doneButton;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_testing_assistance, container, false);
        date = (TextView)view.findViewById(R.id.Final_Date);
        time = (TextView)view.findViewById(R.id.Final_Time);
        dateButton = (Button)view.findViewById(R.id.testing_date_btn);
        timeButton = (Button)view.findViewById(R.id.testing_time_btn);
        doneButton = (Button)view.findViewById(R.id.testing_done_btn);
        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeFragment();
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
        updateText();
        getActivity().setTitle("Testing Assistance");
        return view;
    }


    private void updateText(){
        int d,m,y,h,min,am_pm;
        String ch;
        m=calendar.get(Calendar.MONTH);
        m+=1;
        d=calendar.get(Calendar.DAY_OF_MONTH);
        y=calendar.get(Calendar.YEAR);
        h=calendar.get(Calendar.HOUR_OF_DAY);
        min=calendar.get(Calendar.MINUTE);
        am_pm=calendar.get(Calendar.AM_PM);
        if(am_pm==0)
        {
            ch="AM";
        }
        else
        {
            ch="PM";
        }

        if(h>12){
            h-=12;
        }
        date.setText("Date : "+m+"/"+d+"/"+y);
        time.setText("Time : "+h+":"+min+" "+ch);
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
            updateText();
        }
    };

    TimePickerDialog.OnTimeSetListener t = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            calendar.set(Calendar.HOUR_OF_DAY,hourOfDay);
            calendar.set(Calendar.MINUTE,minute);
            updateText();
        }
    };

    private void changeFragment(){
        String userDetails[] = SharedData.getUserDetails();

        if("patron".equalsIgnoreCase(userDetails[3])){
            getFragmentManager().beginTransaction().replace(R.id.patron_main_container,new PatronSearchFragment()).addToBackStack(null).commit();
        }else {
            getFragmentManager().beginTransaction().replace(R.id.place_holder,new ListFragment()).addToBackStack(null).commit();
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
