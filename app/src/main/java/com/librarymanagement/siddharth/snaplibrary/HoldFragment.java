package com.librarymanagement.siddharth.snaplibrary;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.librarymanagement.siddharth.snaplibrary.helper.CallGetBooksOnHold;
import com.librarymanagement.siddharth.snaplibrary.helper.CallGetSearchedBooks;
import com.librarymanagement.siddharth.snaplibrary.helper.Constants;
import com.librarymanagement.siddharth.snaplibrary.helper.RequestClass;
import com.librarymanagement.siddharth.snaplibrary.helper.SharedData;

import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class HoldFragment extends Fragment {

    public static List<PatronBookItem> holdListItems = new ArrayList<>();
    RecyclerView recyclerView;
    public static TextView patron_no_books_on_hold;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         View view =inflater.inflate(R.layout.fragment_hold, container, false);
         recyclerView = (RecyclerView)view.findViewById(R.id.hold_fragment_recyclerView);
         RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
         recyclerView.setLayoutManager(layoutManager);

        patron_no_books_on_hold = (TextView) view.findViewById(R.id.patron_no_books_on_hold);
        if(patron_no_books_on_hold != null)
            patron_no_books_on_hold.setVisibility(View.GONE);

        //Get list from Server
        try {
            JSONObject jsonObject = new JSONObject();
            HashMap<String, Object> params = new HashMap<String, Object>();

            String[] userDetails = SharedData.getUserDetails();

            jsonObject.put("patronId", Integer.valueOf(userDetails[0]));
            jsonObject.put("email", new JSONObject().put("createdBy", userDetails[1]));

            params.put(Constants.REQUEST_JSON, jsonObject);
            params.put(Constants.ACTION, Constants.ACTION_GET_BOOKS_ON_HOLD);
            params.put(Constants.ACTIVITY, this.getActivity());
            params.put(Constants.FRAGMENT, this);
            params.put(Constants.VIEW, this.getView());
            params.put(Constants.CONTEXT, this.getContext());
            params.put("recyclerView",recyclerView);

            RequestClass.startRequestQueue();
            new CallGetBooksOnHold().process(params);
        }
        catch (Exception e) {

            e.printStackTrace();
        }

         getActivity().setTitle("Books on Hold");
         return view;
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
