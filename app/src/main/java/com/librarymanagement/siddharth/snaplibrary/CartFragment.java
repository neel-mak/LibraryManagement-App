package com.librarymanagement.siddharth.snaplibrary;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class CartFragment extends Fragment {

    private CardView c1;
    private CardView c2;
    private CardView c3;
    private Button btn1;
    private Button btn2;
    private Button btn3;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cart, container, false);
        c1= (CardView)view.findViewById(R.id.cart_fragment_cardview1);
        c2= (CardView)view.findViewById(R.id.cart_fragment_cardview2);
        c3= (CardView)view.findViewById(R.id.cart_fragment_cardview3);
        btn1 = (Button)view.findViewById(R.id.cart_fragment_cancel_btn1);
        btn2 = (Button)view.findViewById(R.id.cart_fragment_cancel_btn2);
        btn3 = (Button)view.findViewById(R.id.cart_fragment_cancel_btn3);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                c1.setVisibility(View.GONE);
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                c2.setVisibility(View.GONE);
            }
        });
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                c3.setVisibility(View.GONE);
            }
        });
        //c2.setVisibility(View.INVISIBLE);
        //c3.setVisibility(View.INVISIBLE);
        return view;
    }

}
