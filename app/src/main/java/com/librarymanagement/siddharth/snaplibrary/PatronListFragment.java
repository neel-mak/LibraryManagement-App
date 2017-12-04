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

public class PatronListFragment extends Fragment //implements AdapterView.OnItemSelectedListener
{
    public static RecyclerView.Adapter adapter;
    public static List<PatronBookItem> bookItemList = new ArrayList<>();

    RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)  {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_list, container, false);
        recyclerView=(RecyclerView)view.findViewById(R.id.recyclerView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        //get parameters from search screen
        String searchCriteria = getArguments().getString("spinner_value");
        String searchValue = getArguments().getString("search_text");

        //Get list from Server
        try {

            JSONObject jsonObject = new JSONObject();
            RequestClass.startRequestQueue();
            HashMap<String, Object> params = new HashMap<String, Object>();

            switch(searchCriteria.toLowerCase()){
                case "created by":
                    jsonObject.put("searchType", "byCreator");
                    jsonObject.put("searchParameters", new JSONObject().put("createdBy", searchValue));

                    params.put(Constants.REQUEST_JSON, jsonObject);
                    params.put(Constants.ACTION, Constants.ACTION_GET_BOOKS_FOR_PATRON);
                    params.put(Constants.ACTIVITY, this.getActivity());
                    params.put(Constants.FRAGMENT, this);
                    params.put(Constants.VIEW, this.getView());
                    params.put(Constants.CONTEXT, this.getContext());
                    params.put("recyclerView",recyclerView);

                    new CallGetSearchedBooks().proccess(params);
                    break;
                case "updated by":
                    jsonObject.put("searchType", "byUpdater");
                    jsonObject.put("searchParameters", new JSONObject().put("updatedBy", searchValue));

                    params.put(Constants.REQUEST_JSON, jsonObject);
                    params.put(Constants.ACTION, Constants.ACTION_GET_BOOKS_FOR_PATRON);
                    params.put(Constants.ACTIVITY, this.getActivity());
                    params.put(Constants.FRAGMENT, this);
                    params.put(Constants.VIEW, this.getView());
                    params.put(Constants.CONTEXT, this.getContext());
                    params.put("recyclerView",recyclerView);

                    new CallGetSearchedBooks().proccess(params);
                    break;
                case "isbn":
                    jsonObject.put("searchType", "byISBN");
                    jsonObject.put("searchParameters", new JSONObject().put("isbn", searchValue));

                    params.put(Constants.REQUEST_JSON, jsonObject);
                    params.put(Constants.ACTION, Constants.ACTION_GET_BOOKS_FOR_PATRON);
                    params.put(Constants.ACTIVITY, this.getActivity());
                    params.put(Constants.FRAGMENT, this);
                    params.put(Constants.VIEW, this.getView());
                    params.put(Constants.CONTEXT, this.getContext());
                    params.put("recyclerView",recyclerView);

                    new CallGetSearchedBooks().proccess(params);
                    break;
                case "title":
                    jsonObject.put("searchType", "byTitle");
                    jsonObject.put("searchParameters", new JSONObject().put("title", searchValue));

                    params.put(Constants.REQUEST_JSON, jsonObject);
                    params.put(Constants.ACTION, Constants.ACTION_GET_BOOKS_FOR_PATRON);
                    params.put(Constants.ACTIVITY, this.getActivity());
                    params.put(Constants.FRAGMENT, this);
                    params.put(Constants.VIEW, this.getView());
                    params.put(Constants.CONTEXT, this.getContext());
                    params.put("recyclerView",recyclerView);

                    new CallGetSearchedBooks().proccess(params);
                    break;
                case "keywords":
                    String keywords = searchValue;
                    List<String> keywordsArray = new ArrayList<String>(Arrays.asList(keywords.split("\\s*,\\s*")));
                    JSONArray jsonArray = new JSONArray();
                    for(int i=0; i<keywordsArray.size(); i++){
                        jsonArray.put(keywordsArray.get(i));
                    }

                    jsonObject.put("searchType", "byMultipleKeywords");
                    jsonObject.put("searchParameters", new JSONObject().put("keywords", jsonArray));

                    params.put(Constants.REQUEST_JSON, jsonObject);
                    params.put(Constants.ACTION, Constants.ACTION_GET_BOOKS_FOR_PATRON);
                    params.put(Constants.ACTIVITY, this.getActivity());
                    params.put(Constants.FRAGMENT, this);
                    params.put(Constants.VIEW, this.getView());
                    params.put(Constants.CONTEXT, this.getContext());
                    params.put("recyclerView",recyclerView);

                    new CallGetSearchedBooks().proccess(params);
                    break;
                default:
                    params.put(Constants.REQUEST_JSON, null);
                    params.put(Constants.ACTION, Constants.ACTION_GET_BOOKS_FOR_PATRON);
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
        return view;
    }
}
