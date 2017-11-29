package com.librarymanagement.siddharth.snaplibrary;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


/**
 * A simple {@link Fragment} subclass.
 */
public class SignUpFragment extends Fragment implements View.OnClickListener {


    public SignUpFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sign_up, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
    }

    private void initView(View view){
        Button btnClick = view.findViewById(R.id.btn_signup);
        btnClick.setOnClickListener(this);
    }

    private void changeFragment(){
        getFragmentManager().beginTransaction().replace(R.id.fragment,new Confirmation_Fragment()).addToBackStack(null).commit();
    }


    @Override
    public void onClick(View v) {
        changeFragment();
    }
}
