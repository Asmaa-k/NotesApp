package com.example.notesapp.async;

import android.os.AsyncTask;

import com.example.notesapp.entities.Note;
import com.example.notesapp.persistence.NoteDao;

public class InsertNoteAsyncTask extends AsyncTask<Note, Void, Void> {

        private NoteDao noteDao;

        public InsertNoteAsyncTask(NoteDao noteDao) {
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            noteDao.insert(notes[0]);
            return null;
        }
    }
