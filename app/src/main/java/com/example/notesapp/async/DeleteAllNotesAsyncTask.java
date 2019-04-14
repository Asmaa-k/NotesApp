package com.example.notesapp.async;

import android.os.AsyncTask;

import com.example.notesapp.persistence.NoteDao;

public class DeleteAllNotesAsyncTask extends AsyncTask<Void,Void,Void> {

    private NoteDao noteDao;

    public DeleteAllNotesAsyncTask(NoteDao noteDao) {
        this.noteDao = noteDao;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        noteDao.deleteAll();
        return null;
    }
}
