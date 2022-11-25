package com.example.weather.Adapter

import android.content.Context
import com.example.weather.Classes.noteClass
import android.widget.BaseAdapter
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import com.example.weather.R
import android.widget.TextView

class noteAdapter(var mactivity: Context, var notes: List<noteClass>) : BaseAdapter() {

    override fun getCount(): Int {
        return notes.size
    }

    override fun getItem(i: Int): Any? {
        return if (notes.isEmpty()) {
            null
        } else {
            notes[i]
        }
    }

    override fun getItemId(i: Int): Long {
        return 0
    }

    override fun getView(i: Int, view: View?, viewGroup: ViewGroup): View {
        val v: View
        val inflater = mactivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        v = inflater.inflate(R.layout.adapter_view_layout, null, false)

        val noteName = v.findViewById<TextView>(R.id.textViewNoteName)
        val noteDate = v.findViewById<TextView>(R.id.textViewNoteDate)
        val note = getItem(i) as noteClass

        if (notes.isEmpty()) {
            noteName.text = "Empty"
        } else {
            noteName.text = note.noteTitle
        }
        if (notes.isEmpty()) {
            noteDate.text = "Empty"
        } else {
            noteDate.text = note.noteCreationDate
        }
        return v
    }
}