package com.ericknavarro.dotzero.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.ericknavarro.dotzero.models.Note

@Dao
interface ArchiveDao {

    @Query("SELECT * FROM notes_table WHERE isArchived = 1")
    fun getArchivedNotes(): LiveData<List<Note>>

    @Query("UPDATE notes_table SET isArchived = 1 WHERE id = :id")
    fun archiveNoteById(id: Long): Int

    @Query("UPDATE notes_table SET isArchived = 0 WHERE id = :id")
    fun unarchiveNoteById(id: Long)

    @Query("UPDATE notes_table SET isArchived = 0, isDeleted = 1 WHERE id = :id")
    fun trashNoteById(id: Long): Int

}