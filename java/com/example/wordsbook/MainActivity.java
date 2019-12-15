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
public class MainActivity extends AppCompatActivity {
    WordsDBHelper mDBHelper;
    private Button wd; //有道
    private Button mywd;//我的
    private Button dewd;//删除
    private Button shengwd;//生词本
    private Button cre;
    private Context content;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        wd = (Button) findViewById(R.id.wds);
        mywd = (Button) findViewById(R.id.mywds);
        dewd = (Button) findViewById(R.id.dewds);
        //代码查表
        /*
        shengwd = (Button) findViewById(R.id.shengwds);
        shengwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db =mDBHelper.getWritableDatabase();

                //核心区
                //读取系统表 sqlite_master
                String sql = "select * from sqlite_master";
                Cursor cursor = db.rawQuery(sql, null);

                //打印表的所有列名
                Log.i("TAG", Arrays.toString(cursor.getColumnNames()));

                //打印当前数据库中的所有表
                if (cursor.moveToFirst()) {
                    do {
                        String str = "";

                        for (String item : cursor.getColumnNames()) {
                            str += item + ": " + cursor.getString(cursor.getColumnIndex(item)) + "\n";
                        }

                        Log.i("TAG", str);

                    } while (cursor.moveToNext());
                }
            }
        });

*/
        //wd.setOnClickListener();
        //有道单词本界面
        wd.setOnClickListener(new View.OnClickListener() {  //输出有道单词本中所有单词
            public void onClick(View v) {
                ArrayList<Map<String, String>> items = (ArrayList<Map<String, String>>) getAll("words");
                Bundle bundle = new Bundle();
                bundle.putSerializable("result", items);
                Intent intent = new Intent(MainActivity.this, YoudaoActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        mywd.setOnClickListener(new View.OnClickListener() {  //输出有道单词本中所有单词
            public void onClick(View v) {
                ArrayList<Map<String, String>> items = (ArrayList<Map<String, String>>) getAll("mywords");
                Bundle bundle = new Bundle();
                bundle.putSerializable("result", items);
                Intent intent = new Intent(MainActivity.this, MyActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        dewd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<Map<String, String>> items = (ArrayList<Map<String, String>>) getAll("dewords");
                Bundle bundle = new Bundle();
                bundle.putSerializable("result", items);
                Intent intent = new Intent(MainActivity.this, DeActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        //为ListView注册上下文菜单：
        ListView list = (ListView) findViewById(R.id.IstWords);
        registerForContextMenu(list);
        mDBHelper = new WordsDBHelper(this, "wordsdb.db", null, 1);

    }

    public void onDestroy() {
        super.onDestroy();
        mDBHelper.close();
    }
    private void setWordsListView(ArrayList<Map<String, String>> items) {
        SimpleAdapter adapter = new SimpleAdapter(this, items, R.layout.item, new String[]{
                Words.Word._ID,Words.Word.COLUMN_NAME_WORD, Words.Word.COLUMN_NAME_MEANING, Words.Word.COLUMN_NAME_SAMPLE},
                new int[]{R.id.textId, R.id.textViewWord, R.id.textViewMeaning, R.id.textViewSample});
        ListView list = (ListView) findViewById(R.id.IstWords);
        list.setAdapter(adapter);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu1, menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.select:
                SearchDialog();
                return true;
            case R.id.add:
                InsertDialog();
                return true;
            case R.id.help:
                AlertDialog.Builder dialog=new AlertDialog.Builder(MainActivity.this);
                dialog.setTitle("操作技巧：");
                dialog.setMessage("进入单词本操作");
                dialog.setCancelable(false);
                dialog.setPositiveButton("OK",new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog,int which){

                    }
                });
                dialog.setNegativeButton("Cancel",new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                dialog.show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    //插入单词
    private void InsertUserSql(String strWord, String strMeaning, String strSample) {
        String sql = "insert into words(word,meaning,sample) values(?,?,?)";
        SQLiteDatabase db = mDBHelper.getWritableDatabase();
        db.execSQL(sql, new String[]{strWord, strMeaning, strSample});
    }
    private void Insert(String strWord, String strMeaning, String strSample) {
        SQLiteDatabase db = mDBHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Words.Word.COLUMN_NAME_WORD, strWord);
        values.put(Words.Word.COLUMN_NAME_MEANING, strMeaning);
        values.put(Words.Word.COLUMN_NAME_SAMPLE, strSample);
        long newRowId = 0;
        newRowId = db.insert(Words.Word.TABLE_NAME, null, values);
    }

    private void InsertDialog() {
        final TableLayout tableLayout = (TableLayout) getLayoutInflater().inflate(R.layout.insert, null);
        new AlertDialog.Builder(this)
                .setTitle("插入单词")
                .setView(tableLayout)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String strWord = ((EditText) tableLayout.findViewById(R.id.txtWord)).getText().toString();
                        String strMeaning = ((EditText) tableLayout.findViewById(R.id.txtMeaning)).getText().toString();
                        String strSample = ((EditText) tableLayout.findViewById(R.id.txtSample)).getText().toString();

                        Insert(strWord, strMeaning, strSample);
                        ArrayList<Map<String, String>> items = getAll("words");
                        setWordsListView(items);
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                })
                .create()
                .show();
    }

    //查询单词
    private ArrayList<Map<String, String>> SearchUserSql(String strWordSearch) {
        SQLiteDatabase db = mDBHelper.getReadableDatabase();
        String sql = "select * from words where word like ? ";    //order by word desc";
        Cursor c = db.rawQuery(sql, new String[]{"%" + strWordSearch + "%"});
        return ConvertCursorList(c);
    }

    private ArrayList<Map<String, String>> Search(String strWordSearch) {
        SQLiteDatabase db = mDBHelper.getReadableDatabase();
        String[] projection = {
                Words.Word._ID,
                Words.Word.COLUMN_NAME_WORD,
                Words.Word.COLUMN_NAME_MEANING,
                Words.Word.COLUMN_NAME_SAMPLE
        };
        String sortOrder =Words.Word.COLUMN_NAME_WORD + " DESC";
        String selection = Words.Word.COLUMN_NAME_WORD + " LIKE ?";
        String[] selectionArgs = {"%" + strWordSearch + "%"};
        Cursor c = db.query(
                Words.Word.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null, null,
                sortOrder
        );
        return ConvertCursorList(c);
    }

    private void SearchDialog() {
        final TableLayout tableLayout = (TableLayout) getLayoutInflater().inflate(R.layout.searchterm, null);
        new AlertDialog.Builder(this)
                .setTitle("查询单词")
                .setView(tableLayout)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String txtSearchWord = ((EditText) tableLayout.findViewById(R.id.txtSearchWord)).getText().toString();
                        ArrayList<Map<String, String>> items = null;
                        items = SearchUserSql(txtSearchWord);
                        if (items.size() > 0) {
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("result", items);
                            Intent intent = new Intent(MainActivity.this, SearchActivity.class);
                            intent.putExtras(bundle);
                            startActivity(intent);
                        } else {
                            Toast.makeText(MainActivity.this, "没有找到", Toast.LENGTH_LONG).show();
                        }
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                })
                .create()
                .show();
    }

    //转换光标链表
    private ArrayList<Map<String, String>> ConvertCursorList(Cursor c) {
        ArrayList<Map<String, String>> list = new ArrayList<>();
        if (c.moveToNext()) {
            Map<String, String> map = new HashMap<>();
            map.put(Words.Word.COLUMN_NAME_WORD, c.getString(c.getColumnIndexOrThrow(Words.Word.COLUMN_NAME_WORD)));
            list.add(map);
        }
        return list;
    }

    private ArrayList<Map<String, String>> getAll(String table_name) {
        ArrayList<Map<String, String>> list = new ArrayList<>();
        //Map<String, String> map = null;
            /*
            SQLiteDatabase db = mDBHelper.getReadableDatabase();
            String columns[] = {mDBHelper.ID, mDBHelper.COLUMN_NAME_WORD, mDBHelper.COLUMN_NAME_MEANING, mDBHelper.COLUMN_NAME_SAMPLE};
            Cursor c = db.query(mDBHelper.TABLE_NAME,null, null,null, null, null, null);
            while (c.moveToNext()) {
                String word1=c.getString(c.getColumnIndexOrThrow(mDBHelper.COLUMN_NAME_WORD));
                String meaning1=c.getString(c.getColumnIndexOrThrow(mDBHelper.COLUMN_NAME_MEANING));
                map.put(word1,meaning1);
               // map.put(mDBHelper.COLUMN_NAME_WORD, c.getString(c.getColumnIndexOrThrow(mDBHelper.COLUMN_NAME_WORD)));
               // map.put(mDBHelper.COLUMN_NAME_WORD, c.getString(c.getColumnIndexOrThrow(mDBHelper.COLUMN_NAME_MEANING)));
                //map.put(mDBHelper.COLUMN_NAME_WORD, c.getString(c.getColumnIndexOrThrow(mDBHelper.COLUMN_NAME_SAMPLE)));
                list.add(map);
                map.clear();
            }
            return list;*/

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

/*
<Button
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:id="@+id/create"
        android:text="新建数据库"/>
    <Button
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:id="@+id/create1"
        android:text="新建数据库1"/>
 */
 /*cre = (Button) findViewById(R.id.create);
        cre1=(Button)findViewById(R.id.create1);
        cre.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mDBHelper.getWritableDatabase();
            }
        });
        cre1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mDBHelper.getWritableDatabase();
            }
        });*/
//ArrayList<Map<String, String>> items = getAll();
//setWordsListView(items);
    /*public void adddb(){
        ContentValues cv=new ContentValues();
        cv.put("word","boy");
        cv.put("wordsmeaing","好的");
        cv.put("wordsjuzi","he is a boy");
        mDBHelper.insert("wordsdb",null,cv);
    }*/
