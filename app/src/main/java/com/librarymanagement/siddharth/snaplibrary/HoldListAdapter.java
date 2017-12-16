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

    private List<HoldListItem> HoldBookList;

    public HoldListAdapter(List<HoldListItem> holdBookList) {
        HoldBookList = holdBookList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.hold_list_items,parent,false);
        return new HoldListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final HoldListItem holdListItem = HoldBookList.get(position);
        holder.Hold_Book_Author.setText(holdListItem.getHold_Book_Author());
        holder.Hold_Book_Title.setText(holdListItem.getHold_Book_Title());
        holder.Hold_Book_Publisher.setText(holdListItem.getHold_Book_Publisher());
        holder.Hold_Book_Expiration_Date.setText(holdListItem.getHold_Book_expiratation_Date());

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
