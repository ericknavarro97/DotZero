package com.ericknavarro.dotzero.databaseManager

import androidx.lifecycle.LiveData
import com.ericknavarro.dotzero.dao.NoteDao
import com.ericknavarro.dotzero.models.Note

class NoteRepository(private val noteDao: NoteDao) {

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    val allNotes: LiveData<List<Note>> = noteDao.getNotes()
    val archivedNotes: LiveData<List<Note>> = noteDao.getArchivedNotes()

    suspend fun insertNote(note: Note) {
        noteDao.insertNote(note)
    }

    fun getNoteById(id: Long): Note {
        return noteDao.getNoteById(id)
    }

    fun updateNote(note: Note){
        noteDao.updateNote(note)
    }

}