package com.example.introductorylab.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.introductorylab.R
import com.example.introductorylab.view.NoteCatalogRecycleAdapter

private const val ARG_ARCHIVED = "archived"
private const val ARG_NOTEBOOK_ID = "notebook_id"

class NotesFragment : Fragment() {
    private var archived: Boolean = false
    private var notebookId: String = ""

    private var noteCatalogView: RecyclerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            archived = it.getBoolean(ARG_ARCHIVED)
            notebookId = it.getString(ARG_NOTEBOOK_ID) ?: "__missing__"
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_notes, container, false)

        // Setup note list
        noteCatalogView = view?.findViewById(R.id.noteList)
        noteCatalogView?.layoutManager = GridLayoutManager(requireActivity(), 2)
        noteCatalogView?.adapter = NoteCatalogRecycleAdapter(requireActivity(), notebookId, archived)

        return view
    }

    override fun onResume() {
        super.onResume()
        noteCatalogView?.adapter?.notifyDataSetChanged()
    }

    companion object {
        @JvmStatic
        fun newInstance(notebookId: String, archived: Boolean) =
                NotesFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_NOTEBOOK_ID, notebookId)
                        putBoolean(ARG_ARCHIVED, archived)
                    }
                }
    }
}