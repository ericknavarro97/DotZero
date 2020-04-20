package com.ericknavarro.dotzero.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ericknavarro.dotzero.R

@Entity(tableName = "notes_table")
data class Note(
    var title: String,
    var body: String,
    var color: Int = R.color.grayColor,
    var darkColor: Int = R.color.whiteColorDark,
    var lastUpdated: String,
    var isArchived: Byte = 0,
    var isDeleted: Byte = 0
)

{

    @PrimaryKey(autoGenerate = true)
    var id: Long = 0

}

