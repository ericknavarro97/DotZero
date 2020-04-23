package com.ericknavarro.dotzero.database.repositoires

import androidx.lifecycle.LiveData
import com.ericknavarro.dotzero.dao.NoteDao
import com.ericknavarro.dotzero.models.Note

class ArchivedNoteRepository(private val dao: NoteDao) {

    val allArchivedNotes: LiveData<List<Note>> = dao.getArchivedNotes()

    fun trashNoteById(id: Long): Int{
        return dao.trashNoteById(id)
    }

    fun unarchiveNoteById(id: Long){
        dao.unarchiveNoteById(id)
    }

}