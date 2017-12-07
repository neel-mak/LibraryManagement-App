package com.librarymanagement.siddharth.snaplibrary.helper;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.librarymanagement.siddharth.snaplibrary.CatalogActivity;
import com.librarymanagement.siddharth.snaplibrary.LoginFragment;
import com.librarymanagement.siddharth.snaplibrary.MainActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by siddharthdaftari on 11/2/17.
 */

public class CallLogin {

    public void processLoginRequest(final HashMap<String, Object> params) throws JSONException{

        String urlString = Constants.AWS_URL + Constants.CALL_LOGIN_URL;
        LogHelper.logMessage("URL", urlString);

        //Extracting ONLY REQUIRED params
        JSONObject requestJSON = null;
        final Context context;
        final String action;
        final View view;
        final LoginFragment fragment;
        final Activity activity;
        requestJSON = (JSONObject) params.get(Constants.REQUEST_JSON);
        context = (Context) params.get(Constants.CONTEXT);
        action = (String) params.get(Constants.ACTION);
        view = (View) params.get(Constants.VIEW);
        fragment = (LoginFragment) params.get(Constants.FRAGMENT);
        activity = (Activity) params.get(Constants.ACTIVITY);

        //Now making the request
        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.POST, urlString, requestJSON, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        try {

                            HashMap returnHashMap = new HashMap<String, String>();

                            Boolean isSuccess = jsonObject.getBoolean("success");
                            if(isSuccess){
                                JSONObject jsonObjectData = jsonObject.getJSONObject("data");

                                //add user to shared preference
                                String[] userDataArray = new String[5];
                                userDataArray[0] = jsonObjectData.getString("id");
                                userDataArray[1] = jsonObjectData.getString("email");
                                userDataArray[2] = jsonObjectData.getString("universityId");
                                userDataArray[3] = jsonObjectData.getString("userType");
                                userDataArray[4] = String.valueOf(jsonObjectData.getBoolean("isVerified"));
                                SharedData.addNewUser(activity.getApplicationContext(), userDataArray);

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
                        LoginFragment.showProgress(false);
                        LoginFragment.mPsdView.requestFocus();

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
            case Constants.ACTION_UPDATE_LOGIN:
                LoginFragment.showProgress(false);

                if(activity!=null) {
                    LogHelper.logMessage("Activity Back", "Activity" + activity.getLocalClassName() + " finished");
                    activity.finish();
                }

                Intent i;
                i = new Intent(context,MainActivity.class);
                context.startActivity(i);
        }
    }
}
