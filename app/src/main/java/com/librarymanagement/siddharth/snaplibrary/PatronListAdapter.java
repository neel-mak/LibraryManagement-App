package com.librarymanagement.siddharth.snaplibrary;

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

public class PatronListAdapter extends RecyclerView.Adapter<PatronListAdapter.ViewHolder>{
    private List<PatronBookItem> BookList;
    public static int listLength = 0;

    public PatronListAdapter(List<PatronBookItem> bookList) {
        BookList = bookList;

    }

    @Override
    public PatronListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.patron_booklist_items,parent,false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(PatronListAdapter.ViewHolder holder, final int position) {

        final PatronBookItem patronBookItem = BookList.get(position);
        LogHelper.logMessage("Apoorv",patronBookItem.getBook_Title());
        holder.Book_Status.setText(patronBookItem.getBook_Status());
        holder.Book_Title.setText(patronBookItem.getBook_Title());
        holder.Book_Publisher.setText(patronBookItem.getBook_Publisher());
        holder.Book_Author.setText(patronBookItem.getBook_Author());
        holder.Book_Copies.setText(patronBookItem.getBook_copies());

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (mTwoPane) {
                LogHelper.logMessage("Siddharth" , "Received Click for"+patronBookItem.getBook_Title());
                    Bundle arguments = new Bundle();
                    arguments.putInt("book_obj_position_in_list", position);
                    PatronCheckoutFragment patronCheckoutFragment = new PatronCheckoutFragment();
                    patronCheckoutFragment.setArguments(arguments);
                     PatronActivity currentPatronActivity = (PatronActivity)v.getContext();
                     android.support.v4.app.FragmentManager fm = currentPatronActivity.getSupportFragmentManager();
                     android.support.v4.app.FragmentTransaction transaction = fm.beginTransaction();
                     transaction.replace(R.id.patron_main_container,patronCheckoutFragment,"CHECKOUT_FRAGMENT");
                     transaction.commit();



//                } else {
//                    Context context = v.getContext();
//                    Intent intent = new Intent(context, CityDetailActivity.class);
//                    intent.putExtra(CityDetailFragment.ARG_ITEM_ID, holder.mItem.id);
//                    intent.putExtra("index", position);
//
//                    context.startActivity(intent);
//                }
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

            Book_Author = (TextView)itemView.findViewById(R.id.patron_booklist_book_author);
            LogHelper.logMessage("ViewHolder", Book_Author.getText().toString());

            Book_Copies = (TextView)itemView.findViewById(R.id.patron_booklist_book_copies);

            Book_Publisher = (TextView)itemView.findViewById(R.id.patron_booklist_book_publisher);
            Book_Title = (TextView)itemView.findViewById(R.id.patron_booklist_book_Title);
            Book_Status = (TextView)itemView.findViewById(R.id.patron_booklist_book_status);
        }



    }
}
