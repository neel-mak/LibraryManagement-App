package com.librarymanagement.siddharth.snaplibrary.helper;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.view.WindowManager;
import android.widget.Toast;

import java.util.HashMap;

/**
 * Created by siddharthdaftari on 11/19/17.
 */

public class ExceptionMessageHandler {

    public static Context context = null;

    public static void handleError(Context c, String message, Exception e, HashMap<String, Object> extraParams) {
        if (message == null || message.equals("")) {
            message = Constants.GENERIC_ERROR_MSG;
        }
        LogHelper.logMessage("Siddharth", "-----> EXCEPTION: " + message + "  -----> context: " + context);
        if (e != null) {
            e.printStackTrace();
        }
        if (context != null){
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
        }else if(extraParams != null && extraParams.containsKey("activity")){
            Toast.makeText((Activity)extraParams.get("activity"), message, Toast.LENGTH_SHORT).show();
        }

    }
}
