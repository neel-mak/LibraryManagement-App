package com.librarymanagement.siddharth.snaplibrary.helper;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.librarymanagement.siddharth.snaplibrary.PatronSearchFragment;
import com.librarymanagement.siddharth.snaplibrary.R;
import com.librarymanagement.siddharth.snaplibrary.TestingAssistanceFragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by siddharthdaftari on 11/2/17.
 */

public class CallUpdateCurrentTime {

    public void process(final HashMap<String, Object> params) throws JSONException{

        String urlString = Constants.AWS_URL + Constants.CALL_TIME_URL;
        LogHelper.logMessage("URL", urlString);

        //Extracting ONLY REQUIRED params
        JSONObject requestJSON = null;
        final Context context;
        final String action;
        final Fragment fragment;
        final Activity activity;
        requestJSON = (JSONObject) params.get(Constants.REQUEST_JSON);
        context = (Context) params.get(Constants.CONTEXT);
        action = (String) params.get(Constants.ACTION);
        fragment = (Fragment) params.get(Constants.FRAGMENT);
        activity = (Activity) params.get(Constants.ACTIVITY);

        //Now making the request
        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.POST, urlString, requestJSON, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        try {

                            HashMap returnHashMap = new HashMap<String, String>();

                            Boolean isSuccess = jsonObject.getBoolean("success");
                            LogHelper.logMessage("Siddharth", String.valueOf(isSuccess));
                            if(isSuccess){

                                params.put("fragment", fragment);
                                updateUI(context, action, activity, returnHashMap, params);
                            }else{
                                HashMap<String, Object> hs = new HashMap<String, Object>();
                                hs.put("activity", activity);
                                ExceptionMessageHandler.handleError(context, jsonObject.getString("message"), null, null);
                            }

                        }catch (JSONException e){
                          ExceptionMessageHandler.handleError(context, e.getMessage(), e, null);
                        }
                    }
                }, new Response.ErrorListener() {

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

    public void updateUI(Context context, String action, Activity activity, HashMap<String, String> returnHashMap, HashMap<String,Object> extraparams){

        switch (action) {
            case Constants.ACTION_SET_NEW_TIME:

                LogHelper.logMessage("Siddharth", "Time updated");
                Fragment fr = (Fragment) extraparams.get("fragment");
                fr.getFragmentManager().beginTransaction().remove(fr);
                fr.getFragmentManager().beginTransaction().add(R.id.patron_main_container,new TestingAssistanceFragment()).addToBackStack(null).commit();
        }
    }
}
