package com.librarymanagement.siddharth.snaplibrary.helper;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.view.View;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.librarymanagement.siddharth.snaplibrary.TestingAssistanceFragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class CallGetCurrentTime {

    public void process(final HashMap<String, Object> params) throws JSONException {
        String urlString = Constants.AWS_URL + Constants.CALL_TIME_URL;
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
                    HashMap returnHashMap = new HashMap<String, Object>();

                    LogHelper.logMessage("Siddharth data",String.valueOf(jsonObject.getString("currentTime")));
                    returnHashMap.put("currentTime",jsonObject.getString("currentTime"));
                    updateUI(fragment, context, action, activity, returnHashMap, params);


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
                System.out.println("Siddharth error");
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
            case Constants.ACTION_GET_CURRENT_TIME:

                LogHelper.logMessage(Constants.ACTION_GET_CURRENT_TIME, "Got Time ...");
                String currentTime = (String)returnHashMap.get("currentTime");

                TestingAssistanceFragment.currentDateTime.setText(currentTime);
                break;
            case Constants.ACTION_SET_NEW_TIME:
                LogHelper.logMessage(Constants.ACTION_SET_NEW_TIME, "Got Time and now sending minutes ...");

                String currentTime1 = (String)returnHashMap.get("currentTime");
                TestingAssistanceFragment.currentDateTime.setText(currentTime1);

                try {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("minutes", extraparams.get("minutes"));

                    HashMap<String, Object> params = new HashMap<String, Object>();
                    params.put(Constants.REQUEST_JSON, jsonObject);
                    params.put(Constants.ACTION, Constants.ACTION_SET_NEW_TIME);
                    params.put(Constants.ACTIVITY, activity);
                    params.put(Constants.FRAGMENT, fragment);
                    params.put(Constants.CONTEXT, context);

                    RequestClass.startRequestQueue();
                    new CallUpdateCurrentTime().process(params);
                }catch (JSONException e){
                    e.printStackTrace();
                }
                break;
        }
    }
}
