package com.ericknavarro.dotzero.ui.archived

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.ericknavarro.dotzero.database.repositoires.ArchivedNoteRepository
import com.ericknavarro.dotzero.database.manager.NoteRoomDatabase
import com.ericknavarro.dotzero.models.Note

class ArchivedViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: ArchivedNoteRepository

    var archivedNotes: LiveData<List<Note>>

    init {
        val dao = NoteRoomDatabase.getDatabase(application).noteDao()
        repository =
            ArchivedNoteRepository(
                dao
            )
        archivedNotes = repository.allArchivedNotes
    }

    fun unarchiveNoteById(id: Long){
        repository.unarchiveNoteById(id)
    }

    fun trashNoteById(id: Long): Int{
        return repository.trashNoteById(id)
    }

}