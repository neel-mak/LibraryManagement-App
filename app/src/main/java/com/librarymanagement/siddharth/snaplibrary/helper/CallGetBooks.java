package com.librarymanagement.siddharth.snaplibrary.helper;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.librarymanagement.siddharth.snaplibrary.AddFragment;
import com.librarymanagement.siddharth.snaplibrary.BookItem;
import com.librarymanagement.siddharth.snaplibrary.ListAdapter;
import com.librarymanagement.siddharth.snaplibrary.ListFragment;
import com.librarymanagement.siddharth.snaplibrary.PatronBookItem;
import com.librarymanagement.siddharth.snaplibrary.PatronListAdapter;
import com.librarymanagement.siddharth.snaplibrary.PatronListFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by apoorv.mehta on 12/3/17.
 */

public class CallGetBooks {

    public void proccessGetBooks(final HashMap<String, Object> params) throws JSONException {
        String urlString = Constants.AWS_URL + Constants.CALL_GET_BOOKS;
        LogHelper.logMessage("URL", urlString);

        final Context context;
        final String action;
        final View view;
        final Fragment fragment;
        final Activity activity;

        context = (Context) params.get(Constants.CONTEXT);
        action = (String) params.get(Constants.ACTION);
        view = (View) params.get(Constants.VIEW);
        fragment = (Fragment) params.get(Constants.FRAGMENT);
        activity = (Activity) params.get(Constants.ACTIVITY);

        //Now making the request
        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.GET, urlString, null,new Response.Listener<JSONObject>() {


            @Override
            public void onResponse(JSONObject jsonObject) {
                try {
                    System.out.println("Apoorv came to response"+jsonObject.toString());
                    HashMap returnHashMap = new HashMap<String, Object>();

                    Boolean isSuccess = jsonObject.getBoolean("success");
                    if (isSuccess) {
                        String message = jsonObject.getString("message");
                        LogHelper.logMessage("Apoorv", "\n message: " + message);
                        LogHelper.logMessage("Apoorv data",String.valueOf(jsonObject.getJSONArray("data").length()));
                        returnHashMap.put("books",jsonObject.getJSONArray("data"));
                        updateUI(fragment, context, action, activity, returnHashMap, params);
                    } else {

                        HashMap<String, Object> extraParams = new HashMap<String, Object>();
                        extraParams.put("activity", activity);
                        ExceptionMessageHandler.handleError(context, jsonObject.getString("message"), null, extraParams);
                    }

                } catch (JSONException e) {

                    HashMap<String, Object> extraParams = new HashMap<String, Object>();
                    extraParams.put("activity", activity);
                    ExceptionMessageHandler.handleError(context, e.getMessage(), e, extraParams);
                }
            }
        }
                , new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("Apoorv error");
                String errorMessage = error.getMessage();

                NetworkResponse networkResponse = error.networkResponse;
                if (networkResponse != null && networkResponse.data != null) {
                    try {

                        JSONObject jsonError = new JSONObject(new String(networkResponse.data));
                        errorMessage = jsonError.getString("message");

                    } catch (JSONException e) {
                        //AddFragment.showProgress(false);
                        ExceptionMessageHandler.handleError(context, Constants.GENERIC_ERROR_MSG, null, null);
                    }
                }
                //AddFragment.showProgress(false);

                error.printStackTrace();
                HashMap<String, Object> hs = new HashMap<String, Object>();
                hs.put("activity", activity);
                ExceptionMessageHandler.handleError(context, errorMessage, error, hs);

            }
        });
        RequestClass.getRequestQueue().add(jsObjRequest);
    }

    public void updateUI(Fragment fragment, Context context, String action, Activity activity, HashMap<String, Object> returnHashMap, HashMap<String,Object> extraparams) throws JSONException {

        switch (action) {
            case Constants.ACTION_GET_BOOKS:

                LogHelper.logMessage("Siddharth", "Got books ...");
                JSONArray booksArr = (JSONArray)returnHashMap.get("books");

                LogHelper.logMessage("Apoorv",String.valueOf((JSONArray)returnHashMap.get("books")));

                if(ListFragment.bookItemList != null)
                    ListFragment.bookItemList.clear();
                for(int i=0;i<booksArr.length();i++)
                {
                    JSONObject jsonObject = booksArr.getJSONObject(i);

                    String id = jsonObject.getString("id");
                    String title = jsonObject.getString("title");
                    String author = jsonObject.getString("author");
                    String publisher = jsonObject.getString("publisher");
                    String numAvailableCopies = jsonObject.getString("numAvailableCopies");
                    String currentStatus = jsonObject.getString("currentStatus");

                    id = id == null || id == JSONObject.NULL || "null".equalsIgnoreCase(id) ? "" : id;
                    title = title == null || title == JSONObject.NULL || "null".equalsIgnoreCase(title) ? "" : title;
                    author = author == null || author == JSONObject.NULL || "null".equalsIgnoreCase(author) ? "" : author;
                    publisher = publisher == null || publisher == JSONObject.NULL || "null".equalsIgnoreCase(publisher) ? "" : publisher;
                    numAvailableCopies = numAvailableCopies == null || numAvailableCopies == JSONObject.NULL || "null".equalsIgnoreCase(numAvailableCopies) ? "" : numAvailableCopies;
                    currentStatus = currentStatus == null || currentStatus == JSONObject.NULL || "null".equalsIgnoreCase(currentStatus) ? "" : currentStatus;

                    BookItem bookItem = new BookItem(id,title,author,publisher,numAvailableCopies,currentStatus);
                    ListFragment.bookItemList.add(bookItem);
                }
                ListAdapter.listLength = booksArr.length();
                RecyclerView recyclerView = (RecyclerView)extraparams.get("recyclerView");
                recyclerView.setAdapter(new ListAdapter(ListFragment.bookItemList));
                LogHelper.logMessage("Apoorv","Length of adapter: "+ ListAdapter.listLength + "BookList length: " + recyclerView.getAdapter().getItemCount());
                recyclerView.getAdapter().notifyDataSetChanged();
                break;
            case Constants.ACTION_GET_BOOKS_FOR_PATRON:

                LogHelper.logMessage("Siddharth", "Got books ...");
                JSONArray booksArr1 = (JSONArray)returnHashMap.get("books");

                LogHelper.logMessage("Apoorv",String.valueOf((JSONArray)returnHashMap.get("books")));

                if(PatronListFragment.bookItemList != null)
                    PatronListFragment.bookItemList.clear();
                for(int i=0;i<booksArr1.length();i++)
                {
                    JSONObject jsonObject = booksArr1.getJSONObject(i);

                    String id = jsonObject.getString("id");
                    String title = jsonObject.getString("title");
                    String author = jsonObject.getString("author");
                    String publisher = jsonObject.getString("publisher");
                    String numAvailableCopies = jsonObject.getString("numAvailableCopies");
                    String currentStatus = jsonObject.getString("currentStatus");

                    id = id == null || id == JSONObject.NULL || "null".equalsIgnoreCase(id) ? "" : id;
                    title = title == null || title == JSONObject.NULL || "null".equalsIgnoreCase(title) ? "" : title;
                    author = author == null || author == JSONObject.NULL || "null".equalsIgnoreCase(author) ? "" : author;
                    publisher = publisher == null || publisher == JSONObject.NULL || "null".equalsIgnoreCase(publisher) ? "" : publisher;
                    numAvailableCopies = numAvailableCopies == null || numAvailableCopies == JSONObject.NULL || "null".equalsIgnoreCase(numAvailableCopies) ? "" : numAvailableCopies;
                    currentStatus = currentStatus == null || currentStatus == JSONObject.NULL || "null".equalsIgnoreCase(currentStatus) ? "" : currentStatus;

                    PatronBookItem bookItem = new PatronBookItem(id,title,author,publisher,numAvailableCopies,currentStatus,"","");

                    PatronListFragment.bookItemList.add(bookItem);
                }
                PatronListAdapter.listLength = booksArr1.length();
                RecyclerView recyclerView1 = (RecyclerView)extraparams.get("recyclerView");
                recyclerView1.setAdapter(new PatronListAdapter(PatronListFragment.bookItemList));
                LogHelper.logMessage("Apoorv","Length of adapter: "+ PatronListAdapter.listLength + "BookList length: " + recyclerView1.getAdapter().getItemCount());
                recyclerView1.getAdapter().notifyDataSetChanged();
        }
    }
}
