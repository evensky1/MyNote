package com.poit.mynote

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.Toast
import androidx.appcompat.widget.SwitchCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.poit.mynote.entity.Note
import com.poit.mynote.adapter.NoteClickDeleteInterface
import com.poit.mynote.adapter.NoteClickInterface
import com.poit.mynote.adapter.NoteRVAdapter
import com.poit.mynote.viewmodel.NoteViewModel

class MainActivity : AppCompatActivity(), NoteClickDeleteInterface, NoteClickInterface {
    private lateinit var notesRV: RecyclerView
    private lateinit var addFAB: FloatingActionButton
    private lateinit var viewModel: NoteViewModel
    private lateinit var switchType: SwitchCompat

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        notesRV = findViewById(R.id.rvNotes)
        addFAB = findViewById(R.id.сreateNoteButton)
        switchType = findViewById(R.id.storeTypeSwitch)
        switchType.isChecked = intent.getBooleanExtra("switch", false)

        notesRV.layoutManager = LinearLayoutManager(this)

        val noteRVAdapter = NoteRVAdapter(this, this, this)
        notesRV.adapter = noteRVAdapter

        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        ).get(NoteViewModel::class.java)

        viewModel.switchNoteSource(if (switchType.isChecked) "DB" else "FS", application)

        viewModel.allNotes.observe(this) { list ->
            list?.let {
                noteRVAdapter.updateList(it)
            }
        }

        addFAB.setOnClickListener {
            val intent = Intent(this@MainActivity, NoteEditorActivity::class.java)
            intent.putExtra("typeIsChecked", switchType.isChecked)
            startActivity(intent)
            this.finish()
        }

        switchType.setOnCheckedChangeListener { _, isChecked ->
            switchType.text = if (isChecked) "DB" else "FS"
            viewModel.switchNoteSource(if (isChecked) "DB" else "FS", application)
            println(viewModel.allNotes.value)
        }
    }

    override fun onDeleteIconClick(note: Note) {
        viewModel.deleteNote(note)
        Toast.makeText(this, "${note.title} Successfully deleted", Toast.LENGTH_LONG).show()
    }

    override fun onNoteClick(note: Note) {
        val intent = Intent(this@MainActivity, NoteEditorActivity::class.java)
        intent.putExtra("noteType", "Edit")
        intent.putExtra("noteTitle", note.title)
        intent.putExtra("noteDescription", note.description)
        intent.putExtra("noteId", note.id)
        intent.putExtra("typeIsChecked", switchType.isChecked)
        startActivity(intent)
        this.finish()
    }
}