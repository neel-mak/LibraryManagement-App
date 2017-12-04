package com.librarymanagement.siddharth.snaplibrary.helper;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.librarymanagement.siddharth.snaplibrary.AddFragment;
import com.librarymanagement.siddharth.snaplibrary.UpdateDeleteFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.nio.IntBuffer;
import java.util.HashMap;

/**
 * Created by siddharthdaftari on 11/2/17.
 */

public class CallISearchForUpdate {

    public void process(final HashMap<String, Object> params) throws JSONException{

        String urlString = Constants.AWS_URL + Constants.CALL_SEARCH_URL;
        LogHelper.logMessage("URL", urlString);

        //Extracting ONLY REQUIRED params
        JSONObject requestJSON = null;
        final Context context;
        final String action;
        final View view;
        final UpdateDeleteFragment fragment;
        final Activity activity;
        requestJSON = (JSONObject) params.get(Constants.REQUEST_JSON);
        context = (Context) params.get(Constants.CONTEXT);
        action = (String) params.get(Constants.ACTION);
        view = (View) params.get(Constants.VIEW);
        fragment = (UpdateDeleteFragment) params.get(Constants.FRAGMENT);
        activity = (Activity) params.get(Constants.ACTIVITY);

        //Now making the request
        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.POST, urlString, requestJSON, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        try {

                            HashMap returnHashMap = new HashMap<String, Object>();

                            Boolean isSuccess = jsonObject.getBoolean("success");
                            LogHelper.logMessage("Siddharth", String.valueOf(isSuccess));
                            if(isSuccess){
                                JSONObject jsonObject1 = jsonObject.getJSONObject("data");
                                returnHashMap.put("author", jsonObject1.getString("author"));
                                returnHashMap.put("title", jsonObject1.getString("title"));
                                returnHashMap.put("callNumber", jsonObject1.getString("callNumber"));
                                returnHashMap.put("publisher", jsonObject1.getString("publisher"));
                                returnHashMap.put("yearOfPublication", jsonObject1.getString("yearOfPublication"));
                                returnHashMap.put("locationInLibrary", jsonObject1.getString("locationInLibrary"));
                                returnHashMap.put("numOfCopies", jsonObject1.getString("numOfCopies"));
                                returnHashMap.put("currentStatus", jsonObject1.getString("currentStatus"));
                                JSONArray jsonArray = jsonObject1.getJSONArray("keywords");
                                String keywords = "";
                                for(int i=0; i<jsonArray.length(); i++){
                                    if(i!=jsonArray.length()-1)
                                        keywords = keywords + jsonArray.getString(i) + ",";
                                    else
                                        keywords = keywords + jsonArray.getString(i);

                                }
                                returnHashMap.put("keywords", keywords);
                                if(jsonObject1.get("coverageImage")!=JSONObject.NULL) {
                                    JSONObject jsonObject2 = jsonObject1.getJSONObject("coverageImage");
                                    JSONArray jsonArray1 = jsonObject2.getJSONArray("data");
                                    byte[] coverageImageByteArray = new byte[jsonArray1.length()];
                                    for (int i = 0; i < jsonArray1.length(); i++) {
                                        coverageImageByteArray[i] = (byte) jsonArray1.getInt(i);
                                    }
                                    returnHashMap.put("coverageImage", objectToBytArray(jsonArray1));

                                    int[] coverageImageIntArray = new int[jsonArray1.length()];
                                    for (int i = 0; i < jsonArray1.length(); i++) {
                                        coverageImageIntArray[i] = (int) jsonArray1.getInt(i);
                                    }
                                    LogHelper.logMessage("coverageImageByteArray", "" + coverageImageByteArray.length);
                                    Bitmap bitmap = Bitmap.createBitmap(coverageImageIntArray.length, coverageImageIntArray.length, Bitmap.Config.ARGB_8888);
                                    bitmap.copyPixelsFromBuffer(makeBuffer(coverageImageIntArray, coverageImageIntArray.length));
                                    UpdateDeleteFragment.updateFragmentImageView.setImageBitmap(bitmap);
                                    UpdateDeleteFragment.updateFragmentImageView.invalidate();
                                }
                                returnHashMap.put("createdBy", jsonObject1.getString("createdBy"));
                                returnHashMap.put("updatedBy", jsonObject1.getString("updatedBy"));
                                returnHashMap.put("numAvailableCopies", jsonObject1.getString("numAvailableCopies"));
                                returnHashMap.put("isbn", jsonObject1.getString("isbn"));

                                updateUI(context, action, activity, returnHashMap, params);
                            }else{
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
                        UpdateDeleteFragment.showProgress(false);

                        error.printStackTrace();
                        HashMap<String, Object> hs = new HashMap<String, Object>();
                        hs.put("activity", activity);
                        ExceptionMessageHandler.handleError(context, errorMessage, error, hs);

                    }
                });
        RequestClass.getRequestQueue().add(jsObjRequest);
    }

    public byte[] objectToBytArray( Object ob ){
        return ((ob.toString()).getBytes());
    }

    public void updateUI(Context context, String action, Activity activity, HashMap<String, Object> returnHashMap, HashMap<String,Object> extraparams){

        switch (action) {
            case Constants.ACTION_LOAD_UPDATE:
                //UpdateDeleteFragment.showProgress(false);

                LogHelper.logMessage("CallSearchForUpdate", "updateUI");
                UpdateDeleteFragment.updateFragmentBookAuthor.setText((String)returnHashMap.get("author"));
                UpdateDeleteFragment.updateFragmentBookTitle.setText((String)returnHashMap.get("title"));
                UpdateDeleteFragment.updateFragmentCallNumber.setText((String)returnHashMap.get("callNumber"));
                UpdateDeleteFragment.updateFragmentBookPublisher.setText((String)returnHashMap.get("publisher"));
                UpdateDeleteFragment.updateFragmentBookYear.setText((String)returnHashMap.get("yearOfPublication"));
                UpdateDeleteFragment.updateFragmentBookLocation.setText((String)returnHashMap.get("locationInLibrary"));
                UpdateDeleteFragment.updateFragmentBookCopies.setText((String)returnHashMap.get("numAvailableCopies"));
                UpdateDeleteFragment.updateFragmentBookStatus.setText((String)returnHashMap.get("currentStatus"));
                UpdateDeleteFragment.updateFragmentBookKeywords.setText((String)returnHashMap.get("keywords"));

//                byte byteArray[] = (byte[])returnHashMap.get("coverageImage");
//                LogHelper.logMessage("Siddharth", "" + byteArray.length);
//                Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
//                UpdateDeleteFragment.updateFragmentImageView.setImageBitmap(bitmap);
//                UpdateDeleteFragment.updateFragmentImageView.invalidate();





        }
    }

    private IntBuffer makeBuffer(int[] src, int n) {
        IntBuffer dst = IntBuffer.allocate(n*n);
        for (int i = 0; i < n; i++) {
            dst.put(src);
        }
        dst.rewind();
        return dst;
    }
}
