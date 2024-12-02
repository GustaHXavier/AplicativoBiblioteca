package com.gustavo.controlelivro.Helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class MyDataBaseHelper extends SQLiteOpenHelper {

    private Context context;
    private static final String DATABASE_NAME = "BookLibrary.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "my_library";
    private static final String COLUNM_ID = "_id";
    private static final String COLUNM_TITLE = "book_title";
    private static final String COLUNM_AUTHOR = "book_author";
    private static final String COLUNM_PAGES = "book_pages";

    public MyDataBaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query =
                "CREATE TABLE " + TABLE_NAME +
                        " (" + COLUNM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COLUNM_TITLE + " TEXT, " +
                        COLUNM_AUTHOR + " TEXT, " +
                        COLUNM_PAGES + " INTEGER);";

        try {
            db.execSQL(query);
            Log.i("Info tabela", "Sucesso ao criar tabela");
        } catch (Exception e) {
            Log.e("Info tabela", "Erro ao criar tabela " + e.getMessage());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void addLivro (String title, String author, int pages) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUNM_TITLE, title);
        cv.put(COLUNM_AUTHOR, author);
        cv.put(COLUNM_PAGES, pages);

       try {
           db.insert(TABLE_NAME, null, cv);
           Toast.makeText(context, "Livro adicionado com sucesso", Toast.LENGTH_SHORT).show();
           Log.i("Info tabela", "Sucesso ao adicionar livro");

       }catch (Exception e){
           Toast.makeText(context, "erro ao adicionar livro", Toast.LENGTH_SHORT).show();
           Log.e("Info tabela", "Erro ao adicionar livro " + e.getMessage());
       }
    }

    public Cursor readAllData (){
        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor c = null;

        if(db != null) {
            c = db.rawQuery(query, null);
        }
        return c;
    }

    public void updateData (String row_id, String title, String author, String pages) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUNM_TITLE, title);
        cv.put(COLUNM_AUTHOR, author);
        cv.put(COLUNM_PAGES, pages);

        try {
            db.update(TABLE_NAME, cv,"_id=?", new String[]{row_id});
            Toast.makeText(context, "Livro atualizado com sucesso", Toast.LENGTH_SHORT).show();
            Log.i("Info tabela", "Sucesso ao atualizar livro");

        }catch (Exception e) {
            Toast.makeText(context, "erro ao atualizar livro", Toast.LENGTH_SHORT).show();
            Log.e("Info tabela", "Erro ao atualizar livro " + e.getMessage());
        }
    }

    public void deleteOneRow (String row_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        try{
            db.delete(TABLE_NAME, "id=?", new String[]{row_id});
            Toast.makeText(context, "Livro excluido com sucesso", Toast.LENGTH_SHORT).show();
            Log.i("Info tabela", "Sucesso ao deletar livro");
        } catch (Exception e) {
            Toast.makeText(context, "erro ao excluir livro", Toast.LENGTH_SHORT).show();
            Log.e("Info tabela", "Erro ao excluido livro " + e.getMessage());
        }

    }
}
