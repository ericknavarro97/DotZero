package com.ericknavarro.dotzero.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.ericknavarro.dotzero.models.Note

@Dao
interface NoteDao {

    @Query("SELECT * FROM notes_table WHERE isArchived = 0 AND isDeleted = 0")
    fun getNotes(): LiveData<List<Note>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertNote(note: Note)

    @Query("SELECT * FROM notes_table WHERE id = :id")
    fun getNoteById(id: Long): Note

    @Update
    fun updateNote(note: Note): Int

    @Delete
    fun deleteNote(note: Note): Int

    @Query("DELETE FROM notes_table")
    fun deleteAllNotes()

    @Query("SELECT * FROM notes_table WHERE isDeleted = 1")
    fun getAllTrashNotes(): LiveData<List<Note>>

}