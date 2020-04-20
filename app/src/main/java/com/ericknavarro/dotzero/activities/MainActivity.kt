package com.ericknavarro.dotzero.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProviders
import com.ericknavarro.dotzero.R
import com.ericknavarro.dotzero.models.Note
import com.ericknavarro.dotzero.ui.note.NoteViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration

    private val newNoteActivityRequestCode = 1
    private lateinit var noteViewModel: NoteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val fab: FloatingActionButton = findViewById(R.id.fab)
        noteViewModel = ViewModelProviders.of(this).get(NoteViewModel::class.java)

        fab.setOnClickListener {
            val noteIntent = Intent(this@MainActivity, NoteActivity::class.java)
            startActivityForResult(noteIntent, newNoteActivityRequestCode)
        }

        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(setOf(
            R.id.nav_home,
            R.id.nav_gallery,
            R.id.nav_slideshow,
            R.id.nav_example
        ), drawerLayout)
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == newNoteActivityRequestCode && resultCode == Activity.RESULT_OK) {
            data?.let { data ->

                Log.e("id act:", "sdf")

                val note = Note(data.getStringExtra(NoteActivity.TITLE_REPLY), data.getStringExtra(NoteActivity.BODY_REPLY), data.getIntExtra(NoteActivity.COLOR_REPLY, R.color.grayColor),
                    data.getIntExtra(NoteActivity.DARK_COLOR_REPLY, R.color.whiteColorDark), data.getStringExtra(NoteActivity.LAST_UPDATE_REPLY))

                if(data.getBooleanExtra(NoteActivity.UPDATE_REPLY, false))
                    noteViewModel.updateNote(note)
                else
                    noteViewModel.insertNote(note)

            }
        } else {

            Toast.makeText(
                applicationContext,"Empty Note Discarded",
                Toast.LENGTH_LONG
            ).show()
        }

    }



    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}
