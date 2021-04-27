package codergrad.bloknot;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.ContactsContract;
import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.database.sqlite.SQLiteDatabase;
import android.widget.ArrayAdapter;
import android.widget.SimpleCursorAdapter;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    DatabaseHelper databaseHelper;
    DatabaseAdapter dbAdapter;
    SQLiteDatabase db;
    Cursor userCursor;
    SimpleCursorAdapter userAdapter;
    ArrayAdapter<Note> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbAdapter = new DatabaseAdapter(this);
        dbAdapter.open();
        ArrayList<Note> dataNotes = dbAdapter.getNotes();
        dbAdapter.close();

        //// PLACEHOLDERS
        dataNotes.add(new Note(0,"First title", "Content","27.04.2021"));
        dataNotes.add(new Note(1,"2 title", "22Content","27.04.2021"));
        dataNotes.add(new Note(2,"3333 title", "Content","27.04.2021"));
        ////

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        RecyclerView recyclerView = findViewById(R.id.rvNumbers);
        int numberOfColumns = 3;
        recyclerView.setLayoutManager(new GridLayoutManager(this, numberOfColumns));
        MyRecyclerViewAdapter adapter = new MyRecyclerViewAdapter(this, dataNotes);

        recyclerView.setAdapter(adapter);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            long clickCounter = 0; //рудимент
            @Override
            public void onClick(View view) {
                /** Это старый код, который добавлял без DatabaseAdapter даннные напрямую в БД. Будет переписан
                SQLiteDatabase db = getBaseContext().openOrCreateDatabase("app.db", MODE_PRIVATE, null);
                db.execSQL("CREATE TABLE IF NOT EXISTS notes(indx INTEGER, title TEXT, content TEXT, date TEXT)");
                db.execSQL("INSERT INTO notes VALUES (0, 'Sample title', 'Sample Content', '22.01.2021')");
                Cursor query = db.rawQuery("SELECT * FROM notes", null);
                String content = "ikik";
                while(query.moveToNext()) {
                   content = query.getString(1);
                }
                data.add(content);
                adapter.notifyItemInserted(clickCounter);
                clickCounter++;
                query.close();
                db.close();
                 **/
            }

        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public void OnResume(){
        super.onResume();
        DatabaseAdapter adapter = new DatabaseAdapter(this);
        adapter.open();
        ArrayList<Note> notes = adapter.getNotes();


        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, notes);
        adapter.close();
    }
    public void add(View view){
        Intent intent = new Intent(this, NotesActivity.class);
        startActivity(intent);
    }
}