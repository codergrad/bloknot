package codergrad.bloknot;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class UserAcitvity extends AppCompatActivity {
    EditText titleBox;
    EditText dateBox;
    Button delButton;
    Button saveButton;

    DatabaseHelper sqlHelper;
    SQLiteDatabase db;
    Cursor userCursor;
    long userId=0;
    @Override
    protected void OnCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_noteslist);

        titleBox = (EditText) findViewById(R.id.title);
        dateBox = (EditText) findViewById(R.id.date);
        delButton = (Button) findViewById(R.id.delButton);
        saveButton = (Button) findViewById(R.id.saveButton);

        sqlHelper = new DatabaseHelper(this);
        db = sqlHelper.open();

        Bundle extras = getIntent().getExtras();
        if (extras != null){
            userId = extras.getLong("id");
        }
        if (userId > 0) {
            userCursor = db.rawQuery("select * from" + DatabaseHelper.TABLE +
                    " where " + DatabaseHelper.COLUMN_ID + "=?",
                    new String[]{String.valueOf(userId)});
            userCursor.moveToFirst();
            titleBox.setText("")
        }
    }

}
