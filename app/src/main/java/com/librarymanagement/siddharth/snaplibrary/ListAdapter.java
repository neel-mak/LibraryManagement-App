package com.librarymanagement.siddharth.snaplibrary;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by NEEL on 02-12-2017.
 */

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder>{
    private List<BookItem> BookList;
    private Context context;

    public ListAdapter(List<BookItem> bookList, Context context) {
        BookList = bookList;
        this.context = context;
    }

    @Override
    public ListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.booklist_items,parent,false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(ListAdapter.ViewHolder holder, int position) {
        BookItem bookItem = BookList.get(position);

        holder.Book_Status.setText(bookItem.getBook_Status());
        holder.Book_Title.setText(bookItem.getBook_Title());
        holder.Book_Publisher.setText(bookItem.getBook_Publisher());
        holder.Book_Author.setText(bookItem.getBook_Author());
        holder.Book_Copies.setText(bookItem.getBook_copies());
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView Book_Title;
        public TextView Book_Author;
        public TextView Book_Publisher;
        public TextView Book_Copies;
        public TextView Book_Status;


        public ViewHolder(View itemView) {
            super(itemView);
            Book_Author = (TextView)itemView.findViewById(R.id.booklist_book_author);

            Book_Copies = (TextView)itemView.findViewById(R.id.booklist_book_copies);

            Book_Publisher = (TextView)itemView.findViewById(R.id.booklist_book_publisher);
            Book_Title = (TextView)itemView.findViewById(R.id.booklist_book_Title);
            Book_Status = (TextView)itemView.findViewById(R.id.booklist_book_status);
        }



    }
}
