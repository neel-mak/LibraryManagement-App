package com.librarymanagement.siddharth.snaplibrary;


import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.Toast;


import com.librarymanagement.siddharth.snaplibrary.helper.CallAddToWaitlistBook;
import com.librarymanagement.siddharth.snaplibrary.helper.CallISearchForUpdate;
import com.librarymanagement.siddharth.snaplibrary.helper.Constants;
import com.librarymanagement.siddharth.snaplibrary.helper.LogHelper;
import com.librarymanagement.siddharth.snaplibrary.helper.RequestClass;
import com.librarymanagement.siddharth.snaplibrary.helper.SharedData;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;


public class PatronCheckoutFragment extends Fragment {
    public static String bookId;
    public static TextView checkoutFragmentBookAuthor;
    public static TextView checkoutFragmentBookTitle;
    public static TextView checkoutFragmentBookPublisher;
    public static TextView checkoutFragmentCallNumber;
    public static TextView checkoutFragmentBookYear;
    public static TextView checkoutFragmentBookLocation;
    public static TextView checkoutFragmentBookCopies;
    public static TextView checkoutFragmentBookKeywords;
    public static TextView checkoutFragmentBookStatus;
    public static ImageView checkoutFragmentImageView;

    public Button checkoutFragmentAddtoCartBtn;
    private static View mUpdateBookProgressView;
    private static View mUpdateBookView;

    int position;
    PatronBookItem currentBook;
    ArrayList<PatronBookItem> booksInCart = new ArrayList<PatronBookItem>();
    public static Set<String> cartItemsSet;
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       // LogHelper.logMessage("Apoorv","Came to Patron Checkout fragment"+getArguments().getInt("book_obj_position_in_list"));
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_patron_checkout, container, false);


        checkoutFragmentBookAuthor = (TextView) view.findViewById(R.id.checkout_Fragment_book_author);
        checkoutFragmentBookTitle = (TextView) view.findViewById(R.id.checkout_Fragment_book_title);
        checkoutFragmentBookPublisher = (TextView) view.findViewById(R.id.checkout_Fragment_book_publisher);
        checkoutFragmentCallNumber = (TextView) view.findViewById(R.id.checkout_Fragment_Call_Number);
        checkoutFragmentBookYear = (TextView) view.findViewById(R.id.checkout_Fragment_book_year);
        checkoutFragmentBookLocation = (TextView) view.findViewById(R.id.checkout_Fragment_book_location);
        checkoutFragmentBookKeywords = (TextView) view.findViewById(R.id.checkout_Fragment_book_keywords);
        checkoutFragmentBookStatus = (TextView) view.findViewById(R.id.checkout_Fragment_book_status);

        checkoutFragmentAddtoCartBtn = (Button)view.findViewById(R.id.checkout_fragment_checkout_btn);

        checkoutFragmentAddtoCartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LogHelper.logMessage("Apoorv","Add to cart clicked"+bookId);
                // put if condition for available copies is 0 then don't allow to add to cart instead of
                // that call showAlert method which will add book to the waitlist
                // at the same time also put visibility for "add to cart button" to GONE
                if(bookId!=null) {
                    try {
                        addBookToCart(bookId);
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                }


            }
        });

        try {
            position = getArguments().getInt("book_obj_position_in_list");
            currentBook = PatronListFragment.bookItemList.get(position);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("searchType", "byId");
            jsonObject.put("searchParameters", new JSONObject().put("id", currentBook.Book_Id));

            HashMap<String, Object> params = new HashMap<String, Object>();
            params.put(Constants.REQUEST_JSON, jsonObject);
            params.put(Constants.ACTION, Constants.ACTION_LOAD_PATRON);
            params.put(Constants.ACTIVITY, this.getActivity());
            params.put(Constants.FRAGMENT, this);
            params.put(Constants.VIEW, this.getView());
            params.put(Constants.CONTEXT, this.getContext());

            RequestClass.startRequestQueue();
            new CallISearchForUpdate().process(params);

        }catch (JSONException e){
            e.printStackTrace();
        }
     getActivity().setTitle("Add to cart");
    return view;
        
    }

    public void showAlert(){
        AlertDialog.Builder myAlert = new AlertDialog.Builder(getContext());
        myAlert.setMessage("Sorry! This book is currently unavailable.. would you like to add it to waitliast ?").setPositiveButton("ADD TO WAITLIST", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                attemptAddToWaitlist();
            }
        }).create();
        myAlert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //do nothing
            }
        });
        myAlert.show();
    }

    public void attemptAddToWaitlist(){
        try{
            JSONObject jsonObject = new JSONObject();
            String[] userDetails = SharedData.getUserDetails();
            jsonObject.put("email", userDetails[1]);
            LogHelper.logMessage("Book to waitlist", currentBook.Book_Id);
            jsonObject.put("bookId", Integer.valueOf(currentBook.Book_Id));
            jsonObject.put("patronId", userDetails[0]);

            HashMap<String, Object> params = new HashMap<String, Object>();
            params.put(Constants.REQUEST_JSON, jsonObject);
            params.put(Constants.ACTION, Constants.ACTION_ADD_TO_WAITLIST);
            params.put(Constants.ACTIVITY, this.getActivity());
            params.put(Constants.FRAGMENT, this);
            params.put(Constants.VIEW, this.getView());
            params.put(Constants.CONTEXT, this.getContext());

            RequestClass.startRequestQueue();
            new CallAddToWaitlistBook().process(params);
        }catch (JSONException e){
            e.printStackTrace();
        }
    }

     void addBookToCart(String bookId) throws JSONException{

         LogHelper.logMessage("Apoorv","current book"+currentBook.Book_Id+" and checking out"+bookId);
         readBooksfromCart(this.getContext());
         if("Unavailable".equalsIgnoreCase(currentBook.Book_Status)){
             showAlert();
         }else if(cartItemsSet.contains(currentBook.getDelimitedString())){
             Toast.makeText(getActivity(), "Book already in the cart" , Toast.LENGTH_SHORT).show();
         }
         else if(booksInCart.size()<3) {
             LogHelper.logMessage("Apoorv",currentBook.getDelimitedString());
//             if(cartItemsSet == null) {
//                 cartItemsSet = new HashSet<String> ();
//             }
             cartItemsSet.add (currentBook.getDelimitedString());
             writeToSharedPreference(this.getContext());
             Toast.makeText(getActivity(), "Book added to cart" , Toast.LENGTH_SHORT).show();
             getFragmentManager().popBackStackImmediate();
             //getFragmentManager().beginTransaction().replace(R.id.patron_main_container,new CartFragment()).addToBackStack(null).commit();
         }else{
             Toast.makeText(getActivity(), "Cannot add more than 3 books in the cart" , Toast.LENGTH_SHORT).show();
         }
    }

     void readBooksfromCart(Context c) {
        SharedPreferences sharedPref = c.getSharedPreferences("cartDataSharedPreference",Context.MODE_PRIVATE);
         cartItemsSet = sharedPref.getStringSet("cartItemsSet",new HashSet<String>());
        if(cartItemsSet!=null) {
            for (String bookString : cartItemsSet) {
                String[] bookdetails = bookString.split("@");
                PatronBookItem pbi = new PatronBookItem(bookdetails[0], bookdetails[1], bookdetails[2], bookdetails[3], bookdetails[4], bookdetails[5],"","", "");
                booksInCart.add(pbi);
                LogHelper.logMessage("Apoorv", "Book in Cart :" + pbi.Book_Id);
            }
        }
        //done reading books, stored in booksInCart



    }


     void writeToSharedPreference(Context c) {


        SharedPreferences sharedPref = c.getSharedPreferences("cartDataSharedPreference",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
         editor.clear();
        editor.putStringSet("cartItemsSet",cartItemsSet);
        editor.apply();
         LogHelper.logMessage("Apoorv","Added number of Books in Cart :"+cartItemsSet.size());
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
