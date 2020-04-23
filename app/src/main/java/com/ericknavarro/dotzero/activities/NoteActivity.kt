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
import android.widget.RadioButton
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
import kotlin.math.log
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

    private var note: Note? = null

    private val replyIntent = Intent()

    private lateinit var noteViewModel: NoteViewModel

    companion object {

        const val TITLE_REPLY = "title"
        const val BODY_REPLY = "body"
        const val COLOR_REPLY = "color"
        const val DARK_COLOR_REPLY = "darkColor"
        const val LAST_UPDATE_REPLY = "lastUpdate"
        const val ID_REPLY = "idNote"

    }

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

        lastUpdate.text = Date().toString()

        wasOpenedNote = intent.hasExtra(RecyclerAdapter.ID_NOTE)

        if(wasOpenedNote)
            setNoteContentById(intent.getLongExtra(RecyclerAdapter.ID_NOTE, 0))

        groupRadioButtonsColor.setOnCheckedChangeListener { group, checkedId ->

            setColorRadioButton(checkedId)
        }

        floatButton.setOnClickListener {
            saveOrDiscardChanges()
        }

    }

    private fun saveNoteContent(){

        if(wasOpenedNote) {

            note!!.title = titleNote.text.toString()
            note!!.body = bodyNote.text.toString()
            note!!.color = color
            note!!.darkColor = darkColor
            note!!.lastUpdated = Date().toString()

        }else {
            note = Note(
                0,
                titleNote.text.toString(),
                bodyNote.text.toString(),
                color,
                darkColor,
                Date().toString()
            )
        }


        replyIntent.putExtra(TITLE_REPLY, note?.title)
        replyIntent.putExtra(BODY_REPLY,  note?.body)
        replyIntent.putExtra(COLOR_REPLY, note?.color)
        replyIntent.putExtra(DARK_COLOR_REPLY, note?.darkColor)
        replyIntent.putExtra(LAST_UPDATE_REPLY, note?.lastUpdated)
        replyIntent.putExtra(ID_REPLY, note!!.id)

        setResult(Activity.RESULT_OK, replyIntent)

        finish()
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun setColorRadioButton(checkedId: Int) {

        when (checkedId) {

            R.id.radioWhite -> {
                darkColor = R.color.whiteColorDark
                color = R.color.grayColor
            }

            R.id.radioOrange -> {
                darkColor = R.color.orangeColorDark
                color = R.color.orangeColor
            }

            R.id.radioRed -> {
                darkColor = R.color.redColorDark
                color = R.color.redColor
            }

            R.id.radioBlue -> {
                darkColor = R.color.blueColorDark
                color = R.color.blueColor
            }
            R.id.radioGreen -> {
                darkColor = R.color.greenColorDark
                color = R.color.greenColor
            }
            else -> -1
        }

        setStatusBarAndActionBarColor(color, darkColor)
    }

    private fun setCheckedRadioButtonByColor(getColor: Int){

        when(getColor){

            R.color.grayColor -> {
                findViewById<RadioButton>(R.id.radioWhite).isChecked = true
                darkColor = R.color.whiteColorDark
                color = R.color.grayColor
            }
            R.color.orangeColor -> {
                findViewById<RadioButton>(R.id.radioOrange).isChecked = true
                darkColor = R.color.orangeColorDark
                color = R.color.orangeColor
            }

            R.color.redColor -> {
                findViewById<RadioButton>(R.id.radioRed).isChecked = true
                darkColor = R.color.redColorDark
                color = R.color.redColor
            }
            R.color.blueColor -> {
                findViewById<RadioButton>(R.id.radioBlue).isChecked = true
                darkColor = R.color.blueColorDark
                color = R.color.blueColor
            }
            R.color.greenColor -> {
                findViewById<RadioButton>(R.id.radioGreen).isChecked = true
                darkColor = R.color.greenColorDark
                color = R.color.greenColor
            }
        }

    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun setNoteContentById(idNote: Long){

        if( idNote > 0){

            note = noteViewModel.getNoteById(idNote)

            titleNote.setText(note!!.title)
            bodyNote.setText(note!!.body)
            lastUpdate.text = note!!.lastUpdated

            setStatusBarAndActionBarColor(note!!.color, note!!.darkColor)
            setCheckedRadioButtonByColor(note!!.color)

        }
    }

    private fun saveOrDiscardChanges(){
        if (!titleNote.text.isNotEmpty() && !bodyNote.text.isNotEmpty())
            finish()
        else
            saveNoteContent()
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun setStatusBarAndActionBarColor(color: Int, darkColor: Int){
        supportActionBar?.setBackgroundDrawable(ColorDrawable(resources.getColor(color)))
        window.statusBarColor = resources.getColor(darkColor)
    }

    /**
     *
     */
    override fun onSupportNavigateUp(): Boolean {

        saveOrDiscardChanges()

        return super.onSupportNavigateUp()
    }

    override fun onBackPressed() {
        super.onBackPressed()

        saveOrDiscardChanges()
    }
}
