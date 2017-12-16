package com.librarymanagement.siddharth.snaplibrary.helper;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.librarymanagement.siddharth.snaplibrary.AddFragment;
import com.librarymanagement.siddharth.snaplibrary.CartFragment;
import com.librarymanagement.siddharth.snaplibrary.CatalogActivity;
import com.librarymanagement.siddharth.snaplibrary.ConfirmationFragment;
import com.librarymanagement.siddharth.snaplibrary.ListFragment;
import com.librarymanagement.siddharth.snaplibrary.PatronBookItem;
import com.librarymanagement.siddharth.snaplibrary.R;
import com.librarymanagement.siddharth.snaplibrary.SuccessFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;


/**
 * Created by apoorv.mehta on 12/5/17.
 */

public class CallCheckoutCart {

    public void processCheckoutBooks(final HashMap<String, Object> params) throws JSONException{

        String urlString = Constants.AWS_URL + Constants.CALL_CHECKOUT_CART_URL;
        LogHelper.logMessage("URL", urlString);

        //Extracting ONLY REQUIRED params
        JSONObject requestJSON = null;
        final Context context;
        final String action;
        final View view;
        final CartFragment fragment;
        final Activity activity;

        requestJSON = (JSONObject) params.get(Constants.REQUEST_JSON);
        context = (Context) params.get(Constants.CONTEXT);
        action = (String) params.get(Constants.ACTION);
        view = (View) params.get(Constants.VIEW);
        fragment = (CartFragment) params.get(Constants.FRAGMENT);
        activity = (Activity) params.get(Constants.ACTIVITY);

        //Now making the request
        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.POST, urlString, requestJSON, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        try {

                            HashMap returnHashMap = new HashMap<String, Object>();

                            Boolean isSuccess = jsonObject.getBoolean("success");// || jsonObject.getBoolean("sucess");
                            if (isSuccess) {
                                String message = jsonObject.getString("message");
                                LogHelper.logMessage("Apoorv", "\n message: " + message);
                                returnHashMap.put("response",jsonObject.getJSONArray("data"));
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
                },new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
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

                        error.printStackTrace();
                        HashMap<String, Object> hs = new HashMap<String, Object>();
                        hs.put("activity", activity);
                        ExceptionMessageHandler.handleError(context, errorMessage, error, hs);

                    }
                });
        RequestClass.getRequestQueue().add(jsObjRequest);
    }

    public void updateUI(Fragment fragment, Context context, String action, Activity activity, HashMap<String, Object> returnHashMap, HashMap<String,Object> extraparams){
        LogHelper.logMessage("Apoorv","came to update ui");
        switch (action) {
            case Constants.ACTION_CHECKOUT_CART:
                LogHelper.logMessage("Apoorv","came to update ui");
                Toast.makeText(activity, " Checkout Transaction Successful.", Toast.LENGTH_SHORT).show();

                CartFragment thisFragment = (CartFragment)fragment;
                thisFragment.emptyCart();

              JSONArray responseData = (JSONArray) returnHashMap.get("response");

                ArrayList<PatronBookItem> checkoutBooks = new ArrayList<PatronBookItem>();
                try {
                    for (int i = 0; i < responseData.length(); i++) {


                        PatronBookItem pbi = new PatronBookItem(
                                responseData.getJSONObject(i).getString("bookId"),
                                responseData.getJSONObject(i).getJSONObject("book").getString("title"),
                                responseData.getJSONObject(i).getJSONObject("book").getString("author"),
                                null,null,null,
                                responseData.getJSONObject(i).getString("checkoutDate"),
                                responseData.getJSONObject(i).getString("dueDate"), null
                                );
                        LogHelper.logMessage("Apoorv","Received book in response of checkout"+pbi.Book_Title);
                        checkoutBooks.add(pbi);
                    }

                }
                catch(Exception e) {
                    e.printStackTrace();
                }

                Bundle responseBundle = new Bundle();
                responseBundle.putSerializable("checkoutbooksserial",(Serializable)checkoutBooks);
                SuccessFragment sfr = new SuccessFragment();
                sfr.setArguments(responseBundle);
                fragment.getFragmentManager().beginTransaction().replace(R.id.patron_main_container,sfr).addToBackStack(null).commit();

               // Fragment fr = new ListFragment();
                //fragment.getFragmentManager().beginTransaction().replace(R.id.place_holder,fr).addToBackStack(null).commit();
        }
    }
}