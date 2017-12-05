package com.librarymanagement.siddharth.snaplibrary;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


public class SuccessFragment extends Fragment {


    private ImageView[] images={};
    private TextView[] authors={};
    private TextView[] titles={};
    private TextView[] dueDates={};
    private TextView[] checkoutDates={};


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_success, container, false);

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


        return view;
    }

}
