package com.librarymanagement.siddharth.snaplibrary;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by NEEL on 16-12-2017.
 */

public class WaitListAdapter extends RecyclerView.Adapter<WaitListAdapter.ViewHolder> {

    private List<WaitListItem> waitListItemList;

    public WaitListAdapter(List<WaitListItem> waitListItemList) {
        this.waitListItemList = waitListItemList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.waitlist_items,parent,false);
        return new WaitListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final WaitListItem waitListItem = waitListItemList.get(position);
        holder.WaitList_Book_Author.setText(waitListItem.getWaitList_Book_Author());
        holder.WaitList_Book_Title.setText(waitListItem.getWaitList_Book_Title());
        holder.WaitList_Book_Publisher.setText(waitListItem.getWaitList_Book_Publisher());
        holder.WaitList_Order.setText(waitListItem.getWaitList_Order());

    }

    @Override
    public int getItemCount() {
        return waitListItemList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView WaitList_Book_Title;
        public TextView WaitList_Book_Author;
        public TextView WaitList_Book_Publisher;
        public TextView WaitList_Order;


        public ViewHolder(View itemView) {
            super(itemView);
            WaitList_Book_Author = (TextView)itemView.findViewById(R.id.waitlist_book_author);
            WaitList_Book_Title = (TextView)itemView.findViewById(R.id.waitlist_book_Title);
            WaitList_Book_Publisher = (TextView)itemView.findViewById(R.id.waitlist_book_publisher);
            WaitList_Order = (TextView)itemView.findViewById(R.id.waitlist_order);


        }


    }
}
