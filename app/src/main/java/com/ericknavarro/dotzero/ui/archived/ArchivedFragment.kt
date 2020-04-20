package com.ericknavarro.dotzero.ui.archived

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ericknavarro.dotzero.R
import com.ericknavarro.dotzero.adapters.RecyclerAdapter

class ArchivedFragment : Fragment() {

    private lateinit var archivedViewModel: ArchivedViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        archivedViewModel =
                ViewModelProviders.of(this).get(ArchivedViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_archived, container, false)

        val recyclerView = root.findViewById<RecyclerView>(R.id.recyclerNotes)
        val adapter = RecyclerAdapter(root.context)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(root.context)

        archivedViewModel.archivedNotes.observe(viewLifecycleOwner, Observer {
            notes -> notes?.let { adapter.setNotes(it) }
        })

        return root
    }
}
