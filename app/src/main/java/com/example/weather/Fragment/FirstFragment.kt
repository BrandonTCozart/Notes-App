package com.example.weather.Fragment

import android.media.Image
import com.example.weather.Classes.noteClass
import com.example.weather.Adapter.noteAdapter
import com.example.weather.DB.DataBaseHelper
import android.view.LayoutInflater
import android.view.ViewGroup
import android.os.Bundle
import android.view.View
import android.widget.Toast
import android.widget.AdapterView.OnItemClickListener
import androidx.fragment.app.Fragment
import com.example.weather.databinding.FragmentFirstBinding
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

class FirstFragment : Fragment() {
    private var binding: FragmentFirstBinding? = null
    lateinit var newNote: noteClass
    lateinit var title: String
    lateinit var noteText: String
    lateinit var date: String
    var image // need to point to image
            : Image? = null
    var tempImage: String? = null
    var allNotes: List<noteClass>? = null
    var noteArrayAdapter: noteAdapter? = null
    var dataBaseHelper: DataBaseHelper? = null
    var editing = 0
    var noteEditingNumber = 0


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        binding = FragmentFirstBinding.inflate(inflater, container, false)


        return binding!!.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dataBaseHelper = DataBaseHelper(context)
        allNotes = dataBaseHelper!!.allNotesFromLocalDB.asReversed()
        noteArrayAdapter = noteAdapter(requireContext(), allNotes!!)
        binding!!.listView.adapter = noteArrayAdapter
        binding!!.buttonDelete.visibility = View.INVISIBLE

        binding!!.buttonDone.setOnClickListener {
            val dataBaseHelper = DataBaseHelper(context)
            allNotes = dataBaseHelper.allNotesFromLocalDB.asReversed()
            noteArrayAdapter = noteAdapter(requireContext(), allNotes!!)
            binding!!.listView.adapter = noteArrayAdapter
            title = binding!!.editTextNoteTitle.text.toString()
            noteText = binding!!.editTextNoteText.text.toString()
            date = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(Date())
            tempImage = ""
            if (editing == 1) {
                val oldTitle = allNotes!!.get(noteEditingNumber).noteTitle
                allNotes!!.get(noteEditingNumber).noteTitle = title as String
                allNotes!!.get(noteEditingNumber).noteText = noteText
                allNotes!!.get(noteEditingNumber).noteCreationDate = date as String
                dataBaseHelper.changeNoteInfo(title!!, noteText!!, oldTitle)
                binding!!.listView.adapter = noteArrayAdapter
                binding!!.editTextNoteText.setText("")
                binding!!.editTextNoteTitle.setText("")
                Toast.makeText(context, "Edit Complete", Toast.LENGTH_SHORT).show()
                binding!!.buttonDone.setText("Done")
                editing = 0
                binding!!.buttonDelete.visibility = View.INVISIBLE
            } else {
                if (binding!!.editTextNoteTitle.text.toString().isEmpty()) {
                    Toast.makeText(context, "Title Required", Toast.LENGTH_SHORT).show()
                } else {

                    // Make sure the Name is different //
                    if (dataBaseHelper.titles.contains(title)) {
                        Toast.makeText(context, "Title Must Be Unique", Toast.LENGTH_SHORT).show()

                        // Make sure the Name is different //
                    } else {
                        newNote = try {
                            noteClass(noteText, title!!, date, tempImage)
                        } catch (e: Exception) {
                            noteClass(noteText, title!!, date, tempImage)
                        }
                        dataBaseHelper.addOne(newNote!!)
                        binding!!.listView.adapter = noteArrayAdapter
                        binding!!.editTextNoteText.setText("")
                        binding!!.editTextNoteTitle.setText("")
                    }
                }
            }
            allNotes = dataBaseHelper.allNotesFromLocalDB.asReversed()
            noteArrayAdapter = noteAdapter(requireContext(), allNotes!!)
            binding!!.listView.adapter = noteArrayAdapter
        }

        binding!!.buttonCancel.setOnClickListener {
            binding!!.editTextNoteTitle.setText("")
            binding!!.editTextNoteText.setText("")
            editing = 0
            binding!!.buttonDelete.visibility = View.INVISIBLE
        }

        binding!!.listView.onItemClickListener = OnItemClickListener { adapterView, view, i, l ->
            allNotes = dataBaseHelper!!.allNotesFromLocalDB.asReversed()
            editing = 1
            noteEditingNumber = i
            val title = allNotes!!.get(i).noteTitle
            val text = allNotes!!.get(i).noteText
            binding!!.editTextNoteTitle.setText(title)
            binding!!.editTextNoteText.setText(text)
            binding!!.buttonDelete.visibility = View.VISIBLE
            binding!!.buttonDone.text == "Complete Edit"
        }

        binding!!.buttonDelete.setOnClickListener {
            dataBaseHelper!!.deleteNote(binding!!.editTextNoteTitle.text.toString())
            allNotes = dataBaseHelper!!.allNotesFromLocalDB.asReversed()
            noteArrayAdapter = noteAdapter(requireContext(), allNotes!!)
            binding!!.listView.adapter = noteArrayAdapter
            binding!!.editTextNoteText.setText("")
            binding!!.editTextNoteTitle.setText("")
            editing = 0
            binding!!.buttonDelete.visibility = View.INVISIBLE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}