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

    @Query("SELECT * FROM notes_table WHERE isArchived = 1 AND isDeleted = 0")
    fun getArchivedNotes(): LiveData<List<Note>>

    @Query("SELECT * FROM notes_table WHERE id = :id")
    fun getNoteById(id: Long): Note

    @Query("UPDATE notes_table SET isArchived = 1 WHERE id = :id")
    fun setArchivedNote(id: Long)

    @Update
    fun updateNote(note: Note): Int

}