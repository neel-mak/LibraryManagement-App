package com.librarymanagement.siddharth.snaplibrary;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.librarymanagement.siddharth.snaplibrary.helper.CallISearchForUpdate;
import com.librarymanagement.siddharth.snaplibrary.helper.Constants;
import com.librarymanagement.siddharth.snaplibrary.helper.RequestClass;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class PatronActivity extends AppCompatActivity {

    public static int cartBookIds[];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patron);

        PatronSearchFragment fragment=new PatronSearchFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.patron_main_container,fragment).addToBackStack(null);
        fragmentTransaction.commit();


    }


}
