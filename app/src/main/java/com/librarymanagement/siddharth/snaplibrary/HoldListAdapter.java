package com.librarymanagement.siddharth.snaplibrary;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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


        public ViewHolder(View itemView) {
            super(itemView);
            Hold_Book_Author = (TextView)itemView.findViewById(R.id.holdlist_book_author);
            Hold_Book_Title = (TextView)itemView.findViewById(R.id.holdlist_book_Title);
            Hold_Book_Publisher = (TextView)itemView.findViewById(R.id.holdlist_book_publisher);
            Hold_Book_Expiration_Date = (TextView)itemView.findViewById(R.id.holdlist_expiration_date);


        }


    }
}
