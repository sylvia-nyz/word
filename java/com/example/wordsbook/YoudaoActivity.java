package com.example.wordsbook;

import androidx.appcompat.app.AppCompatActivity;
import android.net.Uri;
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
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.content.ContentValues;
import android.widget.TableLayout;
import android.content.DialogInterface;
import android.widget.EditText;
import android.view.ContextMenu.ContextMenuInfo;
import android.content.Context;
import android.widget.Toast;
import android.util.Log;
public class YoudaoActivity extends FragmentActivity {
    private ListView list_yd;  //有道链表；
    private Button back;
    private Context context;
    WordsDBHelper mDBHelper;
    SQLiteDatabase.CursorFactory factory;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youdao);

        //mDBHelper=new WordsDBHelper(this,"wordsdb.db",factory,1);//会出现空指针异常；
        mDBHelper = new WordsDBHelper(this, "wordsdb.db", null, 1);
        list_yd=(ListView)findViewById(R.id.list_youdao);
        back=(Button)findViewById(R.id.back_youdao);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(YoudaoActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
//要显示的内容的项数直接在适配器中取就可以，还要修改.xml文件.
        //输出返回过来的单词：
        Intent intent=getIntent();
        ArrayList<Map<String,String>>items=(ArrayList<Map<String, String>>)intent.getSerializableExtra("result");
        SimpleAdapter adapter = new SimpleAdapter(this, items, R.layout.item, new String[]{
               Words.Word._ID,Words.Word.COLUMN_NAME_WORD, Words.Word.COLUMN_NAME_MEANING, Words.Word.COLUMN_NAME_SAMPLE},//
                new int[]{R.id.textId,R.id.textViewWord, R.id.textViewMeaning, R.id.textViewSample});
        list_yd.setAdapter(adapter);
        this.registerForContextMenu(list_yd);
    }
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
        getMenuInflater().inflate(R.menu.menu2, menu);
        super.onCreateContextMenu(menu,v,menuInfo);
    }

    public boolean onContextItemSelected(MenuItem item) {//上下文菜单
        TextView textid = null;
        TextView textword = null;
        TextView textmeaning = null;
        TextView textsample = null;
        AdapterView.AdapterContextMenuInfo info = null;
        View itemview = null;
        switch (item.getItemId()) {

            case R.id.delete:
                info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();//提供上下文菜单的信息
                itemview = info.targetView;  //上下文菜单的子视图
                textid = (TextView) itemview.findViewById(R.id.textId);
                textword = (TextView) itemview.findViewById(R.id.textViewWord);
                textmeaning = (TextView) itemview.findViewById(R.id.textViewMeaning);
                textsample = (TextView) itemview.findViewById(R.id.textViewSample);
                if (textid != null) {
                    String strId = textid.getText().toString();
                    Log.v("YoudaoActivity",strId);
                    DeleteDialog(strId);
                }
                String de_strWord = textword.getText().toString();
                String de_strMeaning = textmeaning.getText().toString();
                String de_strSample = textsample.getText().toString();
                Insert("dewords",de_strWord,de_strMeaning,de_strSample);
                break;
            case R.id.update:
                info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();//提供上下文菜单的信息
                itemview = info.targetView;  //上下文菜单的子视图
                textid = (TextView) itemview.findViewById(R.id.textId);
                textword = (TextView) itemview.findViewById(R.id.textViewWord);
                textmeaning = (TextView) itemview.findViewById(R.id.textViewMeaning);
                textsample = (TextView) itemview.findViewById(R.id.textViewSample);
                if (textid != null && textword != null && textmeaning != null && textsample != null) {
                    String strId = textid.getText().toString();
                    String strWord = textword.getText().toString();
                    String strMeaning = textmeaning.getText().toString();
                    String strSample = textsample.getText().toString();
                    UpdateDialog(strId, strWord, strMeaning, strSample);
                }
                break;
            case R.id.shoucang:
                info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();//提供上下文菜单的信息
                itemview = info.targetView;  //上下文菜单的子视图
                textid = (TextView) itemview.findViewById(R.id.textId);
                textword = (TextView) itemview.findViewById(R.id.textViewWord);
                textmeaning = (TextView) itemview.findViewById(R.id.textViewMeaning);
                textsample = (TextView) itemview.findViewById(R.id.textViewSample);
                if(textid!=null){
                String shoucang_strWord = textword.getText().toString();
                String shoucang_strMeaning = textmeaning.getText().toString();
                String shoucang_strSample = textsample.getText().toString();
                Insert("mywords",shoucang_strWord,shoucang_strMeaning,shoucang_strSample);
                Toast.makeText(YoudaoActivity.this, "收藏成功", Toast.LENGTH_LONG).show();
                break;
            }
            case R.id.chakan:
                info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();//提供上下文菜单的信息
                itemview = info.targetView;  //上下文菜单的子视图
                textid = (TextView) itemview.findViewById(R.id.textId);
                textword = (TextView) itemview.findViewById(R.id.textViewWord);
                textmeaning = (TextView) itemview.findViewById(R.id.textViewMeaning);
                textsample = (TextView) itemview.findViewById(R.id.textViewSample);
                String chakan_strWord = textword.getText().toString();
                String chakan_strMeaning = textmeaning.getText().toString();
                String chakan_strSample = textsample.getText().toString();
                Intent intent=new Intent(YoudaoActivity.this,ChakanActivity.class);
                intent.putExtra("word",chakan_strWord);
                intent.putExtra("meaning",chakan_strMeaning);
                intent.putExtra("sample",chakan_strSample);
                startActivity(intent);
            case R.id.fayin:
                info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();//提供上下文菜单的信息
                itemview = info.targetView;  //上下文菜单的子视图
                textword = (TextView) itemview.findViewById(R.id.textViewWord);
                String str=textword.getText().toString();
                Intent intent2=new Intent(Intent.ACTION_VIEW);
                intent2.setData(Uri.parse("https://m.youdao.com/dict?le=eng&q="+str));
                startActivity(intent2);

        }
        return true;
    }


    //删除单词
    private void DeleteUserSql(String strId) {
        String sql = "delete from words where _ID='" + strId + "'";
        //mDBHelper=new WordsDBHelper(tableLayout.getContext());
        SQLiteDatabase db = mDBHelper.getReadableDatabase();
        db.execSQL(sql);
    }

    private void Delete(String strId) {
        SQLiteDatabase db = mDBHelper.getReadableDatabase();
        String selection = Words.Word._ID + "=?";
        String[] selectionArgs = {strId};
        db.delete(Words.Word.TABLE_NAME, selection, selectionArgs);   //TABLE_NAME为words;
    }

    private void DeleteDialog(final String strId) {
        new AlertDialog.Builder(this)
                .setTitle("删除单词")
                .setMessage("是否真的删除单词？")
                .setPositiveButton(
                "确定", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogInterface, int i) {
                        DeleteUserSql(strId);
                        //Toast.makeText(context, "删除成功", Toast.LENGTH_SHORT).show();
                        ArrayList<Map<String, String>> items = getAll("words");
                        setWordsListView(items);
                        Toast.makeText(YoudaoActivity.this, "删除成功", Toast.LENGTH_LONG).show();
                    }
                })

                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(YoudaoActivity.this, "取消删除", Toast.LENGTH_LONG).show();
                    }
                })
                .create().show();
    }
    //更新单词
    private void UpdateUserId(String strId, String strWord, String strMeaning, String strSample) {
        SQLiteDatabase db = mDBHelper.getWritableDatabase();
        String sql = "update words set word=?,meaning=?,sample=? where _ID=?";
        db.execSQL(sql, new String[]{strWord, strMeaning, strSample, strId});
    }

    private void Update(String strId, String strWord, String strMeaning, String strSample) {
        SQLiteDatabase db = mDBHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Words.Word.COLUMN_NAME_WORD, strWord);
        values.put(Words.Word.COLUMN_NAME_MEANING, strMeaning);
        values.put(Words.Word.COLUMN_NAME_SAMPLE, strSample);
        String selection = Words.Word._ID +"= ?";
        String[] selectionArgs = {strId};
        int count = db.update("words", values, selection, selectionArgs);
        //db.execSQL("delete from words where ID="+);
    }

    private void UpdateDialog(final String strId, final String strWord, final String strMeaning, final String strSample) {
        final TableLayout tableLayout = (TableLayout) getLayoutInflater().inflate(R.layout.insert, null);
        ((EditText) tableLayout.findViewById(R.id.txtWord)).setText(strWord);
        ((EditText) tableLayout.findViewById(R.id.txtMeaning)).setText(strMeaning);
        ((EditText) tableLayout.findViewById(R.id.txtSample)).setText(strSample);
        new AlertDialog.Builder(this)
                .setTitle("更新单词")
                .setView(tableLayout)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String strNewWord = ((EditText) tableLayout.findViewById(R.id.txtWord)).getText().toString();
                        String strNewMeaning = ((EditText) tableLayout.findViewById(R.id.txtMeaning)).getText().toString();
                        String strNewSample = ((EditText) tableLayout.findViewById(R.id.txtSample)).getText().toString();
                        Update(strId, strNewWord, strNewMeaning, strNewSample);
                        ArrayList<Map<String, String>> items = getAll("words");
                        setWordsListView(items);
                        Log.v("YoudaoActivity","更新成功");
                        Log.v("YoudaoActivity",strNewMeaning);
                        Toast.makeText(YoudaoActivity.this, "更新成功", Toast.LENGTH_LONG).show();
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(YoudaoActivity.this, "取消更新", Toast.LENGTH_LONG).show();
                    }
                })
                .create()
                .show();
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
        while(c.moveToNext()){
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

    private void Insert(String table_name,String strWord, String strMeaning, String strSample) {
        SQLiteDatabase db = mDBHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Words.Word.COLUMN_NAME_WORD, strWord);
        values.put(Words.Word.COLUMN_NAME_MEANING, strMeaning);
        values.put(Words.Word.COLUMN_NAME_SAMPLE, strSample);
        db.insert(table_name, null, values);
    }
}


/*


 */