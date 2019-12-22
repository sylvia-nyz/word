package com.example.wordsbook;

import androidx.fragment.app.Fragment;
import android.view.*;
import androidx.annotation.Nullable;
import android.os.Bundle;
import android.widget.ListView;

public class Youdao extends Fragment {
    ListView list;
    YoudaoActivity youdaoactivity;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_youdao, null);
        list = view.findViewById(R.id.list_youdao);
        return view;
    }
}
    /*@Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        youdaoactivity = (YoudaoActivity) getActivity();
    }
}
/*
<fragment
        android:id="@+id/youdao_fragment"
        android:name="com.example.wordsbook.Youdao"
        android:layout_width="1dp"
        android:layout_height="301dp"
        android:layout_weight="1" />
 */