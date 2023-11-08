package com.example.notesapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.notesapp.databinding.ActivityAddNoteBinding
import com.google.android.material.snackbar.Snackbar

class AddNoteActivity : AppCompatActivity() {

    private val binding:ActivityAddNoteBinding by lazy {
        ActivityAddNoteBinding.inflate(layoutInflater)
    }

    private val sharedPref: NotesDatabase by lazy {
        NotesDatabase(this)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.saveCard.setOnClickListener{
            saveNotes()
        }
        binding.backCard.setOnClickListener {
            finish()
        }
    }

    private fun  saveNotes() = binding.apply {
        if (titleEt.text?.isNotEmpty() == true && overviewEt.text?.isNotEmpty() == true){
            sharedPref.saveNote(
                NotesModel(
                    noteTitel = titleEt.text.toString(),
                    noteDescription = overviewEt.text.toString()
                )
            )
            startActivity(Intent ( this@AddNoteActivity, MainActivity::class.java))
        } else showToastMessage (resources.getString(R.string.text_not))
    }

    private fun showToastMessage(massage: String) {
        Snackbar.make(
            binding.root,
            massage,
            Snackbar.LENGTH_SHORT,
        ).show()
    }
}