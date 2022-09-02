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

    int editing = 0;
    int noteEditingNumber = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentFirstBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        DataBaseHelper dataBaseHelper = new DataBaseHelper(getContext());
        List<noteClass> allNotes = dataBaseHelper.getAllNotesFromLocalDB();

        noteAdapter noteArrayAdapter = new noteAdapter(getContext(),allNotes);
        binding.listView.setAdapter(noteArrayAdapter);

        binding.buttonDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DataBaseHelper dataBaseHelper = new DataBaseHelper(getContext());
                List<noteClass> allNotes = dataBaseHelper.getAllNotesFromLocalDB();

                noteAdapter noteArrayAdapter = new noteAdapter(getContext(),allNotes);
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



                }else{

                    if(binding.editTextNoteTitle.getText().toString().matches("")){
                        Toast.makeText(getContext(), "Title Required", Toast.LENGTH_SHORT).show();

                    }else{


                        ArrayList<String> titles = new ArrayList<>();
                        //List<noteClass> allNotes = dataBaseHelper.getAllNotesFromLocalDB();

                        if(allNotes.isEmpty()){
                            //Dont
                        }else{

                            for(int i = 1; i > allNotes.size(); i++){
                                titles.add(allNotes.get(i).getNoteTitle());
                            }

                        }



                        if(titles.contains(title)){

                            Toast.makeText(getContext(), "Title Must Be Unique", Toast.LENGTH_SHORT).show();

                        }else{

                            try {
                                newNote = new noteClass(noteText, title, date, tempImage);
                                Toast.makeText(getContext(), newNote.getNoteText(), Toast.LENGTH_SHORT).show();

                            }catch (Exception e){
                                Toast.makeText(getContext(), "Error creating note", Toast.LENGTH_SHORT).show();
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





            }
        });



        binding.buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.editTextNoteTitle.setText("");
                binding.editTextNoteText.setText("");
                editing = 0;
            }
        });



        binding.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
               List<noteClass> allNotes = dataBaseHelper.getAllNotesFromLocalDB();
               editing = 1;
               noteEditingNumber = i;

               String title = allNotes.get(i).getNoteTitle();
               String text = allNotes.get(i).getNoteText();

               binding.editTextNoteTitle.setText(title);
               binding.editTextNoteText.setText(text);

            }
        });


    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}