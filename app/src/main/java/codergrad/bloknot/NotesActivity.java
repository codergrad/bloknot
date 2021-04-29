package codergrad.bloknot;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class NotesActivity extends AppCompatActivity {
    private EditText titleBox;
    private EditText contentBox;
    private Button delButton;

    private DatabaseAdapter adapter;
    private long noteID = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);

        titleBox = (EditText) findViewById(R.id.title);
        contentBox = (EditText) findViewById(R.id.content);
        delButton = (Button) findViewById(R.id.deleteButton);
        adapter = new DatabaseAdapter(this);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            noteID = extras.getLong("id");
        } if (noteID > 0) {
            adapter.open();
            Note note = adapter.getNote(noteID);
            titleBox.setText(note.getTitle());
            contentBox.setText(note.getContent());
            adapter.close();
        } else {
            delButton.setVisibility(View.GONE);
        }
    }
    public void save(View view){
        String title = titleBox.getText().toString();
        String content = contentBox.getText().toString();
        Note note = new Note(noteID, title, content, null);
        adapter.open();
        if (noteID > 0){
            adapter.update(note);
        } else {
            adapter.insert(note);
        }
        adapter.close();
        goHome();
    }
    public void delete(View view){
        adapter.open();
        adapter.delete(noteID);
        adapter.close();
        goHome();
    }
    private void goHome(){
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }
}