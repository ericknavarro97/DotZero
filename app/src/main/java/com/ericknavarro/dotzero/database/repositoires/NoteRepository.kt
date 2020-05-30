package com.ericknavarro.dotzero.database.repositoires

import androidx.lifecycle.LiveData
import com.ericknavarro.dotzero.dao.NoteDao
import com.ericknavarro.dotzero.models.Note

class NoteRepository(private val dao: NoteDao) {

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    val allNotes: LiveData<List<Note>> = dao.getNotes()

    suspend fun insertNote(note: Note) {
        dao.insertNote(note)
    }

    fun getNoteById(id: Long): Note {
        return dao.getNoteById(id)
    }

    fun updateNote(note: Note): Int{
        return dao.updateNote(note)
    }

    /*fun archiveNoteById(id: Long): Int{
        return dao.achiveNoteById(id)
    }

    fun trashNoteById(id: Long): Int{
        return dao.trashNoteById(id)
    }*/

}