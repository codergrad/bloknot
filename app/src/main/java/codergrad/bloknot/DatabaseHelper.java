package codergrad.bloknot;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.IOException;

class DatabaseHelper extends SQLiteOpenHelper {
    private static String DB_PATH;
    private static String DB_NAME = "database.db";
    private static final int SCHEMA = 1;
    static final String TABLE = "notes";

    static final String COLUMN_ID = "rowid";
    static final String COLUMN_TITLE = "Title";
    static final String COLUMN_CONTENT = "Content";
    static final String COLUMN_DATE = "Date";
    private Context myContext;

    public DatabaseHelper(@Nullable Context context) {
        //, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version
        super(context, DB_NAME, null, SCHEMA);
        this.myContext=context;
        assert context != null;
        DB_PATH = context.getFilesDir().getPath() + DB_NAME;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE + " ("+ COLUMN_ID +" INTEGER PRIMARY KEY AUTOINCREMENT, "+ COLUMN_TITLE +" TEXT, "+ COLUMN_CONTENT +" TEXT, "+ COLUMN_DATE +" TEXT);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE);
        onCreate(db);
    }

    void create_db(){

        InputStream myInput = null;
        OutputStream myOutput = null;
        try {
            File file = new File(DB_PATH);
            if (!file.exists()) {
                myInput = myContext.getAssets().open(DB_NAME);
                String outFileName = DB_PATH;
                myOutput = new FileOutputStream(outFileName);

                byte[] buffer = new byte[1024];
                int length;
                while ((length = myInput.read(buffer)) > 0) {
                    myOutput.write(buffer, 0, length);
                }
                myOutput.flush();
        }
        }
        catch (IOException ex){
            Log.d("DatabaseHelper", ex.getMessage());
        }
        finally {
            try {
                if(myOutput!=null) myOutput.close();
                if(myInput!=null) myInput.close();
            }
            catch (IOException ex){
                Log.d("DatabaseHelper", ex.getMessage());
            }
        }
    }
    public  SQLiteDatabase open()throws SQLException{
        return SQLiteDatabase.openDatabase(DB_PATH, null, SQLiteDatabase.OPEN_READWRITE);
    }
}
