package com.librarymanagement.siddharth.snaplibrary;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.librarymanagement.siddharth.snaplibrary.helper.CallGetBooks;
import com.librarymanagement.siddharth.snaplibrary.helper.Constants;
import com.librarymanagement.siddharth.snaplibrary.helper.RequestClass;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by NEEL on 02-12-2017.
 */

public class ListFragment extends Fragment {
    private RecyclerView.Adapter adapter;
    private List<BookItem> bookItemList = new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)  {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_list, container, false);
        RecyclerView recyclerView=(RecyclerView)view.findViewById(R.id.recyclerView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        //Progress bar code
        Spinner spinner = (Spinner)view.findViewById(R.id.Search_spinner);
        ArrayAdapter<CharSequence> adp;
        adp = ArrayAdapter.createFromResource(getContext(), R.array.search_factors,android.R.layout.simple_spinner_item);
        adp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adp);


        //Get list from Server
         try {

             RequestClass.startRequestQueue();
             HashMap<String, Object> params = new HashMap<String, Object>();
             params.put(Constants.REQUEST_JSON, null);
             params.put(Constants.ACTION, Constants.ACTION_GET_BOOKS);
             params.put(Constants.ACTIVITY, this.getActivity());
             params.put(Constants.FRAGMENT, this);
             params.put(Constants.VIEW, this.getView());
             params.put(Constants.CONTEXT, this.getContext());

             new CallGetBooks().proccessGetBooks(params);
         }
         catch (Exception e) {

            e.printStackTrace();
         }

        for(int i=0;i<10;i++)
        {
            BookItem bookItem = new BookItem(
                    "Transedence","APJ Abdul Kalam",
                    "EDM","50","Available"
            );
            bookItemList.add(bookItem);
        }
        adapter = new ListAdapter(bookItemList);
        recyclerView.setAdapter(adapter);

        Button button = (Button)view.findViewById(R.id.list_fragment_add_btn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        return view;
    }


}
