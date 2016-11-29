package com.example.mukesh.medisys;

/**
 * Created by sujeet on 28/11/16.
 */
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class frag_graph extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        //Inflate the layout for this fragment

        View rootView=inflater.inflate(
                R.layout.rough, container, false);
        TextView t =(TextView)rootView.findViewById(R.id.percent);
        TextView t1 =(TextView)rootView.findViewById(R.id.percent2);
        TextView t2 =(TextView)rootView.findViewById(R.id.skip);
        TextView t3 =(TextView)rootView.findViewById(R.id.take);
        t.setText( getArguments().getString("time"));

        t1.setText( getArguments().getString("key"));
        t2.setText( getArguments().getString("skip"));
        t3.setText( getArguments().getString("take"));

        return rootView;
    }

}
