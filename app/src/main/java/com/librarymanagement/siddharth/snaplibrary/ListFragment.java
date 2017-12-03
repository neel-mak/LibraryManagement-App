package com.librarymanagement.siddharth.snaplibrary;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by NEEL on 02-12-2017.
 */

public class ListFragment extends Fragment {
    private RecyclerView.Adapter adapter;
    private List<BookItem> bookItemList = new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_list, container, false);
        RecyclerView recyclerView=(RecyclerView)view.findViewById(R.id.recyclerView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        Spinner spinner = (Spinner)view.findViewById(R.id.Search_spinner);
        ArrayAdapter<CharSequence> adp;
        adp = ArrayAdapter.createFromResource(getContext(), R.array.search_factors,android.R.layout.simple_spinner_item);
        adp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adp);
        for(int i=0;i<10;i++)
        {
            BookItem bookItem = new BookItem(
                    "Transedence","APJ Abdul Kalam",
                    "EDM","50","Available"
            );
            bookItemList.add(bookItem);
        }
        adapter = new ListAdapter(bookItemList);
        recyclerView.setAdapter(adapter);


        return view;
    }


}
