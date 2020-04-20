package com.ericknavarro.dotzero.activities

import android.app.Activity
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.BoringLayout
import android.text.TextUtils
import android.util.Log
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProviders
import com.ericknavarro.dotzero.R
import com.ericknavarro.dotzero.adapters.RecyclerAdapter
import com.ericknavarro.dotzero.models.Note
import com.ericknavarro.dotzero.ui.note.NoteViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.util.*
import kotlin.properties.Delegates

class NoteActivity : AppCompatActivity() {

    private lateinit var titleNote: EditText
    private lateinit var bodyNote: EditText
    private lateinit var floatButton: FloatingActionButton
    private lateinit var lastUpdate: TextView
    private lateinit var groupRadioButtonsColor: RadioGroup

    private var color: Int = R.color.grayColor
    private var darkColor: Int = R.color.whiteColorDark

    private var wasOpenedNote: Boolean = false
    private var oldTitle: String = ""
    private var oldBody: String = ""

    private lateinit var noteViewModel: NoteViewModel

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note)

        titleNote = findViewById(R.id.titleNote)
        bodyNote = findViewById(R.id.bodyNote)
        floatButton = findViewById(R.id.addNoteBtn)
        groupRadioButtonsColor = findViewById(R.id.radioGroupColors)
        lastUpdate = findViewById(R.id.lastUpdateText)

        noteViewModel = ViewModelProviders.of(this).get(NoteViewModel::class.java)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.title = "Note"

        wasOpenedNote = intent.hasExtra(RecyclerAdapter.ID_NOTE)

        if(wasOpenedNote)
            setNoteContentById(intent.getLongExtra(RecyclerAdapter.ID_NOTE, 0))

        Log.e("id antes", "" + wasOpenedNote)

        groupRadioButtonsColor.setOnCheckedChangeListener { group, checkedId ->

            setColorRadioButton(checkedId)
        }

        floatButton.setOnClickListener {

            if (TextUtils.isEmpty(titleNote.text) && TextUtils.isEmpty(bodyNote.text))
                onBackPressed()
            else
                saveNoteContent()
        }

    }

    private fun saveNoteContent(){

        val replyIntent = Intent()

        val title = titleNote.text.toString()
        val body = bodyNote.text.toString()
        val lastUpdate = Date().toString()

        replyIntent.putExtra(TITLE_REPLY, title)
        replyIntent.putExtra(BODY_REPLY, body)
        replyIntent.putExtra(COLOR_REPLY, color)
        replyIntent.putExtra(DARK_COLOR_REPLY, darkColor)
        replyIntent.putExtra(LAST_UPDATE_REPLY, lastUpdate)
        replyIntent.putExtra(UPDATE_REPLY, wasOpenedNote)

        Log.e("id guardo", "" + wasOpenedNote)

        setResult(Activity.RESULT_OK, replyIntent)

        finish()
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun setColorRadioButton(checkedId: Int) {

        when (checkedId) {

            R.id.radioWhite -> {
                color = R.color.grayColor
                darkColor = R.color.whiteColorDark
            }

            R.id.radioOrange -> {
                color = R.color.orangeColor
                darkColor = R.color.orangeColorDark
            }

            R.id.radioRed -> {
                color = R.color.redColor
                darkColor = R.color.redColorDark
            }

            R.id.radioBlue -> {
                color = R.color.blueColor
                darkColor = R.color.blueColorDark
            }
            R.id.radioGreen -> {
                color = R.color.greenColor
                darkColor = R.color.greenColorDark
            }
            else -> -1
        }

        setStatusBarAndActionBarColor(color, darkColor)
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun setNoteContentById(idNote: Long){

        if( idNote > 0){

            val note: Note = noteViewModel.getNoteById(idNote)

            titleNote.setText(note.title)
            bodyNote.setText(note.body)
            lastUpdate.text = note.lastUpdated

            oldTitle = note.title
            oldBody = note.body

            setStatusBarAndActionBarColor(note.color, note.darkColor)

        }
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun setStatusBarAndActionBarColor(color: Int, darkColor: Int){
        supportActionBar?.setBackgroundDrawable(ColorDrawable(resources.getColor(color)))
        window.statusBarColor = resources.getColor(darkColor)
        //floatButton.setBackgroundResource(darkColor)
    }

    companion object {

        const val TITLE_REPLY = "title"
        const val BODY_REPLY = "body"
        const val COLOR_REPLY = "color"
        const val DARK_COLOR_REPLY = "darkColor"
        const val LAST_UPDATE_REPLY = "lastUpdate"
        const val UPDATE_REPLY = "hasId"

    }


    override fun onSupportNavigateUp(): Boolean {

        onBackPressed()

        return super.onSupportNavigateUp()
    }

    override fun onBackPressed() {
        super.onBackPressed()

        //setResult(Activity.RESULT_CANCELED, replyIntent)
        finish()
    }
}
