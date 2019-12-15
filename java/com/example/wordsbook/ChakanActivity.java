package com.example.wordsbook;

import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.content.Intent;
import android.widget.TextView;
import android.widget.ListView;
public class ChakanActivity extends FragmentActivity {
    private ListView list_yd;  //有道链表；
    private Button back;
    TextView t_word;
    TextView t_meaning;
    TextView t_sample;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chakan);
        back = (Button) findViewById(R.id.back_chakan);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChakanActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        Intent intent=getIntent();
        Bundle bundle=intent.getExtras();
        String word=bundle.getString("word");
        String meaning=intent.getStringExtra("meaning");
        String sample=intent.getStringExtra("sample");

        t_word = (TextView) findViewById(R.id.chakan_Word);
        t_meaning= (TextView)findViewById(R.id.chakan_Meaning);
        t_sample= (TextView)findViewById(R.id.chakan_Sample);

        t_word.setText("单词："+word);
        t_meaning.setText("词义："+meaning);
        t_sample.setText("例句："+sample);

    }
}