package com.example.wordsbook;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import java.util.Map;
import java.util.ArrayList;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ContextMenu;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TextView;
import android.database.sqlite.SQLiteDatabase;
import android.database.Cursor;
import android.widget.Toast;
import android.content.Intent;
import android.widget.SimpleAdapter;
import java.lang.String;
import android.content.Context;
import android.widget.Button;
import java.util.HashMap;
import com.example.wordsbook.Words.*;
import java.util.*;
import android.util.Log;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.*;
public class Main2Activity extends AppCompatActivity {
    WordsDBHelper mDBHelper;
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Intent intent=getIntent();
        ArrayList<Map<String,String>>items=(ArrayList<Map<String, String>>)intent.getSerializableExtra("result");
        SimpleAdapter adapter = new SimpleAdapter(this, items, R.layout.item, new String[]{
                Words.Word.COLUMN_NAME_WORD, Words.Word.COLUMN_NAME_MEANING, Words.Word.COLUMN_NAME_SAMPLE},//
                new int[]{R.id.textViewWord, R.id.textViewMeaning, R.id.textViewSample});
        FragmentManager manger=getSupportFragmentManager();
        Fragment fragment=manger.findFragmentById(R.id.chakan_fragment);
        if(fragment!=null && fragment.isVisible()){
            Chakan chakan=(Chakan)fragment;
            //chakan.set
        }else{
            //ArrayList<Map<String, String>> items = (ArrayList<Map<String, String>>) getAll("words");
            Bundle bundle = new Bundle();
            bundle.putSerializable("result", items);
            Intent intent1 = new Intent(Main2Activity.this, YoudaoActivity.class);
            intent.putExtras(bundle);
            startActivity(intent1);
        }
    }
    private ArrayList<Map<String, String>> getAll(String table_name) {
        ArrayList<Map<String, String>> list = new ArrayList<>();
        SQLiteDatabase db = mDBHelper.getReadableDatabase();
        Cursor c = db.query(table_name, null, null, null, null, null, null);
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