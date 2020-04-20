package com.ericknavarro.dotzero.ui.note

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.ItemTouchHelper
import com.ericknavarro.dotzero.R
import com.ericknavarro.dotzero.SwipeToArchiveCallback
import com.ericknavarro.dotzero.adapters.RecyclerAdapter
import com.google.android.material.snackbar.Snackbar

class NoteFragment : Fragment() {

    private lateinit var noteViewModel: NoteViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        noteViewModel = ViewModelProviders.of(this).get(NoteViewModel::class.java)

        val root = inflater.inflate(R.layout.fragment_note, container, false)

        val recyclerView = root.findViewById<RecyclerView>(R.id.recyclerNotes)
        val adapter = RecyclerAdapter(root.context)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(root.context)

        noteViewModel.allNotes.observe(viewLifecycleOwner, Observer { notes ->
            notes?.let { adapter.setNotes(it) }
        })

        //adapter.addAt(0, Note("badfsdfah", "baaah", R.color.colorAccent, Date().toString(), 0,0))

        /**
         * <p>With left swipe remove (archive) the item from the list</p>
         */
        val swipeArchive = object : SwipeToArchiveCallback(root.context) {

            override fun onSwiped(p0: RecyclerView.ViewHolder, p1: Int) {

                Snackbar.make(root, "Note Archived", Snackbar.LENGTH_LONG).setAction("Undo") {
                    Toast.makeText(root.context, "" + p0.adapterPosition + "", Toast.LENGTH_LONG).show()
                    //adapter.addAt(p0.oldPosition, )
                }.show()

                Log.e("bah", ""+ p0.adapterPosition + ", " + adapter.getItemId(p0.adapterPosition))

                adapter.removeAt(p0.adapterPosition)
            }
        }

        val itemTouchHelper = ItemTouchHelper(swipeArchive)
        itemTouchHelper.attachToRecyclerView(recyclerView)

        return root
    }


}
