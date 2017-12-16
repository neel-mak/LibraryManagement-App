package com.librarymanagement.siddharth.snaplibrary.helper;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import org.json.JSONException;

import java.util.HashSet;
import java.util.Set;

public class SharedData {

    //DO NOT DELETE this comment
    //Format of String: String id, String email, String universityId, String userType, Boolean isVerified
    public static String userDataInStringFormat = new String();

    //will write userDataInStringFormat to file
    public static void writetoSharedInitial(Context c) {
        LogHelper.logMessage("writetoSharedInitial", "In: Context: " + c);

        SharedPreferences sharedPref = c.getSharedPreferences("userDataSharedPreference",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("userDataInStringFormat",userDataInStringFormat);
        editor.apply();

        LogHelper.logMessage("writetoSharedInitial", "Out: userDataInStringFormat: " + sharedPref.getString("userDataInStringFormat",""));
    }

    //will read file to userDataInStringFormat
    public static void readFromSharedInitial (Context c) {
        LogHelper.logMessage("readFromSharedInitial", "In: Context: " + c);

        SharedPreferences sharedPref = c.getSharedPreferences("userDataSharedPreference",Context.MODE_PRIVATE);
        userDataInStringFormat = sharedPref.getString("userDataInStringFormat", "");

        LogHelper.logMessage("readFromSharedInitial", "Out: userDataInStringFormat: " + userDataInStringFormat);
    }

    //Return all details of user
    public static String[] getUserDetails () {

        if(!"".equalsIgnoreCase(userDataInStringFormat)) {
            String[] userDataDetails = userDataInStringFormat.split("~");
            LogHelper.logMessage("getUserDetails", "Out1: userDataDetails: " + userDataDetails);
            return userDataDetails;
        }else{
            LogHelper.logMessage("getUserDetails", "Out2: userDataDetails: " + null);
            return null;
        }
    }

    //add new user
    public static void addNewUser(Context c, String[] userDataDetails){
        LogHelper.logMessage("addNewUser", "In: Context: " + c + " userDataDetails: " + userDataDetails);

        updateMainStringFromStringArray(userDataDetails);
        writetoSharedInitial(c);
        readFromSharedInitial(c);

        LogHelper.logMessage("addNewUser", "Out: Context: " + c + " userDataInStringFormat: " + userDataInStringFormat);
    }

    //update isVerified flag
    public static void updateIsVerified(Context c, boolean isVerified){

        LogHelper.logMessage("updateIsVerified", "In: Context: " + c + " isVerified: " + isVerified);

        if(!"".equalsIgnoreCase(userDataInStringFormat)) {
            String[] userDataDetails = userDataInStringFormat.split("~");

            userDataDetails[4] = String.valueOf(isVerified);

            updateMainStringFromStringArray(userDataDetails);
            writetoSharedInitial(c);
            readFromSharedInitial(c);
        }
        LogHelper.logMessage("updateIsVerified", "Out: Context: " + c + " userDataInStringFormat: " + userDataInStringFormat);
    }

    //helper method: update main userDataInStringFormat from the newly arrived data
    public static void updateMainStringFromStringArray(String[] userDataDetails){
        LogHelper.logMessage("updateMainStringFromStringArray", "In: userDataDetails: " + userDataDetails);

        userDataInStringFormat = userDataDetails[0] + "~" + userDataDetails[1] + "~" + userDataDetails[2] + "~" + userDataDetails[3] + "~" + userDataDetails[4];

        LogHelper.logMessage("updateMainStringFromStringArray", "Out: userDataInStringFormat: " + userDataInStringFormat);
    }

    //clear Shared preference data
    public static void clearUserData(Context context) {

        LogHelper.logMessage("clearUserData", "In: context: " + context);

        userDataInStringFormat = "";
        writetoSharedInitial(context);

        LogHelper.logMessage("clearUserData", "Out: userDataInStringFormat: " + userDataInStringFormat);
    }

    public static void clearCart(Context context){
        LogHelper.logMessage("clearCart", "In: context: " + context);

        SharedPreferences sharedPref = context.getSharedPreferences("cartDataSharedPreference",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putStringSet("cartItemsSet",null);
        editor.apply();

        LogHelper.logMessage("clearCart", "Out: cartItemsSet: " + sharedPref.getStringSet("cartItemsSet", new HashSet<String>()));

    }

}
