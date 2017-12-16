package com.librarymanagement.siddharth.snaplibrary;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;


public class HoldFragment extends Fragment {

    public static List<HoldListItem> holdListItems = new ArrayList<>();
    RecyclerView recyclerView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         View view =inflater.inflate(R.layout.fragment_hold, container, false);
         recyclerView = (RecyclerView)view.findViewById(R.id.hold_fragment_recyclerView);
         RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
         recyclerView.setLayoutManager(layoutManager);

         getActivity().setTitle("Books on Hold");
         return view;
    }

}
