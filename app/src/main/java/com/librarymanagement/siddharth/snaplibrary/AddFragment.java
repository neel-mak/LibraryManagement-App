package com.librarymanagement.siddharth.snaplibrary;


import android.Manifest;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.librarymanagement.siddharth.snaplibrary.helper.CallAddBook;
import com.librarymanagement.siddharth.snaplibrary.helper.CallISBNLookup;
import com.librarymanagement.siddharth.snaplibrary.helper.CallVerifyUser;
import com.librarymanagement.siddharth.snaplibrary.helper.Constants;
import com.librarymanagement.siddharth.snaplibrary.helper.ExceptionMessageHandler;
import com.librarymanagement.siddharth.snaplibrary.helper.FragmentIntentIntegrator;
import com.librarymanagement.siddharth.snaplibrary.helper.LogHelper;
import com.librarymanagement.siddharth.snaplibrary.helper.RequestClass;
import com.librarymanagement.siddharth.snaplibrary.helper.SharedData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static android.app.Activity.RESULT_OK;


public class AddFragment extends Fragment {

    public static EditText addFragmentBookAuthor;
    public static EditText addFragmentBookTitle;
    public static EditText addFragmentBookPublisher;
    public static EditText addFragmentCallNumber;
    public static EditText addFragmentBookYear;
    public static EditText addFragmentBookLocation;
    public static EditText addFragmentBookCopies;
    public static EditText addFragmentBookKeywords;
    public static EditText addFragmentBookStatus;


    public Button addFragmentAddBtn;
    public Button addFragmentUploadBtn;
    public Button addFragmentRemoveFileBtn;
    public Button addFragmentScanBarcodeBtn;
    private static View mAddBookProgressView;
    private static View mAddBookView;
    public static ImageView addFragmentImageView;

    public static EditText toBeDeleted;

    public byte[] imageByteArray;


    private static final int RESULT_SELECT_IMAGE = 1;
    private static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 123;
    private static final int MY_PERMISSIONS_REQUEST_CAMERA = 456;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add, container, false);

        addFragmentBookAuthor = (EditText) view.findViewById(R.id.addFragment_book_author);
        addFragmentBookTitle = (EditText) view.findViewById(R.id.add_Fragment_book_title);
        addFragmentBookPublisher = (EditText) view.findViewById(R.id.add_Fragment_book_publisher);
        addFragmentCallNumber = (EditText) view.findViewById(R.id.add_Fragment_Call_Number);
        addFragmentBookYear = (EditText) view.findViewById(R.id.add_Fragment_book_year);
        addFragmentBookLocation = (EditText) view.findViewById(R.id.add_Fragment_book_location);
        addFragmentBookCopies = (EditText) view.findViewById(R.id.addFragment_book_copies);
        addFragmentBookKeywords = (EditText) view.findViewById(R.id.addFragment_book_keywords);
        addFragmentBookStatus = (EditText) view.findViewById(R.id.addFragment_book_status);
        addFragmentImageView = (ImageView) view.findViewById(R.id.add_fragment_imageView);

        toBeDeleted = (EditText) view.findViewById(R.id.toBeDeleted);

        Button addFragmentAddBtn = (Button)view.findViewById(R.id.addFragment_add_btn);
        addFragmentAddBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                try {
                    attemptAddUser();
                } catch (Exception e) {
                    ExceptionMessageHandler.handleError(getActivity(), Constants.GENERIC_ERROR_MSG, e, null);
                }
            }
        });

        Button addFragmentUploadBtn = (Button)view.findViewById(R.id.addFragment_upload_btn);
        addFragmentUploadBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                try {
                    selectImage();
                } catch (Exception e) {
                    ExceptionMessageHandler.handleError(getActivity(), Constants.GENERIC_ERROR_MSG, e, null);
                }
            }
        });
        Button addFragmentRemoveFileBtn = (Button)view.findViewById(R.id.addFragment_remove_file_btn);
        addFragmentRemoveFileBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                try {
                    removeImage();
                } catch (Exception e) {
                    ExceptionMessageHandler.handleError(getActivity(), Constants.GENERIC_ERROR_MSG, e, null);
                }
            }
        });
        Button addFragmentScanBarcodeBtn = (Button)view.findViewById(R.id.addFragment_scan_barcode_btn);
        addFragmentScanBarcodeBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                try {
                    processBarCode();
                } catch (Exception e) {
                    ExceptionMessageHandler.handleError(getActivity(), Constants.GENERIC_ERROR_MSG, e, null);
                }
            }
        });
        mAddBookProgressView = view.findViewById(R.id.add_book_progress);
        mAddBookView = view.findViewById(R.id.add_book_scrollview);
        getActivity().setTitle("Add Books for checkout");
        return view;
    }

    public void processBarCode(){
//        IntentIntegrator.initiateScan(this,
//                "Warning",
//                "ZXing Barcode Scanner is not installed, download?",
//                "Yes", "No",
//                "PRODUCT_MODE");

        LogHelper.logMessage("processBarCode" , "start");

        if (ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {

            LogHelper.logMessage("processBarCode" , "ContextCompat.checkSelfPermission(getActivity(),\n" +
                    "                Manifest.permission.CAMERA)\n" +
                    "                != PackageManager.PERMISSION_GRANTED");

            requestPermissions(new String[]{Manifest.permission.CAMERA},
                    MY_PERMISSIONS_REQUEST_CAMERA);
        }else{

            LogHelper.logMessage("processBarCode" , "else:: ContextCompat.checkSelfPermission(getActivity(),\n" +
                    "                Manifest.permission.CAMERA)\n" +
                    "                != PackageManager.PERMISSION_GRANTED");

            FragmentIntentIntegrator fragmentIntentIntegrator = new FragmentIntentIntegrator(this);
            fragmentIntentIntegrator.initiateScan();

            return;
        }
    }

    private void removeImage() {
        if(addFragmentImageView!=null){
            addFragmentImageView.invalidate();
            addFragmentImageView.setImageBitmap(null);
            addFragmentImageView.setImageDrawable(null);
            addFragmentImageView.setBackground(null);
            addFragmentImageView.invalidate();
            imageByteArray=null;

            Toast.makeText(getActivity().getApplicationContext(),"Cover removed",Toast.LENGTH_SHORT).show();
        }
    }

    /*
    * Called when some image from the album is picked
    * */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);
        LogHelper.logMessage("Apoorv","Came back to main");
        LogHelper.logMessage("FILE_UPLOAD" , "Photo picked");
        if (requestCode == RESULT_SELECT_IMAGE && resultCode == RESULT_OK && data != null){
            //set the selected image to image variable

            Uri image = data.getData();
            addFragmentImageView.setImageURI(null);
            addFragmentImageView.setImageURI(image);
            addFragmentImageView.invalidate();
            Bitmap captureBmp = null;

            try {
                captureBmp = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), image );
            } catch (IOException e) {
                e.printStackTrace();
            }

            LogHelper.logMessage("Bitmap", "width:" + captureBmp.getWidth() + " height: " +captureBmp.getHeight());
            addFragmentImageView.setImageBitmap(captureBmp);

            //String imgString = Base64.encodeToString(getBytesFromBitmap(captureBmp), Base64.NO_WRAP);
            imageByteArray = getBytesFromBitmap(captureBmp);

            Toast.makeText(getActivity().getApplicationContext(),"Cover uploaded",Toast.LENGTH_SHORT).show();

        }else if(requestCode == IntentIntegrator.REQUEST_CODE && resultCode == RESULT_OK){
            IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
            LogHelper.logMessage("onActivityResult", result.getContents());
            String isbnNumber = result.getContents();
            toBeDeleted.setText(result.getContents());

            try {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("isbn", isbnNumber);

                HashMap<String, Object> params = new HashMap<String, Object>();
                params.put(Constants.REQUEST_JSON, jsonObject);
                params.put(Constants.ACTION, Constants.ACTION_LOOKUP_ISBN);
                params.put(Constants.ACTIVITY, this.getActivity());
                params.put(Constants.FRAGMENT, this);
                params.put(Constants.VIEW, this.getView());
                params.put(Constants.CONTEXT, this.getContext());

                RequestClass.startRequestQueue();
                new CallISBNLookup().processIsbnRequest(params);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    // convert from bitmap to byte array
    public byte[] getBytesFromBitmap(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        LogHelper.logMessage("onRequestPermissionsResult", "requestCode: " + requestCode + " grantResults[0]: " + grantResults[0]);
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE:
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    LogHelper.logMessage("READ_STORAGE" , "Permission granted");
                    //open album to select image
                    Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(galleryIntent, RESULT_SELECT_IMAGE);

                } else {
                    LogHelper.logMessage("READ_STORAGE" , "Permission denied");
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            case MY_PERMISSIONS_REQUEST_CAMERA:
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    LogHelper.logMessage("CAMERA_PERMISSION" , "Permission granted");
                    //open album to select image
                    FragmentIntentIntegrator fragmentIntentIntegrator = new FragmentIntentIntegrator(this);
                    fragmentIntentIntegrator.initiateScan();

                } else {
                    LogHelper.logMessage("CAMERA_PERMISSION" , "Permission denied");
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;


            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    private void selectImage() {

        LogHelper.logMessage("selectImage" , "start");

        if (ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            LogHelper.logMessage("selectImage" , "ContextCompat.checkSelfPermission(getActivity(),\n" +
                    "                Manifest.permission.READ_EXTERNAL_STORAGE)\n" +
                    "                != PackageManager.PERMISSION_GRANTED");

            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
        }else{

            LogHelper.logMessage("selectImage" , "else:: ContextCompat.checkSelfPermission(getActivity(),\n" +
                    "                Manifest.permission.READ_EXTERNAL_STORAGE)\n" +
                    "                != PackageManager.PERMISSION_GRANTED");

            Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(galleryIntent, RESULT_SELECT_IMAGE);
        }
    }

    public void attemptAddUser() throws JSONException {

        // Reset errors.
        addFragmentBookAuthor.setError(null);
        addFragmentBookTitle.setError(null);
        addFragmentBookPublisher.setError(null);
        addFragmentCallNumber.setError(null);
        addFragmentBookYear.setError(null);
        addFragmentBookLocation.setError(null);
        addFragmentBookCopies.setError(null);
        addFragmentBookKeywords.setError(null);
        addFragmentBookStatus.setError(null);

        // Get values from UI.
        String addFragmentBookAuthorString = addFragmentBookAuthor.getText().toString();
        String addFragmentBookTitleString = addFragmentBookTitle.getText().toString();
        String addFragmentBookPublisherString = addFragmentBookPublisher.getText().toString();
        String addFragmentCallNumberString = addFragmentCallNumber.getText().toString();
        String addFragmentBookYearString = addFragmentBookYear.getText().toString();
        String addFragmentBookLocationString = addFragmentBookLocation.getText().toString();
        String addFragmentBookCopiesString = addFragmentBookCopies.getText().toString();
        String addFragmentBookKeywordsString = addFragmentBookKeywords.getText().toString();
        String addFragmentBookStatusString = addFragmentBookStatus.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid email address.
        if (TextUtils.isEmpty(addFragmentBookAuthorString)) {
            addFragmentBookAuthor.setError(getString(R.string.error_field_required));
            focusView = addFragmentBookAuthor;
            cancel = true;
        } else if (TextUtils.isEmpty(addFragmentBookTitleString)) {
            addFragmentBookTitle.setError(getString(R.string.error_field_required));
            focusView = addFragmentBookTitle;
            cancel = true;
        }
//        } else if (TextUtils.isEmpty(addFragmentBookPublisherString)) {
//            addFragmentBookPublisher.setError(getString(R.string.error_field_required));
//            focusView = addFragmentBookPublisher;
//            cancel = true;
//        } else if (TextUtils.isEmpty(addFragmentCallNumberString)) {
//            addFragmentCallNumber.setError(getString(R.string.error_field_required));
//            focusView = addFragmentCallNumber;
//            cancel = true;
//        } else if (TextUtils.isEmpty(addFragmentBookYearString)) {
//            addFragmentBookYear.setError(getString(R.string.error_field_required));
//            focusView = addFragmentBookYear;
//            cancel = true;
//        } else if (TextUtils.isEmpty(addFragmentBookLocationString)) {
//            addFragmentBookLocation.setError(getString(R.string.error_field_required));
//            focusView = addFragmentBookLocation;
//            cancel = true;
//        }
        else if (TextUtils.isEmpty(addFragmentBookCopiesString)) {
            addFragmentBookCopies.setError(getString(R.string.error_field_required));
            focusView = addFragmentBookCopies;
            cancel = true;
        }
//        else if (TextUtils.isEmpty(addFragmentBookKeywordsString)) {
//            addFragmentBookKeywords.setError(getString(R.string.error_field_required));
//            focusView = addFragmentBookKeywords;
//            cancel = true;
//        } else if (TextUtils.isEmpty(addFragmentBookStatusString)) {
//            addFragmentBookStatus.setError(getString(R.string.error_field_required));
//            focusView = addFragmentBookStatus;
//            cancel = true;
//        }

        if(cancel){
            focusView.requestFocus();
        }else {
            showProgress(true);
            List<String> keywordsArray = new ArrayList<String>(Arrays.asList(addFragmentBookKeywordsString.split("\\s*,\\s*")));
            JSONArray jsonArray = new JSONArray();
            for(int i=0; i<keywordsArray.size(); i++){
                jsonArray.put(keywordsArray.get(i));
            }

            JSONObject jsonObject = new JSONObject();
            SharedData.readFromSharedInitial(this.getActivity().getApplicationContext());
            String[] userDetails = SharedData.getUserDetails();
            jsonObject.put("author", addFragmentBookAuthorString);
            jsonObject.put("title", addFragmentBookTitleString);
            jsonObject.put("numOfCopies", addFragmentBookCopiesString);
            jsonObject.put("createdBy", userDetails[1]);
            jsonObject.put("updatedBy", userDetails[1]);
            jsonObject.put("callNumber", addFragmentCallNumberString);
            jsonObject.put("publisher", addFragmentBookPublisherString);
            jsonObject.put("yearOfPublication", addFragmentBookYearString);
            jsonObject.put("locationInLibrary", addFragmentBookLocationString);
            jsonObject.put("currentStatus", addFragmentBookStatusString);
            jsonObject.put("keywords", jsonArray);
            jsonObject.put("coverageImage", imageByteArray);//imageByteArray
            JSONArray jsonArray1 = new JSONArray();
            if(!"".equalsIgnoreCase("" + toBeDeleted.getText())){
                jsonArray1.put(toBeDeleted.getText());
            }
            jsonObject.put("isbn", jsonArray1);

            LogHelper.logMessage("Siddharth", "author:" + addFragmentBookAuthorString
                    + "  title:" + addFragmentBookTitleString
                    + "  numOfCopies:" + addFragmentBookCopiesString
                    + "  createdBy:" + userDetails[1]
                    + "  callNumber:" + addFragmentCallNumberString
                    + "  publisher:" + addFragmentBookPublisherString
                    + "  yearOfPublication:" + addFragmentBookYearString
                    + "  locationInLibrary:" + addFragmentBookLocationString
                    + "  currentStatus:" + addFragmentBookStatusString
                    + "  keywords:" + keywordsArray
                    + "  imageByteArray:" + imageByteArray
                    + "  isbn:" + jsonArray1
            );

            HashMap<String, Object> params = new HashMap<String, Object>();
            params.put(Constants.REQUEST_JSON, jsonObject);
            params.put(Constants.ACTION, Constants.ACTION_ADD_BOOK);
            params.put(Constants.ACTIVITY, this.getActivity());
            params.put(Constants.FRAGMENT, this);
            params.put(Constants.VIEW, this.getView());
            params.put(Constants.CONTEXT, this.getContext());

            RequestClass.startRequestQueue();
            new CallAddBook().processAddBook(params);
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
        if(mAddBookProgressView != null)
            mAddBookProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
        if(mAddBookView != null)
            mAddBookView.setVisibility(show ? View.GONE : View.VISIBLE);
//        }
    }

}
