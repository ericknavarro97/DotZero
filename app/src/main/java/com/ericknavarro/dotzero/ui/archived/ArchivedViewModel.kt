package com.ericknavarro.dotzero.ui.archived

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.ericknavarro.dotzero.databaseManager.NoteRepository
import com.ericknavarro.dotzero.databaseManager.NoteRoomDatabase
import com.ericknavarro.dotzero.models.Note

class ArchivedViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: NoteRepository

    var archivedNotes: LiveData<List<Note>>

    init {
        val notesDao = NoteRoomDatabase.getDatabase(application).noteDao()
        repository = NoteRepository(notesDao)
        archivedNotes = repository.archivedNotes
    }

}