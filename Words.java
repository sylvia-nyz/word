package com.example.cly.word;

import android.provider.BaseColumns;

public class Words {
    public Words(){

    }
    public static abstract class Word implements BaseColumns {
            public static final String TABLE_NAME="wordDB";
            public static final String COLUMN_NAME_WORD="name";
            public static final String COLUMN_NAME_MEANING="meaning";//单词含义
            public static final String COLUMN_NAME_SAMPLE="sample";//单词实例

    }
}
