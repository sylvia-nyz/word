package com.example.cly.word;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class collectActivity extends AppCompatActivity {

    WordsDBHelper mDBHelper;
    WordAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collect);
        RecyclerView wordTitleRecyclerView = (RecyclerView)findViewById( R.id.word_title_recycler_view );
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        wordTitleRecyclerView.setLayoutManager(layoutManager);
        adapter=new WordAdapter(getWord());
        wordTitleRecyclerView.setAdapter(adapter);
        //setHasOptionsMenu(true);
        registerForContextMenu( wordTitleRecyclerView );//注册菜单
        mDBHelper=new WordsDBHelper( this);
    }
    class WordAdapter extends RecyclerView.Adapter<WordAdapter.ViewHolder>{
        private List<Word> mWordList;
        private int mPosition = -1;

        public int getPosition() {
            return mPosition;
        }
        public WordAdapter(List<Word> mWordList){
            this.mWordList=mWordList;
        }
        public void removeItem(int position) {
            mWordList.remove(position);
            notifyDataSetChanged();
        }
        public Word getItem(int position){
            return mWordList.get( position );
        }
        class ViewHolder extends RecyclerView.ViewHolder{
            TextView wordNameText;
            TextView wordMeaningText;
            TextView wordUpdateText;
            TextView wordcollect;
            public ViewHolder(View view){
                super(view);
                wordNameText=(TextView)view.findViewById( R.id.word_name );
                wordMeaningText=(TextView)view.findViewById( R.id.word_meaning );
                wordUpdateText=(TextView)view.findViewById( R.id.word_update );
                wordcollect=(TextView)view.findViewById(R.id.wordcollect);
            }
        }
        public void update(List<Word> mWordList){
            this.mWordList=mWordList;
            notifyDataSetChanged();
        }
        public WordAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
            View view=LayoutInflater.from( parent.getContext() ).inflate( R.layout.word_item,parent,false );
            final WordAdapter.ViewHolder holder=new WordAdapter.ViewHolder( view );
            view.setOnClickListener( new View.OnClickListener(){
                public void onClick(View v){
                    Word word=mWordList.get( holder.getAdapterPosition() );

                }
            } );
            return holder;

        }

        @Override
        public void onBindViewHolder(final WordAdapter.ViewHolder holder, final int position) {
            Word word=mWordList.get(position);
            holder.wordNameText.setText(word.getName());
            holder.wordMeaningText.setText( word.getMeaning() );
            holder.wordUpdateText.setText(word.getUpdate());
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    mPosition =holder.getAdapterPosition();
                    return false;
                }
            });
        }

        @Override
        public int getItemCount() {
            return mWordList.size();
        }

    }
    private List<Word> getWord() {
        List<Word> wordList = new ArrayList<>();
        WordsDBHelper dbHelpermDBHelper = new WordsDBHelper(this);
        SQLiteDatabase db = dbHelpermDBHelper.getWritableDatabase();
        Cursor c;
        c = db.query(Words.Word.TABLE_NAME, null, "collect = 'yes'", null, null, null,null);
        if (c.moveToFirst()) {
            do {
                int id = c.getInt(c.getColumnIndex("id"));
                String name = c.getString(c.getColumnIndex("name"));
                String meaning = c.getString(c.getColumnIndex("meaning"));
                String sample = c.getString(c.getColumnIndex("sample"));
                String updatetime = c.getString(c.getColumnIndex("updatetime"));
                String collect = c.getString(c.getColumnIndex("collect"));
                Word word = new Word();
                word.setId(id);
                word.setName(name);
                word.setMeaning(meaning);
                word.setSample(sample);
                word.setUpdate(updatetime);
                word.setCollect(collect);
                wordList.add(word);
            } while (c.moveToNext());
        }
        return wordList;
    }
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        new MenuInflater(this).inflate(R.menu.menu_context_test, menu);
        super.onCreateContextMenu(menu, v, menuInfo);
    }
    public boolean onContextItemSelected(MenuItem item){
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        Word word;
        int id;
        switch(item.getItemId()){
            case R.id.collect:
                word=adapter.getItem(adapter.getPosition());
                id=word.getId();
                changecollect(id,"no");
                adapter.update(getWord());
                break;
        }
        return true;
    }
    private void changecollect(int id,String collect){
        SQLiteDatabase db=mDBHelper.getReadableDatabase();
        String sql="update wordDB set collect=? where id=?";
        String t=id+"";
        db.execSQL( sql,new String[]{collect,t} );
    }
}
