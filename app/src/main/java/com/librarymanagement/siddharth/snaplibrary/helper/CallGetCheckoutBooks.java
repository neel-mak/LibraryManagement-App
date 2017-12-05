package com.librarymanagement.siddharth.snaplibrary.helper;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.librarymanagement.siddharth.snaplibrary.PatronBookItem;
import com.librarymanagement.siddharth.snaplibrary.ReturnFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import static com.librarymanagement.siddharth.snaplibrary.PatronListFragment.patron_no_books_found;
import static com.librarymanagement.siddharth.snaplibrary.ReturnFragment.return_fragment_cardview0;


public class CallGetCheckoutBooks {

    public void process(final HashMap<String, Object> params) throws JSONException {
        String urlString = Constants.AWS_URL + Constants.CALL_CHECKED_OUT_BOOK_URL;
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

                        for(int j=0; j<9 ;j++){
                            ReturnFragment.cardViews[j].setVisibility(View.GONE);
                        }

                        if(patron_no_books_found != null) {
                            LogHelper.logMessage("Siddharth", "No books found");
                            patron_no_books_found.setText("No books found");
                            patron_no_books_found.setVisibility(View.VISIBLE);
                        }

                        if(return_fragment_cardview0 != null) {
                            LogHelper.logMessage("Siddharth", "No books checked out for return");
                            return_fragment_cardview0.setVisibility(View.VISIBLE);
                        }

                    }
                } else {

                    for(int j=0; j<9 ;j++){
                        ReturnFragment.cardViews[j].setVisibility(View.GONE);
                    }

                    HashMap<String, Object> extraParams = new HashMap<String, Object>();
                    extraParams.put("activity", activity);
                    ExceptionMessageHandler.handleError(context, jsonObject.getString("message"), null, extraParams);
                }

            } catch (JSONException e) {

                for(int j=0; j<9 ;j++){
                    ReturnFragment.cardViews[j].setVisibility(View.GONE);
                }

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

                for(int j=0; j<9 ;j++){
                    ReturnFragment.cardViews[j].setVisibility(View.GONE);
                }

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
            case Constants.ACTION_GET_CHECKED_OUT_BOOKS:

                //Toast.makeText(activity, "Got books ...", Toast.LENGTH_SHORT).show();
                LogHelper.logMessage(Constants.ACTION_GET_CHECKED_OUT_BOOKS, "Got books ...");
                JSONArray booksArr = (JSONArray)returnHashMap.get("books");

                LogHelper.logMessage("Siddharth",String.valueOf((JSONArray)returnHashMap.get("books")));

                if(ReturnFragment.patronBookItems != null)
                    ReturnFragment.patronBookItems.clear();

                int i=0;
                for(i=0;i<booksArr.length();i++)
                {
                    JSONObject jsonObject = booksArr.getJSONObject(i);

                    JSONObject jsonObject1 = jsonObject.getJSONObject("book");
                    String id = jsonObject1.getString("id");
                    String title = jsonObject1.getString("title");
                    String author = jsonObject1.getString("author");
                    String checkoutDate = jsonObject.getString("checkoutDate");
                    String dueDate = jsonObject.getString("dueDate");

                    id = id == null || id == JSONObject.NULL || "null".equalsIgnoreCase(id) ? "" : id;
                    title = title == null || title == JSONObject.NULL || "null".equalsIgnoreCase(title) ? "" : title;
                    author = author == null || author == JSONObject.NULL || "null".equalsIgnoreCase(author) ? "" : author;
                    checkoutDate = checkoutDate == null || checkoutDate == JSONObject.NULL || "null".equalsIgnoreCase(checkoutDate) ? "" : checkoutDate;
                    dueDate = dueDate == null || dueDate == JSONObject.NULL || "null".equalsIgnoreCase(dueDate) ? "" : dueDate;

                    PatronBookItem patronBookItem = new PatronBookItem(id,title,author,"","","", checkoutDate, dueDate);

                    ReturnFragment.patronBookItems.add(patronBookItem);
                    ReturnFragment.checkBoxes[i].setChecked(false);
                    ReturnFragment.titles[i].setText(title);
                    ReturnFragment.authors[i].setText(author);
                    ReturnFragment.checkOutDate[i].setText(checkoutDate);
                    ReturnFragment.dueDate[i].setText(dueDate);
                }

                for(int j=i; j<9 ;j++){
                    ReturnFragment.cardViews[j].setVisibility(View.GONE);
                }

                break;
        }
    }
}
