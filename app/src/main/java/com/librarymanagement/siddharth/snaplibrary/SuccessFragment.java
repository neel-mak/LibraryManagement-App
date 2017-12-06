package com.librarymanagement.siddharth.snaplibrary;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.librarymanagement.siddharth.snaplibrary.helper.LogHelper;

import java.util.ArrayList;


public class SuccessFragment extends Fragment {


    private ImageView[] images= new ImageView[3];
    private TextView[] authors= new TextView[3];
    private TextView[] titles= new TextView[3];
    private TextView[] dueDates= new TextView[3];
    private TextView[] checkoutDates= new TextView[3];


    private CardView c1;
    private CardView c2;
    private CardView c3;
    ArrayList<PatronBookItem> checkedoutbooks = new ArrayList<PatronBookItem>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_success, container, false);

        c1= (CardView)view.findViewById(R.id.success_fragment_cardview1);
        c2= (CardView)view.findViewById(R.id.success_fragment_cardview2);
        c3= (CardView)view.findViewById(R.id.success_fragment_cardview3);

        images[0]=(ImageView)view.findViewById(R.id.success_fragment_book_image1);
        images[1]=(ImageView)view.findViewById(R.id.success_fragment_book_image2);
        images[2]=(ImageView)view.findViewById(R.id.success_fragment_book_image3);

        authors[0]=(TextView)view.findViewById(R.id.success_fragment_author1);
        authors[1]=(TextView)view.findViewById(R.id.success_fragment_author2);
        authors[2]=(TextView)view.findViewById(R.id.success_fragment_author3);

        titles[0]=(TextView)view.findViewById(R.id.success_fragment_book_Title1);
        titles[1]=(TextView)view.findViewById(R.id.success_fragment_book_Title2);
        titles[2]=(TextView)view.findViewById(R.id.success_fragment_book_Title3);

        dueDates[0]=(TextView)view.findViewById(R.id.success_fragmnet_due_date1);
        dueDates[1]=(TextView)view.findViewById(R.id.success_fragmnet_due_date2);
        dueDates[2]=(TextView)view.findViewById(R.id.success_fragment_due_date3);

        checkoutDates[0]=(TextView)view.findViewById(R.id.success_fragment_checkout_date1);
        checkoutDates[1]=(TextView)view.findViewById(R.id.success_fragment_checkout_date2);
        checkoutDates[2]=(TextView)view.findViewById(R.id.success_fragment_checkout_date3);


        Bundle responseBundle = getArguments();
         checkedoutbooks =  (ArrayList<PatronBookItem>)responseBundle.getSerializable("checkoutbooksserial");
        LogHelper.logMessage("Apoorv","Received books in success fragment:"+checkedoutbooks.size());
        getActivity().setTitle("Checkout Results");
        refreshView();
        return view;
    }

    void refreshView() {
        switch (checkedoutbooks.size()){

            case 0:
                c1.setVisibility(View.GONE);
                c2.setVisibility(View.GONE);
                c3.setVisibility(View.GONE);

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
                titles[0].setText(checkedoutbooks.get(0).Book_Title);
                authors[0].setText(checkedoutbooks.get(0).Book_Author);
                dueDates[0].setText("Due Date: "+checkedoutbooks.get(0).bookDueDate);
                checkoutDates[0].setText("Checkout Date: "+checkedoutbooks.get(0).bookCheckedOutDate);
                break;
            case 2:
                titles[1].setText(checkedoutbooks.get(1).Book_Title);
                authors[1].setText(checkedoutbooks.get(1).Book_Author);
                dueDates[1].setText("Due Date: "+checkedoutbooks.get(1).bookDueDate);
                checkoutDates[1].setText("Checkout Date: "+checkedoutbooks.get(1).bookCheckedOutDate);
                break;
            case 3:
                titles[2].setText(checkedoutbooks.get(2).Book_Title);
                authors[2].setText(checkedoutbooks.get(2).Book_Author);
                dueDates[2].setText("Due Date: "+checkedoutbooks.get(2).bookDueDate);
                checkoutDates[2].setText("Checkout Date: "+checkedoutbooks.get(2).bookCheckedOutDate);
                break;
        }
    }

}
