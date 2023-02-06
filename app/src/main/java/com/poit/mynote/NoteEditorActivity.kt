package com.poit.mynote

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.lifecycle.ViewModelProvider
import com.poit.mynote.entity.Note
import com.poit.mynote.viewmodel.NoteFsViewModel
import com.poit.mynote.viewmodel.NoteViewModel
import java.util.Date

class NoteEditorActivity : AppCompatActivity() {
    lateinit var noteTitleEdit: EditText
    lateinit var noteDescriptionEdit: EditText
    lateinit var saveToDbButton: Button
    lateinit var saveToFsButton: Button
    lateinit var viewModel: NoteViewModel
    var noteId = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note_editor)

        noteTitleEdit = findViewById(R.id.noteTitleEdit)
        noteDescriptionEdit = findViewById(R.id.noteDescriptionEdit)
        saveToDbButton = findViewById(R.id.dbSaveBtn)
        saveToFsButton = findViewById(R.id.fsSaveBtn)
        val isChecked = intent.getBooleanExtra("typeIsChecked", false)

        viewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(application))
            .get(if (isChecked) NoteViewModel::class.java else NoteFsViewModel::class.java)

        noteTitleEdit.setText(intent.getStringExtra("noteTitle"))
        noteDescriptionEdit.setText(intent.getStringExtra("noteDescription"))
        noteId = intent.getIntExtra("noteId", -1)

        val operationType = intent.getStringExtra("noteType")

        saveToDbButton.setOnClickListener {
            val note = Note(
                noteTitleEdit.text.toString(),
                noteDescriptionEdit.text.toString(),
                Date().toString())

            when (operationType) {
                "Edit" -> viewModel.updateNote(note.apply { id = noteId })
                else -> viewModel.createNote(note)
            }

            val intent = Intent(applicationContext, MainActivity::class.java)
            intent.putExtra("switch", isChecked)
            startActivity(intent)
            this.finish()
        }
    }
}