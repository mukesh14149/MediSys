package com.example.mukesh.medisys;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by sujeet on 29/11/16.
 */

public class frag_history extends Fragment

{

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        //Inflate the layout for this fragment

        View rootView = inflater.inflate(
                R.layout.rough, container, false);
        TextView t = (TextView) rootView.findViewById(R.id.Name);
        TextView t1 = (TextView) rootView.findViewById(R.id.Time);

        t.setText(getArguments().getString("Time"));

        t1.setText(getArguments().getString("Name"));


        return rootView;
    }
}
