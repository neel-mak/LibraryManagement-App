package com.librarymanagement.siddharth.snaplibrary;


import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.librarymanagement.siddharth.snaplibrary.helper.CallRegister;
import com.librarymanagement.siddharth.snaplibrary.helper.CallVerifyUser;
import com.librarymanagement.siddharth.snaplibrary.helper.Constants;
import com.librarymanagement.siddharth.snaplibrary.helper.ExceptionMessageHandler;
import com.librarymanagement.siddharth.snaplibrary.helper.LogHelper;
import com.librarymanagement.siddharth.snaplibrary.helper.RequestClass;
import com.librarymanagement.siddharth.snaplibrary.helper.SharedData;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;


/**
 * A simple {@link Fragment} subclass.
 */
public class ConfirmationFragment extends Fragment {

    public static EditText verifyNumber;
    public Button verifyBtn;
    private static View mVerifyProgressView;
    private static View mVerifyView;

    public ConfirmationFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_confirmation_, container, false);

        verifyNumber = (EditText) view.findViewById(R.id.verify_number);

        Button verifyButton = (Button)view.findViewById(R.id.verifyBtn);
        verifyButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                try {
                    attemptVerification();
                } catch (Exception e) {
                    ExceptionMessageHandler.handleError(getActivity(), Constants.GENERIC_ERROR_MSG, e, null);
                }
            }
        });
        mVerifyProgressView = view.findViewById(R.id.verify_progress);
        mVerifyView = view.findViewById(R.id.verify_submit);

        return view;
    }

    public void attemptVerification() throws JSONException {

        // Reset errors.
        verifyNumber.setError(null);

        // Get values from UI.
        String verifyCode = verifyNumber.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid email address.
        if (TextUtils.isEmpty(verifyCode)) {
            verifyNumber.setError(getString(R.string.error_field_required));
            focusView = verifyNumber;
            cancel = true;
        } else if (verifyNumber.length() != 5) {
            verifyNumber.setError(getString(R.string.error_invalid_verification_number));
            focusView = verifyNumber;
            cancel = true;
        }

        if(cancel){
            focusView.requestFocus();
        }else {
            showProgress(true);

            JSONObject jsonObject = new JSONObject();
            SharedData.readFromSharedInitial(this.getActivity().getApplicationContext());
            String[] userDetails = SharedData.getUserDetails();
            jsonObject.put("email", userDetails[1]);
            jsonObject.put("verificationCode", verifyNumber.getText());

            LogHelper.logMessage("Siddharth", "email:" + userDetails[1] + "  verificationCode:" + verifyNumber.getText());

            HashMap<String, Object> params = new HashMap<String, Object>();
            params.put(Constants.REQUEST_JSON, jsonObject);
            params.put(Constants.ACTION, Constants.ACTION_VERIFY_USER);
            params.put(Constants.ACTIVITY, this.getActivity());
            params.put(Constants.FRAGMENT, this);
            params.put(Constants.VIEW, this.getView());
            params.put(Constants.CONTEXT, this.getContext());

            RequestClass.startRequestQueue();
            new CallVerifyUser().processVerifyRequest(params);
        }
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    public static void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
//            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);
//
//            mLoginView.setVisibility(show ? View.GONE : View.VISIBLE);
//            mLoginView.animate().setDuration(shortAnimTime).alpha(
//                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
//                @Override
//                public void onAnimationEnd(Animator animation) {
//                    mLoginView.setVisibility(show ? View.GONE : View.VISIBLE);
//                }
//            });
//
//            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
//            mProgressView.animate().setDuration(shortAnimTime).alpha(
//                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
//                @Override
//                public void onAnimationEnd(Animator animation) {
//                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
//                }
//            });
//        } else {
        // The ViewPropertyAnimator APIs are not available, so simply show
        // and hide the relevant UI components.
        if(mVerifyProgressView != null)
            mVerifyProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
        if(mVerifyView != null)
            mVerifyView.setVisibility(show ? View.GONE : View.VISIBLE);
//        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        if(activity != null && activity.getCurrentFocus() != null && activity.getCurrentFocus().getWindowToken() != null) {
            final InputMethodManager inputManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), false ? InputMethodManager.SHOW_FORCED : InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

}
