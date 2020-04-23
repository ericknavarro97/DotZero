package com.ericknavarro.dotzero.ui.note

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.ericknavarro.dotzero.database.repositoires.NoteRepository
import com.ericknavarro.dotzero.database.manager.NoteRoomDatabase
import com.ericknavarro.dotzero.models.Note
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NoteViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: NoteRepository
    // Using LiveData and caching what getAlphabetizedWords returns has several benefits:
    // - We can put an observer on the data (instead of polling for changes) and only update the
    //   the UI when the data actually changes.
    // - Repository is completely separated from the UI through the ViewModel.
    val allNotes: LiveData<List<Note>>

    init {

        val notesDao = NoteRoomDatabase.getDatabase(application).noteDao()
        repository =
            NoteRepository(
                notesDao
            )
        allNotes = repository.allNotes
    }

    /**
     * Launching a new coroutine to insert the data in a non-blocking way
     */
    fun insertNote(note: Note) = viewModelScope.launch(Dispatchers.IO) {
        repository.insertNote(note)
    }

    fun getNoteById(id: Long): Note{
        return repository.getNoteById(id)
    }

    fun updateNote(note: Note): Int{
        return repository.updateNote(note)
    }

    fun trashNoteById(id: Long): Int{
        return repository.trashNoteById(id)
    }

    fun archiveNoteById(id: Long){
        repository.archiveNoteById(id)
    }

}
