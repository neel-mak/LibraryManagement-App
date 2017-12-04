package com.librarymanagement.siddharth.snaplibrary;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.librarymanagement.siddharth.snaplibrary.helper.LogHelper;
import com.librarymanagement.siddharth.snaplibrary.helper.SharedData;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LogHelper.logMessage("MainActivity: onCreate()", "going to read from shared preference.");

        SharedData.readFromSharedInitial(this.getApplicationContext());
        String[] userArray = SharedData.getUserDetails();

        LogHelper.logMessage("MainActivity: onCreate()", "userArray: " + userArray);

        if(userArray != null && userArray.length != 0 && !Boolean.parseBoolean(userArray[4])){
            Fragment fr;
            fr = new ConfirmationFragment();
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragment, fr).addToBackStack(null);
            fragmentTransaction.commit();

        }else if(userArray != null && userArray.length != 0 && "librarian".equalsIgnoreCase(userArray[3]) && Boolean.parseBoolean(userArray[4])){

            Intent i;
            i = new Intent(getApplicationContext(),CatalogActivity.class);
            startActivity(i);

        }else if(userArray != null && userArray.length != 0 && "patron".equalsIgnoreCase(userArray[3]) && Boolean.parseBoolean(userArray[4])){

            Intent i;
            i = new Intent(getApplicationContext(),PatronActivity.class);
            startActivity(i);

        }else {
            Fragment fr;
            fr = new LoginFragment();
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragment, fr).addToBackStack(null);
            fragmentTransaction.commit();
        }
    }


}
