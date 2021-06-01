package codergrad.bloknot;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;


public class NoteActivity extends AppCompatActivity {
DatabaseAdapter dbAdapter;
EditText noteTitle;
EditText noteContent;
long noteID = 0;
long extraID;
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
        extraID = extraNote.getId();
        dbAdapter = new DatabaseAdapter(this);
    }

    private void goHome(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
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
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}