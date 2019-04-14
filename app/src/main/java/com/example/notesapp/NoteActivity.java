package com.example.notesapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.notesapp.entities.Note;
import com.example.notesapp.persistence.NoteRepository;

public class NoteActivity extends AppCompatActivity {

    private LinedEditText mLinedEditText;
    private EditText mEditTitle;
    private NoteRepository mNoteRepository;
    private Note mNoteFinal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow);

        mLinedEditText = findViewById(R.id.note_text);
        mEditTitle = findViewById(R.id.note_edit_title);
        mNoteRepository = new NoteRepository(this);

        enableEditTexts();

        if (getIncomingIntent()) {
            setTitle("Edit Note");
            properties();
        } else setTitle("New Note");
    }

    private void properties() {
        if (mNoteFinal != null) {
            mEditTitle.setText(mNoteFinal.getTitle());
            mLinedEditText.setText(mNoteFinal.getNoteContent());
        }
    }

    private boolean getIncomingIntent() {
        if (getIntent().hasExtra("selected_note")) {
            mNoteFinal = getIntent().getParcelableExtra("selected_note");
            return true;
        }
        return false;
    }

    public void saveNewNote() {
        String title = mEditTitle.getText().toString();
        String description = mLinedEditText.getText().toString();
        long timestamp = System.currentTimeMillis();

        if (title.trim().isEmpty() || description.trim().isEmpty()) {
            Toast.makeText(this, "Please insert a title and description", Toast.LENGTH_SHORT).show();
            return;
        }

        Note note = new Note(title, description, timestamp);
        mNoteRepository.insert(note);
        Toast.makeText(this, "Note Insert", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(this, NoteListActivity.class));
    }

    private void upDateNote() {
        String title = mEditTitle.getText().toString();
        String description = mLinedEditText.getText().toString();
        long timestamp = System.currentTimeMillis();

        if (title.trim().isEmpty() || description.trim().isEmpty()) {
            Toast.makeText(this, "Please insert a title and description", Toast.LENGTH_SHORT).show();
            return;
        }

        if (title.equals(mNoteFinal.getTitle()) && description.equals(mNoteFinal.getNoteContent())) {
            startActivity(new Intent(this, NoteListActivity.class));
            return;
        }
        Note note = new Note(title, description, timestamp);
        if (mNoteFinal != null) {
            int id = mNoteFinal.getId();
            note.setId(id);
            mNoteRepository.update(note);
            Toast.makeText(this, "Note Updated", Toast.LENGTH_SHORT).show();
        }
        startActivity(new Intent(this, NoteListActivity.class));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.save, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.save_note)
            if (getIntent().hasExtra("selected_note")) {
                upDateNote();
            } else {
                //setNoteProperties();
                saveNewNote();
            }
        return super.onOptionsItemSelected(item);
    }

    private void enableEditTexts() {
        mLinedEditText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mLinedEditText.setFocusable(true);
                mLinedEditText.setFocusableInTouchMode(true);
                return false;
            }
        });

        mEditTitle.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mEditTitle.setFocusable(true);
                mEditTitle.setFocusableInTouchMode(true);
                return false;
            }
        });
    }
}
