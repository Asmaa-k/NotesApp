package com.example.notesapp.persistence;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.content.Context;

import com.example.notesapp.async.DeleteAllNotesAsyncTask;
import com.example.notesapp.async.DeleteNoteAsyncTask;
import com.example.notesapp.async.InsertNoteAsyncTask;
import com.example.notesapp.async.UpdateNoteAsyncTask;
import com.example.notesapp.entities.Note;

import java.util.List;

public class NoteRepository {
    private NoteDao noteDao;
    private LiveData<List<Note>> allNotes;

    public NoteRepository(Context context) {
        NoteDataBase db = NoteDataBase.getInstance(context);
        noteDao = db.noteDao();
        allNotes = noteDao.getAllNotes();
    }

    public void insert(Note note) {
        new InsertNoteAsyncTask(noteDao).execute(note);
    }

    public void update(Note note) {
        new UpdateNoteAsyncTask(noteDao).execute(note);
    }

    public void delete(Note note) {
        new DeleteNoteAsyncTask(noteDao).execute(note);
    }

    public void deleteAllNotes() {
        new DeleteAllNotesAsyncTask(noteDao).execute();
    }

    public LiveData<List<Note>> getAllNotes() {
        return allNotes;
    }
}
