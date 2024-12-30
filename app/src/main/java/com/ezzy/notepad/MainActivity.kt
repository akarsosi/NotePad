package com.ezzy.notepad

import NoteRepository
import NoteViewModel
import android.os.Bundle
import android.view.LayoutInflater

import android.widget.EditText
import android.widget.Toast

import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {
    private lateinit var noteViewModel: NoteViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: NoteAdapter // Ensure you have this adapter class

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize ViewModel
        val noteDao = DatabaseProvider.getDatabase(this).noteDao()
        val repository = NoteRepository(noteDao)
        noteViewModel = NoteViewModel(repository)

        // Set up RecyclerView
        recyclerView = findViewById(R.id.recyclerView)
        adapter = NoteAdapter() // Ensure you have this adapter class
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Observe LiveData from ViewModel
        noteViewModel.allNotes.observe(this, Observer { notes ->
            // Update the cached copy of the notes in the adapter
            notes?.let { adapter.setNotes(it) }
        })

        // Set up Floating Action Button
        val fab: FloatingActionButton = findViewById(R.id.fab)
        fab.setOnClickListener {
            showAddNoteDialog()
        }
    }

    private fun showAddNoteDialog() {
        // Inflate the dialog layout
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_add_note, null)
        val titleEditText = dialogView.findViewById<EditText>(R.id.editTextTitle)
        val contentEditText = dialogView.findViewById<EditText>(R.id.editTextContent)

        // Create and show the dialog
        AlertDialog.Builder(this)
            .setTitle("Add Note")
            .setView(dialogView)
            .setPositiveButton("Add") { _, _ ->
                val title = titleEditText.text.toString()
                val content = contentEditText.text.toString()
                if (title.isNotEmpty() && content.isNotEmpty()) {
                    val newNote = Note(
                        title = title,
                        content = content,
                        timestamp = System.currentTimeMillis()
                    )
                    noteViewModel.insert(newNote)
                    Toast.makeText(this, "Note added", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Please enter title and content", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }
}