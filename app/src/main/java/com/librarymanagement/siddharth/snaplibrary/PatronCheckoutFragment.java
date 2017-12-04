package com.librarymanagement.siddharth.snaplibrary;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TextView;
import android.widget.ImageView;


import com.librarymanagement.siddharth.snaplibrary.helper.CallISearchForUpdate;
import com.librarymanagement.siddharth.snaplibrary.helper.Constants;
import com.librarymanagement.siddharth.snaplibrary.helper.LogHelper;
import com.librarymanagement.siddharth.snaplibrary.helper.RequestClass;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;


public class PatronCheckoutFragment extends Fragment {

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
    BookItem currentBook;
    
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
        checkoutFragmentBookCopies = (TextView) view.findViewById(R.id.checkout_Fragment_book_copies);
        checkoutFragmentBookKeywords = (TextView) view.findViewById(R.id.checkout_Fragment_book_keywords);
        checkoutFragmentBookStatus = (TextView) view.findViewById(R.id.checkout_Fragment_book_status);

        checkoutFragmentAddtoCartBtn = (Button)view.findViewById(R.id.checkout_fragment_checkout_btn);

        checkoutFragmentAddtoCartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LogHelper.logMessage("Apoorv","Add to cart clicked");
            }
        });

        try {
            //TODO: Replace with position and ID from actual list
          //  position = 2; // getArguments().getInt("book_obj_position_in_list");
           // currentBook = ListFragment.bookItemList.get(position);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("searchType", "byId");
            jsonObject.put("searchParameters", new JSONObject().put("id", "30"));

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

    return view;
        
    }


}
