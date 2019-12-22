package com.example.wordsbook;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.content.Intent;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Map;
import android.widget.SimpleAdapter;
import android.widget.ListView;
import android.database.sqlite.*;
import android.database.Cursor;
import java.util.HashMap;
public class SearchActivity extends AppCompatActivity {
    private Button bn_back;
    WordsDBHelper mDBHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        TextView tx_word=(TextView)findViewById(R.id.sear_Word);
        TextView tx_meaning=(TextView)findViewById(R.id.sear_Meaning);
        TextView tx_sample=(TextView)findViewById(R.id.sear_Sample);
        Intent intent=getIntent();
        // Bundle bundle=intent.getBundleExtra("result");
        ArrayList<Map<String,String>>items=(ArrayList<Map<String, String>>)intent.getSerializableExtra("result");
        setWordsListView(items);
        /*for(int i=0;i<items.size();i++) {
            tx_word.setText(items.get(i).toString());
            tx_meaning.setText(items.get(i).toString());
        }
         */
        //形式为：word={word}word={word};
        bn_back=(Button)findViewById(R.id.back);
        bn_back.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(SearchActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }
    private void setWordsListView(ArrayList<Map<String, String>> items) {
        SimpleAdapter adapter = new SimpleAdapter(this, items, R.layout.item, new String[]{
                Words.Word.COLUMN_NAME_WORD,Words.Word.COLUMN_NAME_MEANING, Words.Word.COLUMN_NAME_SAMPLE},
                new int[]{R.id.textViewWord, R.id.textViewMeaning, R.id.textViewSample});
        ListView list = (ListView) findViewById(R.id.IstWords);
        list.setAdapter(adapter);
    }
    private ArrayList<Map<String, String>> getAll() {
        ArrayList<Map<String, String>> list = new ArrayList<>();
        SQLiteDatabase db = mDBHelper.getReadableDatabase();
        Cursor c = db.query(Words.Word.TABLE_NAME, null, null, null, null, null, null);
        int colums = c.getColumnCount();
        while(c.moveToNext()){
            Map<String, String> map = new HashMap<String, String>();
            for (int i = 0; i < colums; i++) {
                String word1 = c.getColumnName(i);
                String value1 = c.getString(c.getColumnIndex(word1));
                map.put(word1, value1);
            }
            list.add(map);
        }
        return list;
    }
}
