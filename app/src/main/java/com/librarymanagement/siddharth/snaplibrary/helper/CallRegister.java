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
import com.librarymanagement.siddharth.snaplibrary.ConfirmationFragment;
import com.librarymanagement.siddharth.snaplibrary.R;
import com.librarymanagement.siddharth.snaplibrary.SignUpFragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by siddharthdaftari on 11/2/17.
 */

public class CallRegister {

    public void processRegisterRequest(final HashMap<String, Object> params) throws JSONException{

        String urlString = Constants.AWS_URL + Constants.CALL_REGISTER_URL;
        LogHelper.logMessage("URL", urlString);

        //Extracting ONLY REQUIRED params
        JSONObject requestJSON = null;
        final Context context;
        final String action;
        final View view;
        final SignUpFragment fragment;
        final Activity activity;
        requestJSON = (JSONObject) params.get(Constants.REQUEST_JSON);
        context = (Context) params.get(Constants.CONTEXT);
        action = (String) params.get(Constants.ACTION);
        view = (View) params.get(Constants.VIEW);
        fragment = (SignUpFragment) params.get(Constants.FRAGMENT);
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

                                LogHelper.logMessage("Siddharth","id: " + jsonObjectData.getString("id")
                                        + "\n email: " + jsonObjectData.getString("email")
                                        + "\n universityId: " + jsonObjectData.getString("universityId")
                                        + "\n isVerified: " + jsonObjectData.getString("isVerified")
                                        + "\n userType: " + jsonObjectData.getString("userType")
                                        + "\n verificationCode: " + jsonObjectData.getString("verificationCode"));

                                String[] userDataArray = new String[5];
                                userDataArray[0] = jsonObjectData.getString("id");
                                userDataArray[1] = jsonObjectData.getString("email");
                                userDataArray[2] = jsonObjectData.getString("universityId");
                                userDataArray[3] = jsonObjectData.getString("userType");
                                userDataArray[4] = String.valueOf(jsonObjectData.getBoolean("isVerified"));
                                SharedData.addNewUser(activity.getApplicationContext(), userDataArray);

                                updateUI(fragment, context, action, activity, returnHashMap, params);
                            }else{
                                SignUpFragment.showProgress(false);
                                HashMap<String, Object> hs = new HashMap<String, Object>();
                                hs.put("activity", activity);
                                ExceptionMessageHandler.handleError(context, jsonObject.getString("message"), null, hs);
                            }

                        }catch (JSONException e){
                            HashMap<String, Object> hs = new HashMap<String, Object>();
                            hs.put("activity", activity);
                          ExceptionMessageHandler.handleError(context, e.getMessage(), e, hs);
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
                        SignUpFragment.showProgress(false);
                        SignUpFragment.signUpEmailAddress.requestFocus();

                        error.printStackTrace();
                        HashMap<String, Object> hs = new HashMap<String, Object>();
                        hs.put("activity", activity);
                        ExceptionMessageHandler.handleError(context, errorMessage, error, hs);

                    }
                });
        RequestClass.getRequestQueue().add(jsObjRequest);
    }

    public void updateUI(Fragment fragment, Context context, String action, Activity activity, HashMap<String, String> returnHashMap, HashMap<String,Object> extraparams){

        switch (action) {
            case Constants.ACTION_REGISTER:
                SignUpFragment.showProgress(false);
                fragment.getFragmentManager().beginTransaction().replace(R.id.fragment,new ConfirmationFragment()).commit();
        }
    }
}
