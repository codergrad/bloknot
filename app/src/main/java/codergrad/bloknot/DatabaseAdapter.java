package codergrad.bloknot;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;

public class DatabaseAdapter {
    private DatabaseHelper dbHelper;
    private SQLiteDatabase database;

    public DatabaseAdapter(Context context){
        dbHelper = new DatabaseHelper(context.getApplicationContext());
    }

    public DatabaseAdapter open(){
        database = dbHelper.getWritableDatabase();
        return this;
    }
    public void close(){
        dbHelper.close();
    }
    private Cursor getAllEntries(){
        String[] columns = new String[] {DatabaseHelper.COLUMN_ID, DatabaseHelper.COLUMN_TITLE, DatabaseHelper.COLUMN_CONTENT, DatabaseHelper.COLUMN_DATE};
        return  database.query(DatabaseHelper.TABLE, columns, null, null, null, null, null);
    }
    public ArrayList<Note> getNotes(){
        ArrayList<Note> notes = new ArrayList<>();
        Cursor cursor = getAllEntries();
        while (cursor.moveToNext()){
            int id = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_ID));
            String title = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_TITLE));
            String content = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_CONTENT));
            String date = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_DATE));
            notes.add(new Note(id, title, content, date));
        }
        cursor.close();
        return notes;
    }
    public long getCount(){
        return DatabaseUtils.queryNumEntries(database, DatabaseHelper.TABLE);
    }
    public Note getNote(long id){
        Note note = null;
        String query = String.format("SELECT * FROM %s WHERE %s=?",DatabaseHelper.TABLE, DatabaseHelper.COLUMN_ID);
        Cursor cursor = database.rawQuery(query, new String[]{ String.valueOf(id)});
        if(cursor.moveToFirst()){
            String title = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_TITLE));
            String content = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_CONTENT));
            String date = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_DATE));
        }
        cursor.close();
        return note;
    }
    public long insert(Note note){
        ContentValues cv = new ContentValues();
        cv.put(DatabaseHelper.COLUMN_TITLE, note.getTitle());
        cv.put(DatabaseHelper.COLUMN_CONTENT, note.getContent());
        cv.put(DatabaseHelper.COLUMN_DATE, note.getDate());
        return database.insert(DatabaseHelper.TABLE, null, cv);
    }
    public long delete(long noteID){
        String whereClause = "ID = ?";
        String[] whereArgs = new String[]{String.valueOf(noteID)};
        return database.delete(DatabaseHelper.TABLE, whereClause, whereArgs);
    }
    public long update(Note note){
        String whereClause = DatabaseHelper.COLUMN_ID + "=" + String.valueOf(note.getId());
        ContentValues cv = new ContentValues();
        cv.put(DatabaseHelper.COLUMN_TITLE, note.getTitle());
        cv.put(DatabaseHelper.COLUMN_CONTENT, note.getContent());
        cv.put(DatabaseHelper.COLUMN_DATE, note.getDate());
        return  database.update(DatabaseHelper.TABLE, cv, whereClause, null);
    }
}