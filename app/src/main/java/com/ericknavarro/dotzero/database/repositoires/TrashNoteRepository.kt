package com.ericknavarro.dotzero.database.repositoires

import androidx.lifecycle.LiveData
import com.ericknavarro.dotzero.dao.NoteDao
import com.ericknavarro.dotzero.models.Note

class TrashNoteRepository(private val dao: NoteDao){

    val getAllTrashNotes: LiveData<List<Note>> = dao.getAllTrashNotes()

    fun deleteAllNotes(){
        dao.deleteAllNotes()
    }

    fun deleteNote(note: Note){
        dao.deleteNote(note)
    }

}