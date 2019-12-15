package com.example.wordsbook;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;
import android.widget.Toast;
import com.example.wordsbook.*;
//import java.sql.*;
//import android.os.Build.*;
public class WordsDBHelper extends SQLiteOpenHelper {
    //public static final String TABLE_NAME="words";
    //public static String ID;
    //public static final String COLUMN_NAME_WORD="word";
    //public static final String COLUMN_NAME_MEANING="meaning";
    //public static final String COLUMN_NAME_SAMPLE="sample";
    private final static String DATABASE_NAME = "wordsdb";
    private final static int DATABASE_VERSION = 1;
    private Context content;
    private static final String myTABLE_NAME="mywords";
    private static final String deTABLE_NAME="dewords";
    private final static String SQL_CREATE_DATABASE="create table "+
            Words.Word.TABLE_NAME+" ( "+
            Words.Word._ID +" INTEGER PRIMARY KEY AUTOINCREMENT, "+
            Words.Word.COLUMN_NAME_WORD+" TEXT"+", "+
            Words.Word.COLUMN_NAME_MEANING+" TEXT"+", "+
            Words.Word.COLUMN_NAME_SAMPLE+" TEXT"+")";

    private final static String SQL_CREATE_DATABASE1="create table "+
           myTABLE_NAME+" ( "+
            Words.Word._ID +" INTEGER PRIMARY KEY AUTOINCREMENT, "+
            Words.Word.COLUMN_NAME_WORD+" TEXT"+", "+
            Words.Word.COLUMN_NAME_MEANING+" TEXT"+", "+
            Words.Word.COLUMN_NAME_SAMPLE+" TEXT"+")";
    private final static String SQL_CREATE_DATABASE2="create table "+
            deTABLE_NAME+" ( "+
            Words.Word._ID +" INTEGER PRIMARY KEY AUTOINCREMENT, "+
            Words.Word.COLUMN_NAME_WORD+" TEXT"+", "+
            Words.Word.COLUMN_NAME_MEANING+" TEXT"+", "+
            Words.Word.COLUMN_NAME_SAMPLE+" TEXT"+")";
    private Context mcontext;

    private final static String SQL_DELETE_DATABASE="DROP TABLE IF EXISTS "+Words.Word.TABLE_NAME;
    /*
    public WordsDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
        public void onCreate (SQLiteDatabase sqLiteDatabase){
            Toast.makeText(content, "begin create", Toast.LENGTH_SHORT).show();
            sqLiteDatabase.execSQL(SQL_CREATE_DATABASE);
            Toast.makeText(content, "create successed", Toast.LENGTH_SHORT).show();
        }
*/
    public WordsDBHelper(Context context,String name,SQLiteDatabase.CursorFactory factory,int version){
        super(context,name,factory,version);
        mcontext=context;
    }
    public void onCreate(SQLiteDatabase db){
        //db = openOrCreateDatabase(DATABASE_NAME, Context.MODE_PRIVATE,null);
        db.execSQL(SQL_CREATE_DATABASE);
        db.execSQL(SQL_CREATE_DATABASE1);
        db.execSQL(SQL_CREATE_DATABASE2);
        Toast.makeText(mcontext, "begin create", Toast.LENGTH_SHORT).show();
    }
    public void onUpgrade(SQLiteDatabase sqLiteDatabase,int oldVersion,int newVersion){
        sqLiteDatabase.execSQL(SQL_DELETE_DATABASE);
        onCreate(sqLiteDatabase);
    }
}
/*
数据库名称：wordsdb
有道单词本：words    word --meaning--sammple
我的单词本：mywords
我的删除本：dewords
 */
