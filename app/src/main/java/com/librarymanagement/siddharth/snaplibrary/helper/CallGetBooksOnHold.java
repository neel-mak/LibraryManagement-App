package com.librarymanagement.siddharth.snaplibrary.helper;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.librarymanagement.siddharth.snaplibrary.HoldFragment;
import com.librarymanagement.siddharth.snaplibrary.HoldListAdapter;
import com.librarymanagement.siddharth.snaplibrary.PatronBookItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import static com.librarymanagement.siddharth.snaplibrary.HoldFragment.patron_no_books_on_hold;


public class CallGetBooksOnHold {

    public void process(final HashMap<String, Object> params) throws JSONException {
        String urlString = Constants.AWS_URL + Constants.CALL_GET_BOOKS_ON_HOLD_URL;
        LogHelper.logMessage("URL", urlString);

        final Context context;
        JSONObject requestJSON = null;
        final String action;
        final View view;
        final Fragment fragment;
        final Activity activity;

        context = (Context) params.get(Constants.CONTEXT);
        requestJSON = (JSONObject) params.get(Constants.REQUEST_JSON);
        action = (String) params.get(Constants.ACTION);
        view = (View) params.get(Constants.VIEW);
        fragment = (Fragment) params.get(Constants.FRAGMENT);
        activity = (Activity) params.get(Constants.ACTIVITY);

        //Now making the request
        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.POST, urlString, requestJSON,new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
            try {
                HashMap returnHashMap = new HashMap<String, Object>();

                Boolean isSuccess = jsonObject.getBoolean("success");
                if (isSuccess) {
                    if(jsonObject.get("data") != JSONObject.NULL && jsonObject.getJSONArray("data").length() != 0) {
                        String message = jsonObject.getString("message");
                        LogHelper.logMessage("Siddharth", "\n message: " + message);
                        LogHelper.logMessage("Siddharth", String.valueOf(jsonObject.getJSONArray("data").length()));


                        returnHashMap.put("books", jsonObject.getJSONArray("data"));
                        updateUI(fragment, context, action, activity, returnHashMap, params);
                    }else{
                        //Toast.makeText(activity, "No books checked out for return", Toast.LENGTH_SHORT).show();

                        if(patron_no_books_on_hold != null) {
                            LogHelper.logMessage("Siddharth", "No books on hold!");
                            patron_no_books_on_hold.setText("No books on hold!");
                            patron_no_books_on_hold.setVisibility(View.VISIBLE);
                        }
                    }
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
                String errorMessage = error.getMessage();

                NetworkResponse networkResponse = error.networkResponse;
                if (networkResponse != null && networkResponse.data != null) {
                    try {

                        JSONObject jsonError = new JSONObject(new String(networkResponse.data));
                        errorMessage = jsonError.getString("message");

                    } catch (JSONException e) {
                        ExceptionMessageHandler.handleError(context, Constants.GENERIC_ERROR_MSG, null, null);
                    }
                }

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
            case Constants.ACTION_GET_BOOKS_ON_HOLD:

                LogHelper.logMessage(Constants.ACTION_GET_BOOKS_ON_HOLD, "Got books on hold ...");
                JSONArray booksArr = ((JSONArray)returnHashMap.get("books"));

                LogHelper.logMessage("Siddharth",String.valueOf((JSONArray)returnHashMap.get("books")));

                if(HoldFragment.holdListItems != null)
                    HoldFragment.holdListItems.clear();

                for(int i=0;i<booksArr.length();i++)
                {
                    JSONObject jsonObject = booksArr.getJSONObject(i);
                    JSONObject jsonObject1 = jsonObject.getJSONObject("book");

                    String id = jsonObject1.getString("id");
                    String title = jsonObject1.getString("title");
                    String author = jsonObject1.getString("author");
                    String publisher = jsonObject1.getString("publisher");
                    String holdlistExpirationDate = jsonObject.getString("endDate");

                    id = id == null || id == JSONObject.NULL || "null".equalsIgnoreCase(id) ? "" : id;
                    title = title == null || title == JSONObject.NULL || "null".equalsIgnoreCase(title) ? "" : title;
                    author = author == null || author == JSONObject.NULL || "null".equalsIgnoreCase(author) ? "" : author;
                    publisher = publisher == null || publisher == JSONObject.NULL || "null".equalsIgnoreCase(publisher) ? "" : publisher;
                    holdlistExpirationDate = holdlistExpirationDate == null || holdlistExpirationDate == JSONObject.NULL || "null".equalsIgnoreCase(holdlistExpirationDate) ? "" : holdlistExpirationDate;

                    PatronBookItem bookItem = new PatronBookItem(id,title,author,publisher,"","","","", holdlistExpirationDate);

                    HoldFragment.holdListItems.add(bookItem);
                }
                HoldListAdapter.listLength = booksArr.length();
                RecyclerView recyclerView1 = (RecyclerView)extraparams.get("recyclerView");
                recyclerView1.setAdapter(new HoldListAdapter(HoldFragment.holdListItems));
                LogHelper.logMessage("Siddharth","Length of adapter: "+ HoldListAdapter.listLength + "BookList length: " + recyclerView1.getAdapter().getItemCount());
                recyclerView1.getAdapter().notifyDataSetChanged();

                break;
        }
    }
}
