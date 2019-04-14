package com.example.notesapp;

import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.notesapp.adapter.NoteAdapter;
import com.example.notesapp.entities.Note;
import com.example.notesapp.persistence.NoteRepository;

import java.util.ArrayList;
import java.util.List;

public class NoteListActivity extends AppCompatActivity {

    private ArrayList<Note> mNotes = new ArrayList<>();
    private NoteRepository mNoteRepository;

    private NoteAdapter adapter;
    private RecyclerView mRecyclerView;
    FloatingActionButton mButtonAddNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_list);

        mNoteRepository = new NoteRepository(this);
        initRecyclerView();
        retrieveNotes();

       mButtonAddNote = findViewById(R.id.button_add_note);
        mButtonAddNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(NoteListActivity.this, NoteActivity.class));
            }
        });
    }

    private void retrieveNotes() {
        mNoteRepository.getAllNotes().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(@Nullable List<Note> notes) {
                if (mNotes.size() > 0) {
                    mNotes.clear();
                }
                if (notes != null) {
                    mNotes.addAll(notes);
                }
                adapter.setNotes(notes);
            }
        });
    }

    private void initRecyclerView() {
        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setHasFixedSize(true);
        adapter = new NoteAdapter(mNotes);
        mRecyclerView.setAdapter(adapter);
        deleteNoteItem();
        EdieNoteItem();
    }

    private void EdieNoteItem() {
        adapter.setOnItemClickListener(new NoteAdapter.OnNoteItemClickListener() {
            @Override
            public void onItemClick(Note note) {
                Intent intent = new Intent(NoteListActivity.this, NoteActivity.class);
                intent.putExtra("selected_note", note);
                Toast.makeText(NoteListActivity.this, note.getTitle().toString(),Toast.LENGTH_SHORT).show();
                startActivity(intent);
            }
        });
    }

    private void deleteNoteItem() {
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                mNoteRepository.delete(adapter.getNoteAtPos(viewHolder.getAdapterPosition()));
                Toast.makeText(NoteListActivity.this, "Note Delete", Toast.LENGTH_LONG).show();
            }
        }).attachToRecyclerView(mRecyclerView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.delete_all) {
            mNoteRepository.deleteAllNotes();
            Toast.makeText(this, "All Notes Deleted", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }
}
