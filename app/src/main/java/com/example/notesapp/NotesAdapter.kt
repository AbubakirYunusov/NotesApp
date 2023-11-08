package com.example.notesapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.notesapp.databinding.NotesItemBinding

class NotesAdapter (
    private val onClickDeleteBtn: (index: Int) -> Unit
) : RecyclerView.Adapter<NotesAdapter.NoteViewHolder>() {

    private val noteList = mutableListOf<NotesModel>()

    fun updateList(noteList: List<NotesModel>) {
        this.noteList.clear()
        this.noteList.addAll(noteList)
        notifyDataSetChanged()
    }

    inner class NoteViewHolder(
        private val binding: NotesItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(notesModel: NotesModel) {
            noteList.indexOf(notesModel)
            binding.tvNoteTitle.text = notesModel.noteTitel
            binding.tvNoteOverview.text = notesModel.noteDescription
            binding.deleteButt.setOnClickListener {
                onClickDeleteBtn.invoke(
                    noteList.indexOf(notesModel)
                )
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): NoteViewHolder {
        val binding = NotesItemBinding.bind(
            LayoutInflater
                .from(parent.context)
                .inflate(
                    R.layout.notes_item, parent, false
                )
        )
        return NoteViewHolder(binding)
    }

    override fun getItemCount(): Int = noteList.size

    override fun onBindViewHolder(
        holder: NoteViewHolder,
        position: Int,
    ) {
        holder.bind(noteList[position])
    }
}
