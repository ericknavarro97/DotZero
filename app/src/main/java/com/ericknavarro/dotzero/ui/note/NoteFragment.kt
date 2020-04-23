package com.ericknavarro.dotzero.ui.note

import android.content.Context
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
import com.ericknavarro.dotzero.ui.archived.ArchivedViewModel
import com.google.android.material.snackbar.Snackbar

class NoteFragment : Fragment() {

    private lateinit var noteViewModel: NoteViewModel
    private lateinit var archivedViewModel: ArchivedViewModel

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: RecyclerAdapter

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        noteViewModel = ViewModelProviders.of(this).get(NoteViewModel::class.java)
        archivedViewModel = ViewModelProviders.of(this).get(ArchivedViewModel::class.java)

        val root = inflater.inflate(R.layout.fragment_note, container, false)

        recyclerView = root.findViewById(R.id.recyclerNotes)
        adapter = RecyclerAdapter(root.context)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(root.context)

        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        noteViewModel.allNotes.observe(viewLifecycleOwner, Observer { notes ->
            notes?.let { adapter.setNotes(it) }
        })

        //adapter.addAt(0, Note("badfsdfah", "baaah", R.color.colorAccent, Date().toString(), 0,0))

        /**
         * <p>With left swipe remove (archive) the item from the list</p>
         */
        val swipeArchive = object : SwipeToArchiveCallback(requireContext()) {

            override fun onSwiped(p0: RecyclerView.ViewHolder, p1: Int) {

                val note = adapter.getItem(p0.adapterPosition)

                view?.let {
                    Snackbar.make(it, "Note Archived", Snackbar.LENGTH_LONG).setAction("Undo") {archivedViewModel.unarchiveNoteById(note.id) }.show()
                }

                noteViewModel.archiveNoteById(note.id)

                adapter.removeAt(p0.adapterPosition)
            }
        }

        val itemTouchHelper = ItemTouchHelper(swipeArchive)
        itemTouchHelper.attachToRecyclerView(recyclerView)

    }

}
