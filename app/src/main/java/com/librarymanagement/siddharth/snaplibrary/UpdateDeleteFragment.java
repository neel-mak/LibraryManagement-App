package com.librarymanagement.siddharth.snaplibrary;


import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.librarymanagement.siddharth.snaplibrary.helper.CallAddBook;
import com.librarymanagement.siddharth.snaplibrary.helper.CallDeleteBook;
import com.librarymanagement.siddharth.snaplibrary.helper.CallISBNLookup;
import com.librarymanagement.siddharth.snaplibrary.helper.CallISearchForUpdate;
import com.librarymanagement.siddharth.snaplibrary.helper.CallUpdateBook;
import com.librarymanagement.siddharth.snaplibrary.helper.Constants;
import com.librarymanagement.siddharth.snaplibrary.helper.ExceptionMessageHandler;
import com.librarymanagement.siddharth.snaplibrary.helper.LogHelper;
import com.librarymanagement.siddharth.snaplibrary.helper.RequestClass;
import com.librarymanagement.siddharth.snaplibrary.helper.SharedData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;


public class UpdateDeleteFragment extends Fragment {


    public static EditText updateFragmentBookAuthor;
    public static EditText updateFragmentBookTitle;
    public static EditText updateFragmentBookPublisher;
    public static EditText updateFragmentCallNumber;
    public static EditText updateFragmentBookYear;
    public static EditText updateFragmentBookLocation;
    public static EditText updateFragmentBookCopies;
    public static EditText updateFragmentBookKeywords;
    public static TextView updateFragmentBookStatus;
    public static TextView updateFragmentAvailableCopies;
    public static ImageView updateFragmentImageView;

    public Button updateFragmentUpdateBtn;
    public Button updateFragmentDeleteBtn;
    private static View mUpdateBookProgressView;
    private static View mUpdateBookView;

    int position;
    BookItem currentBook;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        if(activity != null && activity.getCurrentFocus() != null && activity.getCurrentFocus().getWindowToken() != null) {
            final InputMethodManager inputManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), false ? InputMethodManager.SHOW_FORCED : InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        LogHelper.logMessage("Apoorv","Came to update delete fragment"+getArguments().getInt("book_obj_position_in_list"));
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_update_delete, container, false);

        updateFragmentBookAuthor = (EditText) view.findViewById(R.id.update_Fragment_book_author);
        updateFragmentBookTitle = (EditText) view.findViewById(R.id.Update_Fragment_book_title);
        updateFragmentBookPublisher = (EditText) view.findViewById(R.id.update_Fragment_book_publisher);
        updateFragmentCallNumber = (EditText) view.findViewById(R.id.update_Fragment_Call_Number);
        updateFragmentBookYear = (EditText) view.findViewById(R.id.update_Fragment_book_year);
        updateFragmentBookLocation = (EditText) view.findViewById(R.id.update_Fragment_book_location);
        updateFragmentBookCopies = (EditText) view.findViewById(R.id.update_Fragment_book_copies);
        updateFragmentBookKeywords = (EditText) view.findViewById(R.id.update_Fragment_book_keywords);
        updateFragmentBookStatus = (TextView) view.findViewById(R.id.Update_Fragment_book_status);
        updateFragmentAvailableCopies = (TextView) view.findViewById(R.id.Update_Fragment_books_available);

        updateFragmentImageView = (ImageView) view.findViewById(R.id.update_fragment_imageView);

        updateFragmentUpdateBtn = (Button)view.findViewById(R.id.update_fragment_update_btn);
        updateFragmentUpdateBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                try {
                    attemptUpdateBook();
                } catch (Exception e) {
                    ExceptionMessageHandler.handleError(getActivity(), Constants.GENERIC_ERROR_MSG, e, null);
                }
            }
        });

        updateFragmentDeleteBtn = (Button)view.findViewById(R.id.update_fragment_delete_btn);
        updateFragmentDeleteBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                try {
                    attemptDeleteBook();
                } catch (Exception e) {
                    ExceptionMessageHandler.handleError(getActivity(), Constants.GENERIC_ERROR_MSG, e, null);
                }
            }
        });

        try {

            position =  getArguments().getInt("book_obj_position_in_list");
            currentBook = ListFragment.bookItemList.get(position);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("searchType", "byId");
            jsonObject.put("searchParameters", new JSONObject().put("id", currentBook.Book_Id));

            HashMap<String, Object> params = new HashMap<String, Object>();
            params.put(Constants.REQUEST_JSON, jsonObject);
            params.put(Constants.ACTION, Constants.ACTION_LOAD_UPDATE);
            params.put(Constants.ACTIVITY, this.getActivity());
            params.put(Constants.FRAGMENT, this);
            params.put(Constants.VIEW, this.getView());
            params.put(Constants.CONTEXT, this.getContext());

            RequestClass.startRequestQueue();
            new CallISearchForUpdate().process(params);

        }catch (JSONException e){
            e.printStackTrace();
        }
        getActivity().setTitle("Update or Delete Book");
        mUpdateBookProgressView = view.findViewById(R.id.add_book_progress);
        mUpdateBookView = view.findViewById(R.id.add_book_scrollview);
        getActivity().setTitle("Update book");
        return view;
    }

    public void attemptDeleteBook(){
        LogHelper.logMessage("attemptDeleteBook" , "start");

        try{
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", currentBook.Book_Id);

            HashMap<String, Object> params = new HashMap<String, Object>();
            params.put(Constants.REQUEST_JSON, jsonObject);
            params.put(Constants.ACTION, Constants.ACTION_DELETE_BOOK);
            params.put(Constants.ACTIVITY, this.getActivity());
            params.put(Constants.FRAGMENT, this);
            params.put(Constants.VIEW, this.getView());
            params.put(Constants.CONTEXT, this.getContext());

            RequestClass.startRequestQueue();
            new CallDeleteBook().process(params);

        }catch (JSONException e){
            e.printStackTrace();
        }

    }

    public void attemptUpdateBook(){
        LogHelper.logMessage("attemptUpdateBook" , "start");
        // Reset errors.
        updateFragmentBookAuthor.setError(null);
        updateFragmentBookTitle.setError(null);
        updateFragmentBookPublisher.setError(null);
        updateFragmentCallNumber.setError(null);
        updateFragmentBookYear.setError(null);
        updateFragmentBookLocation.setError(null);
        updateFragmentBookCopies.setError(null);
        updateFragmentBookKeywords.setError(null);
        updateFragmentBookStatus.setError(null);
        updateFragmentAvailableCopies.setError(null);

        // Get values from UI.
        String updateFragmentBookAuthorString = updateFragmentBookAuthor.getText().toString();
        String updateFragmentBookTitleString = updateFragmentBookTitle.getText().toString();
        String updateFragmentBookPublisherString = updateFragmentBookPublisher.getText().toString();
        String updateFragmentCallNumberString = updateFragmentCallNumber.getText().toString();
        String updateFragmentBookYearString = updateFragmentBookYear.getText().toString();
        String updateFragmentBookLocationString = updateFragmentBookLocation.getText().toString();
        String updateFragmentBookCopiesString = updateFragmentBookCopies.getText().toString();
        String updateFragmentBookKeywordsString = updateFragmentBookKeywords.getText().toString();
        String updateFragmentBookStatusString = updateFragmentBookStatus.getText().toString();
        String updateFragmentAvailableCopiesString = updateFragmentAvailableCopies.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid email address.
        if (TextUtils.isEmpty(updateFragmentBookAuthorString)) {
            updateFragmentBookAuthor.setError(getString(R.string.error_field_required));
            focusView = updateFragmentBookAuthor;
            cancel = true;
        } else if (TextUtils.isEmpty(updateFragmentBookTitleString)) {
            updateFragmentBookTitle.setError(getString(R.string.error_field_required));
            focusView = updateFragmentBookTitle;
            cancel = true;
        } else if (TextUtils.isEmpty(updateFragmentBookPublisherString)) {
            updateFragmentBookPublisher.setError(getString(R.string.error_field_required));
            focusView = updateFragmentBookPublisher;
            cancel = true;
        } else if (TextUtils.isEmpty(updateFragmentCallNumberString)) {
            updateFragmentCallNumber.setError(getString(R.string.error_field_required));
            focusView = updateFragmentCallNumber;
            cancel = true;
        } else if (TextUtils.isEmpty(updateFragmentBookLocationString)) {
            updateFragmentBookLocation.setError(getString(R.string.error_field_required));
            focusView = updateFragmentBookLocation;
            cancel = true;
        } else if (TextUtils.isEmpty(updateFragmentBookCopiesString)) {
            updateFragmentBookCopies.setError(getString(R.string.error_field_required));
            focusView = updateFragmentBookCopies;
            cancel = true;
        }
//        else if (TextUtils.isEmpty(updateFragmentBookKeywordsString)) {
//            updateFragmentBookKeywords.setError(getString(R.string.error_field_required));
//            focusView = updateFragmentBookKeywords;
//            cancel = true;
//        }

        if(cancel){
            focusView.requestFocus();
        }else {
            showProgress(true);

            try {
                List<String> keywordsArray = new ArrayList<String>(Arrays.asList(updateFragmentBookKeywordsString.split("\\s*,\\s*")));
                JSONArray jsonArray = new JSONArray();
                for (int i = 0; i < keywordsArray.size(); i++) {
                    jsonArray.put(keywordsArray.get(i));
                }

                JSONObject jsonObject = new JSONObject();
                SharedData.readFromSharedInitial(this.getActivity().getApplicationContext());
                String[] userDetails = SharedData.getUserDetails();
                jsonObject.put("id", currentBook.Book_Id);
                jsonObject.put("author", updateFragmentBookAuthorString);
                jsonObject.put("title", updateFragmentBookTitleString);
                jsonObject.put("numOfCopies", updateFragmentBookCopiesString);
                jsonObject.put("updatedBy", userDetails[1]);
                jsonObject.put("locationInLibrary", updateFragmentBookLocationString);
                jsonObject.put("callNumber", updateFragmentCallNumberString);
                jsonObject.put("publisher", updateFragmentBookPublisherString);
                jsonObject.put("yearOfPublication", updateFragmentBookYearString);
                jsonObject.put("currentStatus", updateFragmentBookStatusString);
                jsonObject.put("keywords", jsonArray);
                //jsonObject.put("coverageImage", imageByteArray);//imageByteArray
                //jsonObject.put("isbn", toBeDeleted.getText());

                LogHelper.logMessage("Siddharth", "author:" + updateFragmentBookAuthorString
                        + "  title:" + updateFragmentBookTitleString
                        + "  numOfCopies:" + updateFragmentBookCopiesString
                        + "  createdBy:" + userDetails[1]
                        + "  callNumber:" + updateFragmentCallNumberString
                        + "  publisher:" + updateFragmentBookPublisherString
                        + "  yearOfPublication:" + updateFragmentBookYearString
                        + "  locationInLibrary:" + updateFragmentBookLocationString
                        + "  currentStatus:" + updateFragmentBookStatusString
                        + "  updateFragmentAvailableCopiesString" + updateFragmentAvailableCopiesString
                        + "  keywords:" + keywordsArray
                );

                HashMap<String, Object> params = new HashMap<String, Object>();
                params.put(Constants.REQUEST_JSON, jsonObject);
                params.put(Constants.ACTION, Constants.ACTION_UPDATE_BOOK);
                params.put(Constants.ACTIVITY, this.getActivity());
                params.put(Constants.FRAGMENT, this);
                params.put(Constants.VIEW, this.getView());
                params.put(Constants.CONTEXT, this.getContext());

                RequestClass.startRequestQueue();
                new CallUpdateBook().process(params);
            }catch (JSONException e){
                e.printStackTrace();
            }
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
        if(mUpdateBookProgressView != null)
            mUpdateBookProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
        if(mUpdateBookView != null)
            mUpdateBookView.setVisibility(show ? View.GONE : View.VISIBLE);
//        }
    }



}
