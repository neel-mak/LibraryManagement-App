package com.librarymanagement.siddharth.snaplibrary;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;


public class ReturnFragment extends Fragment {

    private Button return_btn;
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

        Author1 = (TextView)view.findViewById(R.id.return_fragment_author1);
        Author2 = (TextView)view.findViewById(R.id.return_fragment_author2);
        Author3 = (TextView)view.findViewById(R.id.return_fragment_author3);
        Author4 = (TextView)view.findViewById(R.id.return_fragment_author4);
        Author5 = (TextView)view.findViewById(R.id.return_fragment_author5);
        Author6 = (TextView)view.findViewById(R.id.return_fragment_author6);
        Author7 = (TextView)view.findViewById(R.id.return_fragment_author7);
        Author8 = (TextView)view.findViewById(R.id.return_fragment_author8);
        Author9 = (TextView)view.findViewById(R.id.return_fragment_author9);

        Title1 = (TextView)view.findViewById(R.id.return_fragment_book_Title1);
        Title2 = (TextView)view.findViewById(R.id.return_fragment_book_Title2);
        Title3 = (TextView)view.findViewById(R.id.return_fragment_book_Title3);
        Title4 = (TextView)view.findViewById(R.id.return_fragment_book_Title4);
        Title5 = (TextView)view.findViewById(R.id.return_fragment_book_Title5);
        Title6 = (TextView)view.findViewById(R.id.return_fragment_book_Title6);
        Title7 = (TextView)view.findViewById(R.id.return_fragment_book_Title7);
        Title8 = (TextView)view.findViewById(R.id.return_fragment_book_Title8);
        Title9 = (TextView)view.findViewById(R.id.return_fragment_book_Title9);

        Publisher1 = (TextView)view.findViewById(R.id.return_fragment_book_publisher1);
        Publisher2 = (TextView)view.findViewById(R.id.return_fragment_book_publisher2);
        Publisher3 = (TextView)view.findViewById(R.id.return_fragment_book_publisher3);
        Publisher4 = (TextView)view.findViewById(R.id.return_fragment_book_publisher4);
        Publisher5 = (TextView)view.findViewById(R.id.return_fragment_book_publisher5);
        Publisher6 = (TextView)view.findViewById(R.id.return_fragment_book_publisher6);
        Publisher7 = (TextView)view.findViewById(R.id.return_fragment_book_publisher7);
        Publisher8 = (TextView)view.findViewById(R.id.return_fragment_book_publisher8);
        Publisher9 = (TextView)view.findViewById(R.id.return_fragment_book_publisher9);

        return_btn = (Button)view.findViewById(R.id.return_fragment_return_btn);
        return_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        return view;
    }


    public void onCheckedBoxClicked(View view){
        boolean checked = ((CheckBox) view).isChecked();
        switch (view.getId()){
            case R.id.return_fragment_checkbox1:
                if(checked){

                }


            case R.id.return_fragment_checkbox2:
                if(checked){

                }


            case R.id.return_fragment_checkbox3:
                if(checked){

                }

            case R.id.return_fragment_checkbox4:
                if(checked){

                }

            case R.id.return_fragment_checkbox5:
                if(checked){

                }

            case R.id.return_fragment_checkbox6:
                if(checked){

                }

            case R.id.return_fragment_checkbox7:
                if(checked){

                }

            case R.id.return_fragment_checkbox8:
                if(checked){

                }

            case R.id.return_fragment_checkbox9:
                if(checked){

                }
        }
    }
}
