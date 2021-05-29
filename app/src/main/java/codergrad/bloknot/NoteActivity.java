package codergrad.bloknot;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import android.text.TextWatcher;
import static androidx.navigation.Navigation.findNavController;


public class NoteActivity extends AppCompatActivity {
DatabaseAdapter dbAdapter;
EditText noteTitle;
EditText noteContent;
long noteID = 0;
Intent intent = getIntent();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        noteTitle = (EditText) findViewById(R.id.noteTitle);
        noteContent = (EditText) findViewById(R.id.noteContent);
        Intent intent = getIntent();
        Note extraNote = intent.getParcelableExtra("extraNoteKey");
        noteTitle.setText(extraNote.getTitle());
        noteContent.setText(extraNote.getContent());
        long extraID = extraNote.getId();
        dbAdapter = new DatabaseAdapter(this);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == android.R.id.home) {
                    Intent intent = getIntent();
                    Note extraNote = intent.getParcelableExtra("extraNoteKey");
                    String title = noteTitle.getText().toString();
                    String content = noteContent.getText().toString();
                    dbAdapter.open();
                    noteID = dbAdapter.getCount();
                    if (extraID > noteID) {
                        Note note = new Note(noteID, title, content, null);
                        dbAdapter.insert(note);
                        noteID++;
                    } else {
                        extraNote.setTitle(title);
                        extraNote.setContent(content);
                        dbAdapter.update(extraNote);
                    }
                    dbAdapter.close();
                    goHome();
                    return true;
                }
                return false;
            }
        });

        Button saveButton = findViewById(R.id.saveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save(v, extraID, extraNote);

            }
        });
    }
   public void save(View v, long extraID, Note extraNote){
       String title = noteTitle.getText().toString();
       String content = noteContent.getText().toString();
       dbAdapter.open();
       noteID = dbAdapter.getCount();
       if (extraID > noteID) {
           Note note = new Note(noteID, title, content, null);
           dbAdapter.insert(note);
           noteID++;
       } else {
           extraNote.setTitle(title);
           extraNote.setContent(content);
           dbAdapter.update(extraNote);
       }
       dbAdapter.close();
        goHome();
   }

    private void goHome(){
        Intent intent = new Intent(this, MainActivity.class);
        String title = noteTitle.getText().toString();
        String content = noteContent.getText().toString();
       // intent.putExtra(EXTRA_MESSAGE, title);
       // intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP); //вызывало проблемы
        startActivity(intent);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch (id)
        {
            case android.R.id.home:
               // save( view, extraID, extraNote);
                System.out.println("HOME BUTTON PRESSED");
                return true;
        }
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}