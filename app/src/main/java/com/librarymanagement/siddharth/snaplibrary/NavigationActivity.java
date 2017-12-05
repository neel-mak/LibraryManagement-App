package com.librarymanagement.siddharth.snaplibrary;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.librarymanagement.siddharth.snaplibrary.helper.LogHelper;
import com.librarymanagement.siddharth.snaplibrary.helper.SharedData;

import org.w3c.dom.Text;

public class NavigationActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    protected DrawerLayout drawer;
    TextView emailInDrawer ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
      setSupportActionBar(toolbar);




        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer,toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.SharedData.readFromSharedInitial(this);

        emailInDrawer = (TextView) findViewById(R.id.nav_email);
        String userDetails[] = SharedData.getUserDetails();
        if(emailInDrawer != null) {
            LogHelper.logMessage("nav_email", emailInDrawer.toString());
            emailInDrawer.setText(userDetails[1]);
        }
        getMenuInflater().inflate(R.menu.navigation, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        switch (id){
            case R.id.nav_home:
                for (Fragment fr: getSupportFragmentManager ().getFragments()){
                    getSupportFragmentManager().beginTransaction().remove(fr).commitNowAllowingStateLoss();
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.patron_main_container, new PatronSearchFragment()).addToBackStack(null).commit();
                break;
            case R.id.nav_cart:
                getSupportFragmentManager().beginTransaction().replace(R.id.patron_main_container, new CartFragment()).addToBackStack(null).commit();
                break;
            case R.id.nav_your_books:
                getSupportFragmentManager().beginTransaction().replace(R.id.patron_main_container, new ReturnFragment()).addToBackStack(null).commit();
                break;
            case R.id.nav_test_screen:
                getSupportFragmentManager().beginTransaction().replace(R.id.patron_main_container, new TestingAssistanceFragment()).addToBackStack(null).commit();
                break;
            case R.id.nav_signout:
                for(Fragment fr : getSupportFragmentManager().getFragments()){
                    getSupportFragmentManager().beginTransaction().remove(fr).commitNowAllowingStateLoss();
                }

                SharedData.clearUserData(this);

                Intent i;
                i = new Intent(this,MainActivity.class);
                this.startActivity(i);
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
