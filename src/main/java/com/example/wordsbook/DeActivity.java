package com.example.wordsbook;
//只支持查看：
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
import android.view.ContextMenu;
import android.view.MenuItem;
import android.widget.AdapterView;
import androidx.appcompat.app.*;
import android.content.ContentValues;
import android.widget.TableLayout;
import android.content.DialogInterface;
import android.widget.EditText;
import android.view.ContextMenu.ContextMenuInfo;
import android.content.Context;
import android.widget.Toast;
import android.util.Log;
public class DeActivity extends AppCompatActivity {
    private ListView list_de;  //有道链表；
    private Button back;
    private Context context;
    WordsDBHelper mDBHelper;
    SQLiteDatabase.CursorFactory factory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_de);

        //mDBHelper=new WordsDBHelper(this,"wordsdb.db",factory,1);//会出现空指针异常；
        mDBHelper = new WordsDBHelper(this, "wordsdb.db", null, 1);
        list_de = (ListView) findViewById(R.id.list_de);
        back = (Button) findViewById(R.id.back_de);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DeActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        //输出返回过来的单词：
        Intent intent=getIntent();
        ArrayList<Map<String,String>>items=(ArrayList<Map<String, String>>)intent.getSerializableExtra("result");
        SimpleAdapter adapter = new SimpleAdapter(this, items, R.layout.item, new String[]{
                Words.Word._ID,Words.Word.COLUMN_NAME_WORD, Words.Word.COLUMN_NAME_MEANING, Words.Word.COLUMN_NAME_SAMPLE},//
                new int[]{R.id.textId, R.id.textViewWord, R.id.textViewMeaning, R.id.textViewSample});
        list_de.setAdapter(adapter);

    }

    private void setWordsListView(ArrayList<Map<String, String>> items) {
        SimpleAdapter adapter = new SimpleAdapter(this, items, R.layout.item, new String[]{
                Words.Word._ID, Words.Word.COLUMN_NAME_WORD, Words.Word.COLUMN_NAME_MEANING, Words.Word.COLUMN_NAME_SAMPLE},
                new int[]{R.id.textId, R.id.textViewWord, R.id.textViewMeaning, R.id.textViewSample});
        ListView list = (ListView) findViewById(R.id.IstWords);
        list.setAdapter(adapter);
    }

    private ArrayList<Map<String, String>> getAll(String table_name) {
        ArrayList<Map<String, String>> list = new ArrayList<>();
        SQLiteDatabase db = mDBHelper.getReadableDatabase();
        Cursor c = db.query(table_name, null, null, null, null, null, null);
        int colums = c.getColumnCount();
        while (c.moveToNext()) {
            Map<String, String> map = new HashMap<String, String>();
            for (int i = 0; i < colums; i++) {
                String word1 = c.getColumnName(i);
                String value1 = c.getString(c.getColumnIndex(word1));
                map.put(word1, value1);
            }
            list.add(map);
        }
        c.close();
        return list;
    }
}