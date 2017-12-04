package com.librarymanagement.siddharth.snaplibrary;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.librarymanagement.siddharth.snaplibrary.helper.LogHelper;

import java.util.List;

/**
 * Created by NEEL on 02-12-2017.
 */

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder>{
    private List<BookItem> BookList;
    public static int listLength = 0;

    public ListAdapter(List<BookItem> bookList) {
        BookList = bookList;

    }

    @Override
    public ListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.booklist_items,parent,false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(ListAdapter.ViewHolder holder, final int position) {

        final BookItem bookItem = BookList.get(position);
        LogHelper.logMessage("Apoorv",bookItem.getBook_Title());
        holder.Book_Status.setText(bookItem.getBook_Status());
        holder.Book_Title.setText(bookItem.getBook_Title());
        holder.Book_Publisher.setText(bookItem.getBook_Publisher());
        holder.Book_Author.setText(bookItem.getBook_Author());
        holder.Book_Copies.setText(bookItem.getBook_copies());

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogHelper.logMessage("Apoorv" , "Received Click for"+bookItem.getBook_Title());
                    Bundle arguments = new Bundle();
                    arguments.putInt("book_obj_position_in_list", position);
                    UpdateDeleteFragment updateDeleteFragment = new UpdateDeleteFragment();
                    updateDeleteFragment.setArguments(arguments);
                     CatalogActivity currentCatalogActivity = (CatalogActivity)v.getContext();
                     android.support.v4.app.FragmentManager fm = currentCatalogActivity.getSupportFragmentManager();
                     android.support.v4.app.FragmentTransaction transaction = fm.beginTransaction();
                     transaction.replace(R.id.place_holder,updateDeleteFragment,"UPDATE_DELETE_FRAGMENT");
                     transaction.commit();

            }
        });
    }

    @Override
    public int getItemCount() {
        return BookList.size();
    }

    public int getBookListSize(){
        return BookList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView Book_Title;
        public TextView Book_Author;
        public TextView Book_Publisher;
        public TextView Book_Copies;
        public TextView Book_Status;
        public View mView;


        public ViewHolder(View itemView) {
            super(itemView);
            mView = itemView;

            Book_Author = (TextView)itemView.findViewById(R.id.booklist_book_author);
            LogHelper.logMessage("ViewHolder", Book_Author.getText().toString());

            Book_Copies = (TextView)itemView.findViewById(R.id.booklist_book_copies);

            Book_Publisher = (TextView)itemView.findViewById(R.id.booklist_book_publisher);
            Book_Title = (TextView)itemView.findViewById(R.id.booklist_book_Title);
            Book_Status = (TextView)itemView.findViewById(R.id.booklist_book_status);
        }



    }
}
