package com.android.sqlite_2500;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "db_note";
    //CASE SENSETIVE versi database harus berbeda dr aplikasi lainnya jika sama aplikasi harus diuninstall
    private static final int DATABASE_VERSION = 1;

    //Variable Table
    private static final String TABLE_NAME = "table_note";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_TITLE = "title";
    private static final String COLUMN_NOTE = "note";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE " + TABLE_NAME + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COLUMN_TITLE + "TEXT," +
                COLUMN_NOTE + "TEXT" +
                ")";

        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if(oldVersion != newVersion){
            db.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME);
            onCreate(db);
        }
    }

    //untuk menampilkan table
    public ArrayList<Note> getAllNote(){

        ArrayList<Note> noteList = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.query(TABLE_NAME, new String[]{COLUMN_ID,COLUMN_TITLE,COLUMN_NOTE},null,null,null,null, null);

        if(c != null && c.moveToFirst()){
            do{
                Note note = new Note();

                note.setId(Integer.parseInt(c.getString(0)));
                note.setTitle(c.getString(1));
                note.setNote(c.getString(2));

                noteList.add(note);
            }while (c.moveToNext());
        }

        db.close();
        return noteList;
    }

    //untuk menambahkan
    public void addNote (Note note){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_TITLE,note.getTitle());
        values.put(COLUMN_NOTE,note.getNote());

        db.insert(TABLE_NAME, null,values);
        db.close();
    }

    //untuk menghapus
    public void deleteNote(String id) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_NAME, "id = " + id, null);
    }
}
