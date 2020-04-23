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

    @Query("SELECT * FROM notes_table WHERE isArchived = 1")
    fun getArchivedNotes(): LiveData<List<Note>>

    @Query("SELECT * FROM notes_table WHERE id = :id")
    fun getNoteById(id: Long): Note

    @Query("UPDATE notes_table SET isArchived = 1 WHERE id = :id")
    fun achiveNoteById(id: Long): Int

    @Query("UPDATE notes_table SET isArchived = 0 WHERE id = :id")
    fun unarchiveNoteById(id: Long)

    @Update
    fun updateNote(note: Note): Int

    @Query("UPDATE notes_table SET isArchived = 0, isDeleted = 1 WHERE id = :id")
    fun trashNoteById(id: Long): Int

    @Query("SELECT * FROM notes_table WHERE isDeleted = 1")
    fun getAllTrashNotes(): LiveData<List<Note>>

    @Query("DELETE FROM notes_table")
    fun deleteAllNotes()

    @Delete
    fun deleteNote(note: Note): Int

}