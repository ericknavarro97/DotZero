package com.ericknavarro.dotzero.ui.trash

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.ericknavarro.dotzero.R
import com.ericknavarro.dotzero.adapters.RecyclerAdapter

class TrashFragment : Fragment() {

    private lateinit var archivedViewModel: TrashViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: RecyclerAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_archived, container, false)

        recyclerView = root.findViewById(R.id.recyclerNotes)
        adapter = RecyclerAdapter(root.context)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(root.context)

        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        archivedViewModel = ViewModelProviders.of(this).get(TrashViewModel::class.java)

        archivedViewModel.allTrashNotes.observe(viewLifecycleOwner, Observer {
                notes -> notes?.let { adapter.setNotes(it) }
        })

    }

}
