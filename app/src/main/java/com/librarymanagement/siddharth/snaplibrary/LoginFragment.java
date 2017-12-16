package com.librarymanagement.siddharth.snaplibrary;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.librarymanagement.siddharth.snaplibrary.helper.CallLogin;
import com.librarymanagement.siddharth.snaplibrary.helper.Constants;
import com.librarymanagement.siddharth.snaplibrary.helper.ExceptionMessageHandler;
import com.librarymanagement.siddharth.snaplibrary.helper.RequestClass;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class LoginFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>,View.OnClickListener{

    private EditText mEmailView;
    public static EditText mPsdView;
    private static View mProgressView;
    private static View mLoginView;
    private TextView mSignUpVIew;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view=inflater.inflate(R.layout.fragment_login, container, false);


        mEmailView = (EditText) view.findViewById(R.id.email_id);
        mPsdView = (EditText) view.findViewById(R.id.password);
        mPsdView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_NULL) {
                    try {
                        attemptLogin();
                    } catch (Exception e) {
                        ExceptionMessageHandler.handleError(getContext(), Constants.GENERIC_ERROR_MSG, e, null);
                    }
                    return true;
                }
                return false;
            }
        });

        Button login = (Button) view.findViewById(R.id.btn_login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    attemptLogin();
                } catch (Exception e) {
                    ExceptionMessageHandler.handleError(getActivity(), Constants.GENERIC_ERROR_MSG, e, null);
                }
            }
        });

        mProgressView = view.findViewById(R.id.login_progress);
        mLoginView = view.findViewById(R.id.login_submit);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
    }

    private void initView(View view){
        mSignUpVIew = (TextView)view.findViewById(R.id.sign_up_textview);
        mSignUpVIew.setOnClickListener(this);

    }



    private void attemptLogin() throws JSONException {

        // Reset errors.
        mEmailView.setError(null);
        mPsdView.setError(null);

        // Store values at the time of the login attempt.
        String email_data = mEmailView.getText().toString();
        String password_data = mPsdView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid email address.
        if (TextUtils.isEmpty(email_data)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        } else if (!isEmailValid(email_data)) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
            cancel = true;
        }
        // Check for a valid password, if the user entered one.
        else if (TextUtils.isEmpty(password_data)) {
            mPsdView.setError(getString(R.string.error_field_required));
            focusView = mPsdView;
            cancel = true;
        }
        else if (!isPasswordValid(password_data)) {
            mPsdView.setError(getString(R.string.error_invalid_password));
            focusView = mPsdView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            showProgress(true);

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("email", mEmailView.getText());
            jsonObject.put("password", mPsdView.getText());

            HashMap<String, Object> params = new HashMap<String, Object>();
            params.put(Constants.REQUEST_JSON, jsonObject);
            params.put(Constants.ACTION, Constants.ACTION_UPDATE_LOGIN);
            params.put(Constants.ACTIVITY, this.getActivity());
            params.put(Constants.FRAGMENT, this);
            params.put(Constants.VIEW, this.getView());
            params.put(Constants.CONTEXT, this.getContext());

            RequestClass.startRequestQueue();
            new CallLogin().processLoginRequest(params);
            //mAuthTask = new UserLoginTask(email_data, password_data);
            //mAuthTask.execute((Void) null);

        }

    }

    private boolean isEmailValid(String email_data) {

        Matcher matcher = Constants.VALID_EMAIL_ADDRESS_REGEX .matcher(email_data);
        return matcher.find();
    }

    private boolean isPasswordValid(String password) {

        //return password.length() > 4;
        return true;
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
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginView.setVisibility(show ? View.GONE : View.VISIBLE);
//        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    private void changeFragment(){
        getFragmentManager().beginTransaction().replace(R.id.fragment,new SignUpFragment()).addToBackStack(null).commit();
    }

    @Override
    public void onClick(View v) {
        changeFragment();
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
