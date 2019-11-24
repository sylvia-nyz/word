package com.example.cly.word;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class SearchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.word_content );
        //Toast.makeText(SearchActivity.this,"找到了",Toast.LENGTH_LONG).show();
        Word list=(Word)getIntent().getSerializableExtra( "result" );
        String t=list.getName();
        String n=list.getMeaning();
        String k=list.getSample();
        String s=list.getUpdate();
        WordContentFragment wordContentFragment=(WordContentFragment)getSupportFragmentManager().findFragmentById( R.id.word_content_fragment );
        wordContentFragment.refresh(t,n,k,s);
    }
}
