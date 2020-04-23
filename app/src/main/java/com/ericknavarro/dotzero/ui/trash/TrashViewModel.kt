package com.ericknavarro.dotzero.ui.trash

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.ericknavarro.dotzero.database.manager.NoteRoomDatabase
import com.ericknavarro.dotzero.database.repositoires.TrashNoteRepository
import com.ericknavarro.dotzero.models.Note

class TrashViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: TrashNoteRepository
    val allTrashNotes: LiveData<List<Note>>

    init {
        val dao = NoteRoomDatabase.getDatabase(application).noteDao()
        repository =
            TrashNoteRepository(
                dao
            )
        allTrashNotes = repository.getAllTrashNotes
    }

    fun deleteNote(note: Note){
        repository.deleteNote(note)
    }

    fun deleteAllNotes(){
        repository.deleteAllNotes()
    }

}
