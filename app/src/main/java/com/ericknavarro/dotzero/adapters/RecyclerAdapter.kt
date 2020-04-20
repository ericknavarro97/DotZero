package com.ericknavarro.dotzero.adapters

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat.getColor
import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity

import com.ericknavarro.dotzero.R
import com.ericknavarro.dotzero.activities.NoteActivity
import com.ericknavarro.dotzero.databaseManager.NoteRepository
import com.ericknavarro.dotzero.models.Note
import java.util.*

class RecyclerAdapter internal constructor(
    context: Context
) : RecyclerView.Adapter<RecyclerAdapter.NoteViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var notes = emptyList<Note>().toMutableList()// Cached copy of words
    private val context: Context = context

    inner class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val context: Context = itemView.context
        val titleNote: TextView = itemView.findViewById(R.id.titleNote)
        val colorNote: LinearLayout = itemView.findViewById(R.id.LinearLayoutColor)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val itemView = inflater.inflate(R.layout.adapter_note_item, parent, false)
        return NoteViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val current = notes[position]

        holder.titleNote.text = current.title
        holder.colorNote.setBackgroundColor(ContextCompat.getColor(holder.context, current.color))

        holder.titleNote.setOnClickListener {
            val noteIntent = Intent(holder.context, NoteActivity::class.java)
            noteIntent.putExtra(ID_NOTE, current.id)
            context.startActivity(noteIntent)
        }

    }

    companion object{
        const val ID_NOTE = "idNote"
    }

    fun removeAt(position: Int) {
        notes.removeAt(position)
        notifyItemRemoved(position)
    }

    fun addAt(position: Int, note: Note){
        notes.add(position, note)
        notifyItemInserted(position)
    }

    internal fun setNotes(note: List<Note>) {
        this.notes = note.toMutableList()
        notifyDataSetChanged()
    }

    override fun getItemCount() = notes.size
}