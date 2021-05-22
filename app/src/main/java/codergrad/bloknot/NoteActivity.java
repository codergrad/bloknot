package codergrad.bloknot;

import androidx.appcompat.app.AppCompatActivity;

 import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

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

        noteTitle = (EditText) findViewById(R.id.noteTitle);
        noteContent = (EditText) findViewById(R.id.noteContent);
        Intent intent = getIntent();
        Note extraNote = intent.getParcelableExtra("extraNoteKey");
        noteTitle.setText(extraNote.getTitle());
        noteContent.setText(extraNote.getContent());
        long extraID = extraNote.getId();
        dbAdapter = new DatabaseAdapter(this);

        Button saveButton = findViewById(R.id.saveButton);
        saveButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                save(v, extraID, extraNote);
              /*  Snackbar.make(v, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/

            }
        });
        }
   public void save(View view, long extraID, Note extraNote){
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
}