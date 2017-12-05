package com.librarymanagement.siddharth.snaplibrary;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.librarymanagement.siddharth.snaplibrary.helper.CallGetBooks;
import com.librarymanagement.siddharth.snaplibrary.helper.CallGetSearchedBooks;
import com.librarymanagement.siddharth.snaplibrary.helper.Constants;
import com.librarymanagement.siddharth.snaplibrary.helper.RequestClass;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * Created by NEEL on 02-12-2017.
 */

public class PatronSearchFragment extends Fragment //implements AdapterView.OnItemSelectedListener
{

    public static Button searchBtn;
    public static EditText searchParams;
    public static Spinner spinner;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)  {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_patron_search, container, false);

        //Progress bar code
        spinner = (Spinner)view.findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adp;
        adp = ArrayAdapter.createFromResource(getContext(), R.array.search_factors,android.R.layout.simple_spinner_item);
        adp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adp);

        searchParams = (EditText)  view.findViewById(R.id.patron_search_fragment_search_text);
        searchBtn = (Button)view.findViewById(R.id.patron_search_fragment_search_btn);
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callSearch();
            }
        });
        getActivity().setTitle("Search Books");
        return view;
    }

    public void callSearch(){
        try {

            Bundle arguments = new Bundle();
            arguments.putString("spinner_value", spinner.getSelectedItem().toString());
            arguments.putString("search_text", searchParams.getText().toString());

            PatronListFragment patronListFragment = new PatronListFragment();
            patronListFragment.setArguments(arguments);

            PatronActivity currentPatronActivity = (PatronActivity)this.getActivity();
            android.support.v4.app.FragmentManager fm = currentPatronActivity.getSupportFragmentManager();
            android.support.v4.app.FragmentTransaction transaction = fm.beginTransaction();
            transaction.replace(R.id.patron_main_container,patronListFragment,"PATRON_LIST_FRAGMENT").addToBackStack(null);
            transaction.commit();
        }
        catch (Exception e) {

            e.printStackTrace();
        }
    }

//    @Override
//    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
//        String parent.getItemAtPosition(pos);
//    }
//
//    @Override
//    public void onNothingSelected(AdapterView<?> adapterView) {
//
//    }
}
