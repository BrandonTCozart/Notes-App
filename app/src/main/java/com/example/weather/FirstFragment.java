package com.example.weather;

import android.media.Image;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.weather.databinding.FragmentFirstBinding;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class FirstFragment extends Fragment {

    private FragmentFirstBinding binding;
    noteClass newNote;
    String title;
    String noteText;
    String date;
    Image image; // need to point to image
    String tempImage;

    List<noteClass> allNotes;
    noteAdapter noteArrayAdapter;
    DataBaseHelper dataBaseHelper;

    int editing = 0;
    int noteEditingNumber = 0;

    int joeyMode = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentFirstBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        dataBaseHelper = new DataBaseHelper(getContext());
        allNotes = dataBaseHelper.getAllNotesFromLocalDB();

        noteArrayAdapter = new noteAdapter(getContext(),allNotes);
        binding.listView.setAdapter(noteArrayAdapter);

        binding.buttonDelete.setVisibility(View.INVISIBLE);

        binding.buttonDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DataBaseHelper dataBaseHelper = new DataBaseHelper(getContext());
                allNotes = dataBaseHelper.getAllNotesFromLocalDB();

                noteArrayAdapter = new noteAdapter(getContext(),allNotes);
                binding.listView.setAdapter(noteArrayAdapter);

                title = binding.editTextNoteTitle.getText().toString();
                noteText = binding.editTextNoteText.getText().toString();
                date = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
                tempImage = "";

                if(editing == 1){
                    String oldTitle = allNotes.get(noteEditingNumber).getNoteTitle();
                    allNotes.get(noteEditingNumber).setNoteTitle(title);
                    allNotes.get(noteEditingNumber).setNoteText(noteText);
                    allNotes.get(noteEditingNumber).setNoteCreationDate(date);


                    dataBaseHelper.changeNoteInfo(title, noteText, oldTitle);


                    binding.listView.setAdapter(noteArrayAdapter);

                    binding.editTextNoteText.setText("");
                    binding.editTextNoteTitle.setText("");

                    Toast.makeText(getContext(), "Edit Complete", Toast.LENGTH_SHORT).show();

                    editing = 0;

                    binding.buttonDelete.setVisibility(View.INVISIBLE);



                }else{

                    if(binding.editTextNoteTitle.getText().toString().matches("")){
                        Toast.makeText(getContext(), "Title Required", Toast.LENGTH_SHORT).show();

                    }else{

                        // Make sure the Name is different //

                        if(dataBaseHelper.getTitles().contains(title)){

                            Toast.makeText(getContext(), "Title Must Be Unique", Toast.LENGTH_SHORT).show();

                            // Make sure the Name is different //

                        }else{

                            try {
                                newNote = new noteClass(noteText, title, date, tempImage);

                            }catch (Exception e){
                                newNote = new noteClass(noteText, title, date, tempImage);

                            }

                            dataBaseHelper.addOne(newNote);

                            binding.listView.setAdapter(noteArrayAdapter);

                            binding.editTextNoteText.setText("");
                            binding.editTextNoteTitle.setText("");

                        }

                    }
                }

                allNotes = dataBaseHelper.getAllNotesFromLocalDB();
                noteArrayAdapter = new noteAdapter(getContext(),allNotes);
                binding.listView.setAdapter(noteArrayAdapter);

                joeyMode = 0;

            }
        });



        binding.buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.editTextNoteTitle.setText("");
                binding.editTextNoteText.setText("");
                editing = 0;

                if(joeyMode == 15){
                    Toast.makeText(getContext(), "Whats Good Cuz", Toast.LENGTH_SHORT).show();
                    joeyMode = 0;
                }

                joeyMode = joeyMode + 1;

                binding.buttonDelete.setVisibility(View.INVISIBLE);
            }
        });



        binding.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
               allNotes = dataBaseHelper.getAllNotesFromLocalDB();
               editing = 1;
               noteEditingNumber = i;

               String title = allNotes.get(i).getNoteTitle();
               String text = allNotes.get(i).getNoteText();

               binding.editTextNoteTitle.setText(title);
               binding.editTextNoteText.setText(text);

                binding.buttonDelete.setVisibility(View.VISIBLE);

                joeyMode = 0;

            }
        });

        binding.buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dataBaseHelper.deleteNote(binding.editTextNoteTitle.getText().toString());

                allNotes = dataBaseHelper.getAllNotesFromLocalDB();

                noteArrayAdapter = new noteAdapter(getContext(),allNotes);
                binding.listView.setAdapter(noteArrayAdapter);

                binding.editTextNoteText.setText("");
                binding.editTextNoteTitle.setText("");

                editing = 0;

                binding.buttonDelete.setVisibility(View.INVISIBLE);
            }
        });


    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}