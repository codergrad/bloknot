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

public class NoteActivity extends AppCompatActivity {
DatabaseAdapter dbAdapter;
EditText noteTitle;
EditText noteContent;
long noteID = 0;
public static final String EXTRA_MESSAGE = "ru.codergrad.bloknot.MESSAGE";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        noteTitle = (EditText) findViewById(R.id.noteTitle);
        noteContent = (EditText) findViewById(R.id.noteContent);

        dbAdapter = new DatabaseAdapter(this);


        Button saveButton = findViewById(R.id.saveButton);
        saveButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                save(v);
              /*  Snackbar.make(v, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/

            }
        });
        }
   public void save(View view){
       String title = noteTitle.getText().toString();
       String content = noteContent.getText().toString();
       Note note = new Note(noteID, title, content, null);
       dbAdapter.open();
       noteID = dbAdapter.getCount();
       dbAdapter.insert(note);
       noteID++;
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