package com.librarymanagement.siddharth.snaplibrary;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.librarymanagement.siddharth.snaplibrary.helper.CallGetBooks;
import com.librarymanagement.siddharth.snaplibrary.helper.CallGetSearchedBooks;
import com.librarymanagement.siddharth.snaplibrary.helper.Constants;
import com.librarymanagement.siddharth.snaplibrary.helper.LogHelper;
import com.librarymanagement.siddharth.snaplibrary.helper.RequestClass;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * Created by NEEL on 02-12-2017.
 */

public class ListFragment extends Fragment //implements AdapterView.OnItemSelectedListener
{
    public static RecyclerView.Adapter adapter;
    public static List<BookItem> bookItemList = new ArrayList<>();

    public static Button searchBtn;
    public static EditText searchParams;
    public static Spinner spinner;
    RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)  {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_list, container, false);
        recyclerView=(RecyclerView)view.findViewById(R.id.recyclerView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        //Progress bar code
        spinner = (Spinner)view.findViewById(R.id.Search_spinner);
        ArrayAdapter<CharSequence> adp;
        adp = ArrayAdapter.createFromResource(getContext(), R.array.search_factors,R.layout.spinner_item_style);
        adp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adp);

        searchParams = (EditText)  view.findViewById(R.id.list_fragment_search_text);
        searchBtn = (Button)view.findViewById(R.id.list_fragment_search_btn);
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callSearch();
            }
        });

        Button button = (Button)view.findViewById(R.id.list_fragment_add_btn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveToAddBookScreen();
            }
        });

        getActivity().setTitle("Home");
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
            params.put("recyclerView",recyclerView);

            new CallGetBooks().proccessGetBooks(params);
        }
        catch (Exception e) {

            e.printStackTrace();
        }

        return view;
    }

    public void moveToAddBookScreen(){
        FragmentManager fm = this.getFragmentManager();
        fm.beginTransaction().detach(this).commitNowAllowingStateLoss();
        fm.beginTransaction().replace(R.id.place_holder,new AddFragment()).addToBackStack(null).commit();
        fm.beginTransaction().attach(this).commitNowAllowingStateLoss();
    }

    public void callSearch(){
        //Get list from Server
        try {

            JSONObject jsonObject = new JSONObject();
            RequestClass.startRequestQueue();
            HashMap<String, Object> params = new HashMap<String, Object>();

            switch(spinner.getSelectedItem().toString().toLowerCase()){
                case "created by":
                    jsonObject.put("searchType", "byCreator");
                    jsonObject.put("searchParameters", new JSONObject().put("createdBy", searchParams.getText().toString()));

                    params.put(Constants.REQUEST_JSON, jsonObject);
                    params.put(Constants.ACTION, Constants.ACTION_GET_BOOKS);
                    params.put(Constants.ACTIVITY, this.getActivity());
                    params.put(Constants.FRAGMENT, this);
                    params.put(Constants.VIEW, this.getView());
                    params.put(Constants.CONTEXT, this.getContext());
                    params.put("recyclerView",recyclerView);

                    new CallGetSearchedBooks().proccess(params);
                    break;
                case "updated by":
                    jsonObject.put("searchType", "byUpdater");
                    jsonObject.put("searchParameters", new JSONObject().put("updatedBy", searchParams.getText().toString()));

                    params.put(Constants.REQUEST_JSON, jsonObject);
                    params.put(Constants.ACTION, Constants.ACTION_GET_BOOKS);
                    params.put(Constants.ACTIVITY, this.getActivity());
                    params.put(Constants.FRAGMENT, this);
                    params.put(Constants.VIEW, this.getView());
                    params.put(Constants.CONTEXT, this.getContext());
                    params.put("recyclerView",recyclerView);

                    new CallGetSearchedBooks().proccess(params);
                    break;
                case "isbn":
                    jsonObject.put("searchType", "byISBN");
                    jsonObject.put("searchParameters", new JSONObject().put("isbn", searchParams.getText().toString()));

                    params.put(Constants.REQUEST_JSON, jsonObject);
                    params.put(Constants.ACTION, Constants.ACTION_GET_BOOKS);
                    params.put(Constants.ACTIVITY, this.getActivity());
                    params.put(Constants.FRAGMENT, this);
                    params.put(Constants.VIEW, this.getView());
                    params.put(Constants.CONTEXT, this.getContext());
                    params.put("recyclerView",recyclerView);

                    new CallGetSearchedBooks().proccess(params);
                    break;
                case "title":
                    jsonObject.put("searchType", "byTitle");
                    jsonObject.put("searchParameters", new JSONObject().put("title", searchParams.getText().toString()));

                    params.put(Constants.REQUEST_JSON, jsonObject);
                    params.put(Constants.ACTION, Constants.ACTION_GET_BOOKS);
                    params.put(Constants.ACTIVITY, this.getActivity());
                    params.put(Constants.FRAGMENT, this);
                    params.put(Constants.VIEW, this.getView());
                    params.put(Constants.CONTEXT, this.getContext());
                    params.put("recyclerView",recyclerView);

                    new CallGetSearchedBooks().proccess(params);
                    break;
                case "keywords":
                    String keywords = searchParams.getText().toString();
                    List<String> keywordsArray = new ArrayList<String>(Arrays.asList(keywords.split("\\s*,\\s*")));
                    JSONArray jsonArray = new JSONArray();
                    for(int i=0; i<keywordsArray.size(); i++){
                        jsonArray.put(keywordsArray.get(i));
                    }

                    jsonObject.put("searchType", "byMultipleKeywords");
                    jsonObject.put("searchParameters", new JSONObject().put("keywords", jsonArray));

                    params.put(Constants.REQUEST_JSON, jsonObject);
                    params.put(Constants.ACTION, Constants.ACTION_GET_BOOKS);
                    params.put(Constants.ACTIVITY, this.getActivity());
                    params.put(Constants.FRAGMENT, this);
                    params.put(Constants.VIEW, this.getView());
                    params.put(Constants.CONTEXT, this.getContext());
                    params.put("recyclerView",recyclerView);

                    new CallGetSearchedBooks().proccess(params);
                    break;
                default:
                    params.put(Constants.REQUEST_JSON, null);
                    params.put(Constants.ACTION, Constants.ACTION_GET_BOOKS);
                    params.put(Constants.ACTIVITY, this.getActivity());
                    params.put(Constants.FRAGMENT, this);
                    params.put(Constants.VIEW, this.getView());
                    params.put(Constants.CONTEXT, this.getContext());
                    params.put("recyclerView",recyclerView);

                    new CallGetBooks().proccessGetBooks(params);
                    break;
            }
        }
        catch (Exception e) {

            e.printStackTrace();
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        if(activity != null && activity.getCurrentFocus() != null && activity.getCurrentFocus().getWindowToken() != null) {
            final InputMethodManager inputManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), false ? InputMethodManager.SHOW_FORCED : InputMethodManager.HIDE_IMPLICIT_ONLY);
        }
    }
}
