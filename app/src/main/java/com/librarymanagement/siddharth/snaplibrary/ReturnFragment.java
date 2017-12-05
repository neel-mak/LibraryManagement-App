package com.librarymanagement.siddharth.snaplibrary;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.librarymanagement.siddharth.snaplibrary.helper.CallGetCheckoutBooks;
import com.librarymanagement.siddharth.snaplibrary.helper.CallISBNLookup;
import com.librarymanagement.siddharth.snaplibrary.helper.Constants;
import com.librarymanagement.siddharth.snaplibrary.helper.RequestClass;
import com.librarymanagement.siddharth.snaplibrary.helper.SharedData;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class ReturnFragment extends Fragment {

    public static Button return_btn;

    public static CardView[] cardViews = new CardView[9];
    public static CheckBox[] checkBoxes = new CheckBox[9];

    public static ImageView[] images = new ImageView[9];

    public static TextView[] authors = new TextView[9];
    public static TextView[] titles = new TextView[9];
    public static TextView[] checkOutDate = new TextView[9];
    public static TextView[] dueDate = new TextView[9];
    public static List<PatronBookItem> patronBookItems;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_return, container, false);

        cardViews[0] = (CardView)view.findViewById(R.id.return_fragment_cardview1);
        cardViews[1] = (CardView)view.findViewById(R.id.return_fragment_cardview2);
        cardViews[2] = (CardView)view.findViewById(R.id.return_fragment_cardview3);
        cardViews[3] = (CardView)view.findViewById(R.id.return_fragment_cardview4);
        cardViews[4] = (CardView)view.findViewById(R.id.return_fragment_cardview5);
        cardViews[5] = (CardView)view.findViewById(R.id.return_fragment_cardview6);
        cardViews[6] = (CardView)view.findViewById(R.id.return_fragment_cardview7);
        cardViews[7] = (CardView)view.findViewById(R.id.return_fragment_cardview8);
        cardViews[8] = (CardView)view.findViewById(R.id.return_fragment_cardview9);

        checkBoxes[0] = (CheckBox)view.findViewById(R.id.return_fragment_checkbox1);
        checkBoxes[1] = (CheckBox)view.findViewById(R.id.return_fragment_checkbox2);
        checkBoxes[2] = (CheckBox)view.findViewById(R.id.return_fragment_checkbox3);
        checkBoxes[3] = (CheckBox)view.findViewById(R.id.return_fragment_checkbox4);
        checkBoxes[4] = (CheckBox)view.findViewById(R.id.return_fragment_checkbox5);
        checkBoxes[5] = (CheckBox)view.findViewById(R.id.return_fragment_checkbox6);
        checkBoxes[6] = (CheckBox)view.findViewById(R.id.return_fragment_checkbox7);
        checkBoxes[7] = (CheckBox)view.findViewById(R.id.return_fragment_checkbox8);
        checkBoxes[8] = (CheckBox)view.findViewById(R.id.return_fragment_checkbox9);

        images[0] = (ImageView)view.findViewById(R.id.return_fragment_book_image1);
        images[1] = (ImageView)view.findViewById(R.id.return_fragment_book_image2);
        images[2] = (ImageView)view.findViewById(R.id.return_fragment_book_image3);
        images[3] = (ImageView)view.findViewById(R.id.return_fragment_book_image4);
        images[4] = (ImageView)view.findViewById(R.id.return_fragment_book_image5);
        images[5] = (ImageView)view.findViewById(R.id.return_fragment_book_image6);
        images[6] = (ImageView)view.findViewById(R.id.return_fragment_book_image7);
        images[7] = (ImageView)view.findViewById(R.id.return_fragment_book_image8);
        images[8] = (ImageView)view.findViewById(R.id.return_fragment_book_image9);

        authors[0] = (TextView)view.findViewById(R.id.return_fragment_author1);
        authors[1] = (TextView)view.findViewById(R.id.return_fragment_author2);
        authors[2] = (TextView)view.findViewById(R.id.return_fragment_author3);
        authors[3] = (TextView)view.findViewById(R.id.return_fragment_author4);
        authors[4] = (TextView)view.findViewById(R.id.return_fragment_author5);
        authors[5] = (TextView)view.findViewById(R.id.return_fragment_author6);
        authors[6] = (TextView)view.findViewById(R.id.return_fragment_author7);
        authors[7] = (TextView)view.findViewById(R.id.return_fragment_author8);
        authors[8] = (TextView)view.findViewById(R.id.return_fragment_author9);

        titles[0] = (TextView)view.findViewById(R.id.return_fragment_book_Title1);
        titles[1] = (TextView)view.findViewById(R.id.return_fragment_book_Title2);
        titles[2] = (TextView)view.findViewById(R.id.return_fragment_book_Title3);
        titles[3] = (TextView)view.findViewById(R.id.return_fragment_book_Title4);
        titles[4] = (TextView)view.findViewById(R.id.return_fragment_book_Title5);
        titles[5] = (TextView)view.findViewById(R.id.return_fragment_book_Title6);
        titles[6] = (TextView)view.findViewById(R.id.return_fragment_book_Title7);
        titles[7] = (TextView)view.findViewById(R.id.return_fragment_book_Title8);
        titles[8] = (TextView)view.findViewById(R.id.return_fragment_book_Title9);

        checkOutDate[0] = (TextView)view.findViewById(R.id.return_fragment_checkout_date1);
        checkOutDate[1] = (TextView)view.findViewById(R.id.return_fragment_checkout_date2);
        checkOutDate[2] = (TextView)view.findViewById(R.id.return_fragment_checkout_date3);
        checkOutDate[3] = (TextView)view.findViewById(R.id.return_fragment_checkout_date4);
        checkOutDate[4] = (TextView)view.findViewById(R.id.return_fragment_checkout_date5);
        checkOutDate[5] = (TextView)view.findViewById(R.id.return_fragment_checkout_date6);
        checkOutDate[6] = (TextView)view.findViewById(R.id.return_fragment_checkout_date7);
        checkOutDate[7] = (TextView)view.findViewById(R.id.return_fragment_checkout_date8);
        checkOutDate[8] = (TextView)view.findViewById(R.id.return_fragment_checkout_date9);

        dueDate[0] = (TextView)view.findViewById(R.id.return_fragment_due_date1);
        dueDate[1] = (TextView)view.findViewById(R.id.return_fragment_due_date2);
        dueDate[2] = (TextView)view.findViewById(R.id.return_fragment_due_date3);
        dueDate[3] = (TextView)view.findViewById(R.id.return_fragment_due_date4);
        dueDate[4] = (TextView)view.findViewById(R.id.return_fragment_due_date5);
        dueDate[5] = (TextView)view.findViewById(R.id.return_fragment_due_date6);
        dueDate[6] = (TextView)view.findViewById(R.id.return_fragment_due_date7);
        dueDate[7] = (TextView)view.findViewById(R.id.return_fragment_due_date8);
        dueDate[8] = (TextView)view.findViewById(R.id.return_fragment_due_date9);

        return_btn = (Button)view.findViewById(R.id.return_fragment_return_btn);
        return_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                returnBooks();
            }
        });

        //on load
        patronBookItems = new ArrayList<PatronBookItem>();
        try {
            JSONObject jsonObject = new JSONObject();
            SharedData.readFromSharedInitial(this.getActivity());
            String userDetails[] = SharedData.getUserDetails();
            jsonObject.put("email", userDetails[1]);
            jsonObject.put("patronId", Integer.parseInt(userDetails[0]));

            HashMap<String, Object> params = new HashMap<String, Object>();
            params.put(Constants.REQUEST_JSON, jsonObject);
            params.put(Constants.ACTION, Constants.ACTION_GET_CHECKED_OUT_BOOKS);
            params.put(Constants.ACTIVITY, this.getActivity());
            params.put(Constants.FRAGMENT, this);
            params.put(Constants.VIEW, this.getView());
            params.put(Constants.CONTEXT, this.getContext());

            RequestClass.startRequestQueue();
            new CallGetCheckoutBooks().process(params);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return view;
    }

    public void returnBooks(){

        //getting book ids of checked books

        try {
            JSONObject jsonObject = new JSONObject();
            SharedData.readFromSharedInitial(this.getActivity());
            String userDetails[] = SharedData.getUserDetails();
            jsonObject.put("email", userDetails[1]);
            jsonObject.put("patronId", Integer.parseInt(userDetails[0]));
            //jsonObject.put("bookIds", );

            HashMap<String, Object> params = new HashMap<String, Object>();
            params.put(Constants.REQUEST_JSON, jsonObject);
            params.put(Constants.ACTION, Constants.ACTION_RETURN_BOOKS);
            params.put(Constants.ACTIVITY, this.getActivity());
            params.put(Constants.FRAGMENT, this);
            params.put(Constants.VIEW, this.getView());
            params.put(Constants.CONTEXT, this.getContext());

            RequestClass.startRequestQueue();
            //new CallReturnBooks().process(params);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

}
