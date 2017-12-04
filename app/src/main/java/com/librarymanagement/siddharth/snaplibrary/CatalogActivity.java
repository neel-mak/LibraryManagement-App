package com.librarymanagement.siddharth.snaplibrary;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.librarymanagement.siddharth.snaplibrary.helper.LogHelper;

public class CatalogActivity extends AppCompatActivity {

    public static int bookId = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalog);

        /*Siddharth Code */ /*Fragment fr = new AddFragment();
        Fragment fr = new UpdateDeleteFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.catalog_fragment, fr).addToBackStack(null).commit();
        */
        ListFragment listFragment=new ListFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.place_holder,listFragment);
        fragmentTransaction.commit();

    }
}
