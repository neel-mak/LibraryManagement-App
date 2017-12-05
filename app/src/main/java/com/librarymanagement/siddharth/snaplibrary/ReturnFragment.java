package com.librarymanagement.siddharth.snaplibrary;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;


public class ReturnFragment extends Fragment {

    private CheckBox cb1;
    private CheckBox cb2;
    private CheckBox cb3;
    private CheckBox cb4;
    private CheckBox cb5;
    private CheckBox cb6;
    private CheckBox cb7;
    private CheckBox cb8;
    private CheckBox cb9;

    private ImageView img1;
    private ImageView img2;
    private ImageView img3;
    private ImageView img4;
    private ImageView img5;
    private ImageView img6;
    private ImageView img7;
    private ImageView img8;
    private ImageView img9;

    private TextView Author1;
    private TextView Title1;
    private TextView Publisher1;

    private TextView Author2;
    private TextView Title2;
    private TextView Publisher2;

    private TextView Author3;
    private TextView Title3;
    private TextView Publisher3;

    private TextView Author4;
    private TextView Title4;
    private TextView Publisher4;

    private TextView Author5;
    private TextView Title5;
    private TextView Publisher5;

    private TextView Author6;
    private TextView Title6;
    private TextView Publisher6;

    private TextView Author7;
    private TextView Title7;
    private TextView Publisher7;

    private TextView Author8;
    private TextView Title8;
    private TextView Publisher8;

    private TextView Author9;
    private TextView Title9;
    private TextView Publisher9;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_return, container, false);
        cb1 = (CheckBox)view.findViewById(R.id.return_fragment_checkbox1);
        cb2 = (CheckBox)view.findViewById(R.id.return_fragment_checkbox2);
        cb3 = (CheckBox)view.findViewById(R.id.return_fragment_checkbox3);
        cb4 = (CheckBox)view.findViewById(R.id.return_fragment_checkbox4);
        cb5 = (CheckBox)view.findViewById(R.id.return_fragment_checkbox5);
        cb6 = (CheckBox)view.findViewById(R.id.return_fragment_checkbox6);
        cb7 = (CheckBox)view.findViewById(R.id.return_fragment_checkbox7);
        cb8 = (CheckBox)view.findViewById(R.id.return_fragment_checkbox8);
        cb9 = (CheckBox)view.findViewById(R.id.return_fragment_checkbox9);


        img1 = (ImageView)view.findViewById(R.id.return_fragment_book_image1);
        img2 = (ImageView)view.findViewById(R.id.return_fragment_book_image2);
        img3 = (ImageView)view.findViewById(R.id.return_fragment_book_image3);
        img4 = (ImageView)view.findViewById(R.id.return_fragment_book_image4);
        img5 = (ImageView)view.findViewById(R.id.return_fragment_book_image5);
        img6 = (ImageView)view.findViewById(R.id.return_fragment_book_image6);
        img7 = (ImageView)view.findViewById(R.id.return_fragment_book_image7);
        img8 = (ImageView)view.findViewById(R.id.return_fragment_book_image8);
        img9 = (ImageView)view.findViewById(R.id.return_fragment_book_image9);


        return view;
    }

}
