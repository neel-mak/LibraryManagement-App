package com.librarymanagement.siddharth.snaplibrary;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.librarymanagement.siddharth.snaplibrary.helper.CallAddBook;
import com.librarymanagement.siddharth.snaplibrary.helper.CallCheckoutCart;
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
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;


public class CartFragment extends Fragment {

    private CardView c1;
    private CardView c2;
    private CardView c3;
    private Button btn1;
    private Button btn2;
    private Button btn3;
    private Button checkoutBtn;
    private TextView emptyCartText;

    ImageView imgBook1;
    TextView  titleBook1;
    TextView  authorBook1;
    TextView  publisher1;

    ImageView imgBook2;
    TextView  titleBook2;
    TextView  authorBook2;
    TextView  publisher2;

    ImageView imgBook3;
    TextView  titleBook3;
    TextView  authorBook3;
    TextView  publisher3;


    ArrayList<PatronBookItem> booksInCart = new ArrayList<PatronBookItem>();
    public static Set<String> cartItemsSet;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cart, container, false);
        c1= (CardView)view.findViewById(R.id.cart_fragment_cardview1);
        c2= (CardView)view.findViewById(R.id.cart_fragment_cardview2);
        c3= (CardView)view.findViewById(R.id.cart_fragment_cardview3);
        btn1 = (Button)view.findViewById(R.id.cart_fragment_cancel_btn1);
        btn2 = (Button)view.findViewById(R.id.cart_fragment_cancel_btn2);
        btn3 = (Button)view.findViewById(R.id.cart_fragment_cancel_btn3);
        checkoutBtn = (Button)view.findViewById(R.id.cart_fragment_checkout_btn);
        emptyCartText = (TextView) view.findViewById(R.id.cart_fragment_empty_cart_text);

        //Card 1
        imgBook1 = (ImageView) view.findViewById(R.id.cart_fragment_book_image1);
        titleBook1 = (TextView) view.findViewById(R.id.cart_fragment_book_Title1);
        authorBook1 = (TextView) view.findViewById(R.id.cart_fragment_author1);
        publisher1 = (TextView) view.findViewById(R.id.cart_fragment_book_publisher1);

        //Card 2
        imgBook2 = (ImageView) view.findViewById(R.id.cart_fragment_book_image2);
        titleBook2 = (TextView) view.findViewById(R.id.cart_fragment_book_Title2);
        authorBook2 = (TextView) view.findViewById(R.id.cart_fragment_author2);
        publisher2 = (TextView) view.findViewById(R.id.cart_fragment_book_publisher2);

        //Card 3
        imgBook3 = (ImageView) view.findViewById(R.id.cart_fragment_book_image3);
        titleBook3 = (TextView) view.findViewById(R.id.cart_fragment_book_Title3);
        authorBook3 = (TextView) view.findViewById(R.id.cart_fragment_author3);
        publisher3 = (TextView) view.findViewById(R.id.cart_fragment_book_publisher3);


        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                booksInCart.remove(0);
                refreshCart();
                commitCart();

            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                booksInCart.remove(1);
                refreshCart();
                commitCart();

            }
        });
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                booksInCart.remove(2);
                refreshCart();
                commitCart();

            }
        });

        checkoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    processCheckout();
                } catch (Exception e)
                {
                    ExceptionMessageHandler.handleError(getContext(),e.toString(),e,null);
                }
            }
        });



        //Read current cart from sharedPreferences
        LogHelper.logMessage("Apoorv","Attempting read");
        SharedPreferences sharedPref = getContext().getSharedPreferences("cartDataSharedPreference", Context.MODE_PRIVATE);
        cartItemsSet = sharedPref.getStringSet("cartItemsSet",new HashSet<String>());
        if(cartItemsSet!=null) {
            for (String bookString : cartItemsSet) {
                String[] bookdetails = bookString.split("@");
                PatronBookItem pbi = new PatronBookItem(bookdetails[0], bookdetails[1], bookdetails[2], bookdetails[3], bookdetails[4], bookdetails[5], "","");
                booksInCart.add(pbi);
                LogHelper.logMessage("Apoorv", "Book in Cart :" + pbi.Book_Id);
            }

            refreshCart();

        }
        getActivity().setTitle("Checkout Books");
        return view;
    }

    void refreshCart() {

        switch (booksInCart.size()){

            case 0:
                c1.setVisibility(View.GONE);
                c2.setVisibility(View.GONE);
                c3.setVisibility(View.GONE);
                checkoutBtn.setVisibility(View.GONE);
                emptyCartText.setVisibility(View.VISIBLE);

                break;
            case 1:
                c2.setVisibility(View.GONE);
                c3.setVisibility(View.GONE);
                //TODO: Populate 1st Card
                populateCard(1);

                break;
            case 2:
                c3.setVisibility(View.GONE);
                //TODO: Populate first two cards
                populateCard(1);
                populateCard(2);
                break;
            case 3:
                //TODO: Populate all three cards
                populateCard(1);
                populateCard(2);
                populateCard(3);
                break;

        }
    }

    void populateCard(int cardNumber) {
        switch (cardNumber) {
            case 1:
                titleBook1.setText(booksInCart.get(0).Book_Title);
                authorBook1.setText(booksInCart.get(0).Book_Author);
                publisher1.setText(booksInCart.get(0).Book_Publisher);
                break;
            case 2:
                titleBook2.setText(booksInCart.get(1).Book_Title);
                authorBook2.setText(booksInCart.get(1).Book_Author);
                publisher2.setText(booksInCart.get(1).Book_Publisher);
                break;
            case 3:
                titleBook3.setText(booksInCart.get(2).Book_Title);
                authorBook3.setText(booksInCart.get(2).Book_Author);
                publisher3.setText(booksInCart.get(2).Book_Publisher);
                break;
        }
    }

    void commitCart() {
        cartItemsSet.clear();

        for (PatronBookItem pbi: booksInCart) {
            cartItemsSet.add(pbi.getDelimitedString());
        }
        SharedPreferences sharedPref = getContext().getSharedPreferences("cartDataSharedPreference",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putStringSet("cartItemsSet",cartItemsSet);
        editor.apply();
        LogHelper.logMessage("Apoorv","Added number of Books in Cart :"+cartItemsSet.size());
    }

    void processCheckout() throws JSONException {
        String[] checkoutbookids =  new String[booksInCart.size()] ;
        JSONArray bookArray = new JSONArray();
        for (int i=0;i<booksInCart.size();i++) {
            checkoutbookids[i] = booksInCart.get(i).Book_Id;
            bookArray.put( booksInCart.get(i).Book_Id);
        }

        JSONObject jsonObject = new JSONObject();
        SharedData.readFromSharedInitial(this.getActivity().getApplicationContext());
        String[] userDetails = SharedData.getUserDetails();
        jsonObject.put("email", userDetails[1]);
        jsonObject.put("patronId", userDetails[0]);
        jsonObject.put("bookIds", bookArray);

        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put(Constants.REQUEST_JSON, jsonObject);
        params.put(Constants.ACTION, Constants.ACTION_CHECKOUT_CART);
        params.put(Constants.ACTIVITY, this.getActivity());
        params.put(Constants.FRAGMENT, this);
        params.put(Constants.VIEW, this.getView());
        params.put(Constants.CONTEXT, this.getContext());

        RequestClass.startRequestQueue();
        new CallCheckoutCart().processCheckoutBooks(params);


    }

     public void emptyCart() {
        booksInCart.clear();
        commitCart();
        refreshCart();
    }
}
