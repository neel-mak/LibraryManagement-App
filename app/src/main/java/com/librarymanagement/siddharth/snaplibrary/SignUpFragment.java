package com.librarymanagement.siddharth.snaplibrary;


import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.librarymanagement.siddharth.snaplibrary.helper.CallLogin;
import com.librarymanagement.siddharth.snaplibrary.helper.CallRegister;
import com.librarymanagement.siddharth.snaplibrary.helper.Constants;
import com.librarymanagement.siddharth.snaplibrary.helper.ExceptionMessageHandler;
import com.librarymanagement.siddharth.snaplibrary.helper.RequestClass;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;


/**
 * A simple {@link Fragment} subclass.
 */
public class SignUpFragment extends Fragment  {

    public static EditText signUpEmailAddress;
    public EditText signUpPassword;
    public EditText signUpSjsuId;
    public Button signUpButton;
    private static View mSignupProgressView;
    private static View mSignupView;

    public SignUpFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sign_up, container, false);

        signUpEmailAddress = (EditText) view.findViewById(R.id.signUpEmailAddress);
        signUpPassword = (EditText) view.findViewById(R.id.signUpPassword);
        signUpSjsuId = (EditText) view.findViewById(R.id.signUpSjsuId);

        signUpButton = (Button) view.findViewById(R.id.btn_signup);
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    attemptSignUp();
                } catch (Exception e) {
                    ExceptionMessageHandler.handleError(getActivity(), Constants.GENERIC_ERROR_MSG, e, null);
                }
            }
        });

        mSignupProgressView = view.findViewById(R.id.signup_progress);
        mSignupView = view.findViewById(R.id.signup_submit);

        return view;
    }

    public void attemptSignUp() throws JSONException {

        // Reset errors.
        signUpEmailAddress.setError(null);
        signUpPassword.setError(null);
        signUpSjsuId.setError(null);

        // Get values from UI.
        String email = signUpEmailAddress.getText().toString();
        String password = signUpPassword.getText().toString();
        String sjsuId = signUpSjsuId.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            signUpEmailAddress.setError(getString(R.string.error_field_required));
            focusView = signUpEmailAddress;
            cancel = true;
        } else if (!Constants.VALID_EMAIL_ADDRESS_REGEX .matcher(email).find()) {
            signUpEmailAddress.setError(getString(R.string.error_invalid_email));
            focusView = signUpEmailAddress;
            cancel = true;
        }
        // Check for a valid password, if the user entered one.
        else if (TextUtils.isEmpty(password)) {
            signUpPassword.setError(getString(R.string.error_field_required));
            focusView = signUpPassword;
            cancel = true;
        }else if(TextUtils.isEmpty(sjsuId)){
            signUpSjsuId.setError(getString(R.string.error_field_required));
            focusView = signUpSjsuId;
            cancel = true;
        }else if(sjsuId.length() != 6){
            signUpSjsuId.setError(getString(R.string.error_incorrect_sjsuid));
            focusView = signUpSjsuId;
            cancel = true;
        }

        if(cancel){
            focusView.requestFocus();
        }else{
            showProgress(true);

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("email", signUpEmailAddress.getText());
            jsonObject.put("password", signUpPassword.getText());
            jsonObject.put("universityId", signUpSjsuId.getText());

            HashMap<String, Object> params = new HashMap<String, Object>();
            params.put(Constants.REQUEST_JSON, jsonObject);
            params.put(Constants.ACTION, Constants.ACTION_REGISTER);
            params.put(Constants.ACTIVITY, this.getActivity());
            params.put(Constants.FRAGMENT, this);
            params.put(Constants.VIEW, this.getView());
            params.put(Constants.CONTEXT, this.getContext());

            RequestClass.startRequestQueue();
            new CallRegister().processRegisterRequest(params);
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
        mSignupProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
        mSignupView.setVisibility(show ? View.GONE : View.VISIBLE);
//        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }



}
