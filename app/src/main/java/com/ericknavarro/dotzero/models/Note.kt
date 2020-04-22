package com.ericknavarro.dotzero.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ericknavarro.dotzero.R

@Entity(tableName = "notes_table")
data class Note(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,
    var title: String,
    var body: String,
    var color: Int = R.color.grayColor,
    var darkColor: Int = R.color.whiteColorDark,
    var lastUpdated: String,
    var isArchived: Byte = 0,
    var isDeleted: Byte = 0
)

{
    override fun toString(): String {
        return "Note(title='$title', body='$body', color=$color, darkColor=$darkColor, lastUpdated='$lastUpdated', isArchived=$isArchived, isDeleted=$isDeleted, id=$id)"
    }


}



