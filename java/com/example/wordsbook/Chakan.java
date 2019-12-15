package com.example.wordsbook;

import androidx.fragment.app.Fragment;
import android.view.*;
import androidx.annotation.Nullable;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

public class Chakan extends Fragment {
    TextView word;
    TextView meaning;
    TextView sample;
    ChakanActivity chakanactivity;
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_chakan, null);
        word = view.findViewById(R.id.chakan_Word);
        meaning = view.findViewById(R.id.chakan_Meaning);
        sample = view.findViewById(R.id.chakan_Sample);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        chakanactivity = (ChakanActivity) getActivity();
    }/*
   public void setIndex(int i){
       sample.setText("dijile");
    }*/
}
