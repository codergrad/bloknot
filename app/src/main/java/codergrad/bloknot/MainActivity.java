package codergrad.bloknot;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.database.sqlite.SQLiteDatabase;
import android.widget.ArrayAdapter;
import android.widget.SimpleCursorAdapter;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity  {

    RecyclerView recyclerView;
    DatabaseHelper databaseHelper;
    DatabaseAdapter dbAdapter;
    SQLiteDatabase db;
    Cursor userCursor;
    SimpleCursorAdapter userAdapter;
    ArrayAdapter<Note> arrayAdapter;
    private Bundle savedInstanceState;
    ArrayList<Note> dataNotes;
    private Object Note;
    private Object ArrayList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setDataNotes();
        buildRecyclerView(dataNotes);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NewNote();
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
        switch (id)
        {
            case R.id.action_settings:

                Intent intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);

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
    }
    protected void NewNote(){
          Intent intent = new Intent(this, NoteActivity.class);
          long noteID = dataNotes.size() + 1;
          intent.putExtra("extraNoteKey", new Note(noteID, "", "", ""));
          startActivity(intent);
    }

    public ArrayList<codergrad.bloknot.Note> setDataNotes(){
        dbAdapter = new DatabaseAdapter(this);
        dbAdapter.open();
        dataNotes = dbAdapter.getNotes();
        dbAdapter.close();
        return dataNotes;
    }
    public void buildRecyclerView(ArrayList<Note> dataNotes){
        RecyclerView recyclerView = findViewById(R.id.rvNumbers);
        int numberOfColumns = 3;
        recyclerView.setLayoutManager(new GridLayoutManager(this, numberOfColumns));
        MyRecyclerViewAdapter adapter = new MyRecyclerViewAdapter(this, dataNotes, new MyRecyclerViewAdapter.ItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(MainActivity.this, NoteActivity.class);
                intent.putExtra("extraNoteKey", dataNotes.get(position));
                startActivity(intent);
            }
        });
        recyclerView.setAdapter(adapter);
    }
}