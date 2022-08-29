package com.example.weather;

import android.media.Image;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.weather.databinding.FragmentFirstBinding;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentFirstBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //when the screen comes up the list of notes auto comes up //
        DataBaseHelper dataBaseHelper = new DataBaseHelper(getContext());
        List<noteClass> allNotes = dataBaseHelper.getAllNotesFromLocalDB();

        ArrayAdapter noteArrayAdapter = new ArrayAdapter<noteClass>(getContext(), android.R.layout.simple_list_item_1, allNotes);
        binding.listView.setAdapter(noteArrayAdapter);
        //when the screen comes up the list of notes auto comes up //

        binding.buttonDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                title = binding.editTextNoteTitle.getText().toString();
                noteText = binding.editTextNoteText.getText().toString();
                date = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
                tempImage = "";

                try {
                    newNote = new noteClass(noteText, title, date, tempImage);
                    Toast.makeText(getContext(), newNote.toString(), Toast.LENGTH_SHORT).show();

                }catch (Exception e){
                    Toast.makeText(getContext(), "Error creating note", Toast.LENGTH_SHORT).show();
                    newNote = new noteClass(noteText, title, date, tempImage);
                }

                boolean b = dataBaseHelper.addOne(newNote);

                /*
                DataBaseHelper dataBaseHelper = new DataBaseHelper(getContext());
                List<noteClass> allNotes = dataBaseHelper.getAllNotesFromLocalDB();

                ArrayAdapter noteArrayAdapter = new ArrayAdapter<noteClass>(getContext(), android.R.layout.simple_list_item_1, allNotes);
                binding.listView.setAdapter(noteArrayAdapter);

                 */

            }
        });



    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}