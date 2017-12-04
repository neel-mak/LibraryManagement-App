package com.librarymanagement.siddharth.snaplibrary;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

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

//    @Override
//    public void onBackPressed(){
//        FragmentManager fm = getSupportFragmentManager();
//        if (fm.getBackStackEntryCount() > 1) {
//            Log.i("MainActivity", "popping backstack");
//            fm.popBackStack();
//        } else {
//            Log.i("MainActivity", "nothing on backstack, calling super");
//            super.onBackPressed();
//        }
//    }
}
