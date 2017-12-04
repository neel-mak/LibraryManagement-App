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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
        final ListFragment fragment;
        final Activity activity;

        context = (Context) params.get(Constants.CONTEXT);
        action = (String) params.get(Constants.ACTION);
        view = (View) params.get(Constants.VIEW);
        fragment = (ListFragment) params.get(Constants.FRAGMENT);
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
                        AddFragment.showProgress(false);
                        ExceptionMessageHandler.handleError(context, Constants.GENERIC_ERROR_MSG, null, null);
                    }
                }
                AddFragment.showProgress(false);

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

                Toast.makeText(activity, "Got books ...", Toast.LENGTH_SHORT).show();
                JSONArray booksArr = (JSONArray)returnHashMap.get("books");

                LogHelper.logMessage("Apoorv",String.valueOf((JSONArray)returnHashMap.get("books")));

                for(int i=0;i<booksArr.length();i++)
                {
                    JSONObject jsonObject = booksArr.getJSONObject(i);

                    BookItem bookItem = new BookItem(
                            jsonObject.getString("title"),jsonObject.getString("author"),
                            jsonObject.getString("publisher"),jsonObject.getString("numAvailableCopies"),jsonObject.getString("currentStatus")
                    );
                    ListFragment.bookItemList.add(bookItem);
                }
                ListAdapter listAdapter = new ListAdapter(ListFragment.bookItemList);
                listAdapter.setItemCount(booksArr.length()-1);
                ListFragment.adapter = listAdapter;
                RecyclerView recyclerView = (RecyclerView)extraparams.get("recyclerView");
                recyclerView.setAdapter(ListFragment.adapter);

        }
    }
}
