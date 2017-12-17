package com.librarymanagement.siddharth.snaplibrary;

import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.librarymanagement.siddharth.snaplibrary.helper.CallCheckoutCart;
import com.librarymanagement.siddharth.snaplibrary.helper.Constants;
import com.librarymanagement.siddharth.snaplibrary.helper.LogHelper;
import com.librarymanagement.siddharth.snaplibrary.helper.RequestClass;
import com.librarymanagement.siddharth.snaplibrary.helper.SharedData;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

/**
 * Created by NEEL on 15-12-2017.
 */

public class HoldListAdapter extends RecyclerView.Adapter<HoldListAdapter.ViewHolder> {

    private List<PatronBookItem> HoldBookList;
    public static int listLength = 0;

    public HoldListAdapter(List<PatronBookItem> holdBookList) {
        HoldBookList = holdBookList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.hold_list_items,parent,false);
        return new HoldListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final PatronBookItem patronBookItem = HoldBookList.get(position);
        holder.Hold_Book_Author.setText(patronBookItem.getBook_Author());
        holder.Hold_Book_Title.setText(patronBookItem.getBook_Title());
        holder.Hold_Book_Publisher.setText(patronBookItem.getBook_Publisher());
        holder.Hold_Book_Expiration_Date.setText(patronBookItem.getBookExpiratationDate());

        holder.Hold_Book_Checkout_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String bookId = patronBookItem.Book_Id;
                LogHelper.logMessage("Siddharth","Checkout on hold screen clicked" + bookId);

                if(bookId!=null) {
                    try {

                        Fragment fr = HoldFragment.fragment;

                        JSONArray bookArray = new JSONArray();
                        bookArray.put(bookId);
                        LogHelper.logMessage("Siddharth","BookId to be checkout: " + bookId);

                        JSONObject jsonObject = new JSONObject();
                        SharedData.readFromSharedInitial( view.getContext().getApplicationContext());
                        String[] userDetails = SharedData.getUserDetails();
                        jsonObject.put("email", userDetails[1]);
                        jsonObject.put("patronId", userDetails[0]);
                        jsonObject.put("bookIds", bookArray);

                        HashMap<String, Object> params = new HashMap<String, Object>();
                        params.put(Constants.REQUEST_JSON, jsonObject);
                        params.put(Constants.ACTION, Constants.ACTION_CHECKOUT_HOLD_BOOK);
                        params.put(Constants.ACTIVITY, fr.getActivity());
                        params.put(Constants.FRAGMENT, fr);
                        params.put(Constants.VIEW, view);
                        params.put(Constants.CONTEXT, fr.getContext());

                        RequestClass.startRequestQueue();
                        new CallCheckoutCart().processCheckoutBooks(params);
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return HoldBookList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView Hold_Book_Title;
        public TextView Hold_Book_Author;
        public TextView Hold_Book_Publisher;
        public TextView Hold_Book_Expiration_Date;
        public Button Hold_Book_Checkout_Btn;

        public ViewHolder(View itemView) {
            super(itemView);
            Hold_Book_Author = (TextView)itemView.findViewById(R.id.holdlist_book_author);
            Hold_Book_Title = (TextView)itemView.findViewById(R.id.holdlist_book_Title);
            Hold_Book_Publisher = (TextView)itemView.findViewById(R.id.holdlist_book_publisher);
            Hold_Book_Expiration_Date = (TextView)itemView.findViewById(R.id.holdlist_expiration_date);
            Hold_Book_Checkout_Btn = (Button) itemView.findViewById(R.id.holdlist_checkout_btn);
        }


    }
}
