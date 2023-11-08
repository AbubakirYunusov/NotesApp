package com.example.notesapp


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.notesapp.databinding.ActivityMainBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val sharedPref: NotesDatabase by lazy {
        NotesDatabase(this)
    }

    private val adaptor: NotesAdapter by lazy {
        NotesAdapter(onClickDeleteBtn = {
            sharedPref.deleteNoteAtIndex(it)
            setupViewsAndAdapter()
        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupViewsAndAdapter()
        setupClickListener()
    }

    private fun setupViewsAndAdapter() {
        val listOfNotes = sharedPref.getAllNotes()
        if (listOfNotes.isNotEmpty()){
            binding.noteEmptyImg.visibility = View.GONE
            binding.recyclerView.visibility = View.VISIBLE
            adaptor.updateList(listOfNotes)
            binding.recyclerView.adapter = adaptor
        } else {
            binding.noteEmptyImg.visibility = View.VISIBLE
            binding.recyclerView.visibility = View.GONE
        }
    }

    private fun setupClickListener() = binding.apply {
        addNoteBtn.setOnClickListener{
            startActivity(Intent(this@MainActivity, AddNoteActivity::class.java))
        }
        deleteCard.setOnClickListener {
            showConfirmDeleteNoteDialog()
        }
    }

    private fun showConfirmDeleteNoteDialog () {
        val alertDialog = MaterialAlertDialogBuilder ( this)
        alertDialog.setMessage(resources.getString(R.string.text_do_all))
        alertDialog. setPositiveButton(resources.getString(R.string.text_yes)) {dialog, _ ->
            deleteAllSavedNotes()
            dialog.dismiss()
        }
        alertDialog.setNegativeButton(resources.getString(R.string.text_cancel)) {dialog, _ ->
            dialog. dismiss ()
        }
        alertDialog.create().show()
    }

    private fun deleteAllSavedNotes() {
        sharedPref.deleteAllNotes()
        adaptor.updateList(emptyList())
        binding.noteEmptyImg.visibility = View.VISIBLE
        binding.recyclerView.visibility = View.GONE
    }
}

