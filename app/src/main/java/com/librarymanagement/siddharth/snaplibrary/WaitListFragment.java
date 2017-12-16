package com.librarymanagement.siddharth.snaplibrary;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.librarymanagement.siddharth.snaplibrary.helper.CallGetBooksOnHold;
import com.librarymanagement.siddharth.snaplibrary.helper.CallGetWaitlistedBooks;
import com.librarymanagement.siddharth.snaplibrary.helper.Constants;
import com.librarymanagement.siddharth.snaplibrary.helper.RequestClass;
import com.librarymanagement.siddharth.snaplibrary.helper.SharedData;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class WaitListFragment extends Fragment {

    public static List<PatronBookItem> waitListItemArrayList = new ArrayList<PatronBookItem>();
    RecyclerView recyclerView;
    public static TextView patron_no_waitlist_books_found;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_wait_list, container, false);
        recyclerView = (RecyclerView)view.findViewById(R.id.waitlist_recyclerView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        patron_no_waitlist_books_found = (TextView) view.findViewById(R.id.patron_no_waitlist_books_found);
        if(patron_no_waitlist_books_found != null)
            patron_no_waitlist_books_found.setVisibility(View.GONE);

        //Get list from Server
        try {
            JSONObject jsonObject = new JSONObject();
            HashMap<String, Object> params = new HashMap<String, Object>();

            String[] userDetails = SharedData.getUserDetails();

            jsonObject.put("patronId", Integer.valueOf(userDetails[0]));
            jsonObject.put("email", userDetails[1]);

            params.put(Constants.REQUEST_JSON, jsonObject);
            params.put(Constants.ACTION, Constants.ACTION_GET_BOOKS_ON_WAITLIST);
            params.put(Constants.ACTIVITY, this.getActivity());
            params.put(Constants.FRAGMENT, this);
            params.put(Constants.VIEW, this.getView());
            params.put(Constants.CONTEXT, this.getContext());
            params.put("recyclerView",recyclerView);

            RequestClass.startRequestQueue();
            new CallGetWaitlistedBooks().process(params);
        }
        catch (Exception e) {

            e.printStackTrace();
        }

        getActivity().setTitle("Wait Listed Books");
        return view;
    }

}
