package com.ezzy.notepad

import android.view.LayoutInflater

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class NoteAdapter : RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {
    private var notes: List<Note> = emptyList() // Cached copy of notes

    class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val noteTitle: TextView = itemView.findViewById(R.id.noteTitle)
        val noteContent: TextView = itemView.findViewById(R.id.noteContent)
        val noteTimestamp: TextView = itemView.findViewById(R.id.noteTimestamp)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_note, parent, false)
        return NoteViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val currentNote = notes[position]
        holder.noteTitle.text = currentNote.title
        holder.noteContent.text = currentNote.content
        holder.noteTimestamp.text = currentNote.timestamp.toString() // Format as needed
    }

    override fun getItemCount(): Int {
        return notes.size
    }

    fun setNotes(newNotes: List<Note>) {
        notes = newNotes
        notifyDataSetChanged() // Notify the adapter to refresh the data
    }
}