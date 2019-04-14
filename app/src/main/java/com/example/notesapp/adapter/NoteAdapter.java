package com.example.notesapp.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.notesapp.R;
import com.example.notesapp.entities.Note;
import com.example.notesapp.util.Utility;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteHolder> {

    private OnNoteItemClickListener mListener;
    private List<Note> mNotes = new ArrayList<>();

    public NoteAdapter(ArrayList<Note> mNotes) {
        this.mNotes = mNotes;
    }

    @NonNull
    @Override
    public NoteHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.note_items, viewGroup, false);
        return new NoteHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteHolder noteHolder, int i) {

        Utility utl = new Utility();
        Note currentNote = mNotes.get(i);
        noteHolder.textViewTitle.setText(currentNote.getTitle());
        noteHolder.textViewNoteContent.setText(currentNote.getNoteContent());

        long x = currentNote.getTimeStamp();


        Date date = new Date(x);
        noteHolder.textViewDate.setText(utl.parseDate(utl.formatDate(date)));
        noteHolder.textViewTime.setText(utl.formatTime(date));
    }

    @Override
    public int getItemCount() {
        return mNotes.size();
    }

    public void setNotes(List<Note> notes) {
        this.mNotes = notes;
        notifyDataSetChanged();
    }

    public Note getNoteAtPos(int i)
    {
        return mNotes.get(i);
    }

    class NoteHolder extends RecyclerView.ViewHolder {
        private TextView textViewTitle;
        private TextView textViewNoteContent;
        private TextView textViewDate;
        private TextView textViewTime;

        public NoteHolder(View itemView) {
            super(itemView);

            textViewTitle = itemView.findViewById(R.id.text_view_title);
            textViewNoteContent = itemView.findViewById(R.id.text_view_content);
            textViewDate = itemView.findViewById(R.id.text_view_date);
            textViewTime = itemView.findViewById(R.id.text_view_time);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int i = getAdapterPosition();
                    if (mListener != null && i != RecyclerView.NO_POSITION) {
                        mListener.onItemClick(mNotes.get(i));
                    }
                }
            });
        }


    }

    public interface OnNoteItemClickListener {
        void onItemClick(Note note);
    }

    public void setOnItemClickListener(OnNoteItemClickListener mListener) {
        this.mListener = mListener;
    }
}
