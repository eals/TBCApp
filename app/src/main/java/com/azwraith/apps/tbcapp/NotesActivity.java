package com.azwraith.apps.tbcapp;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class NotesActivity extends AppCompatActivity {

    private RecyclerView recycler;
    private String newNote;
    CardAdapter cardAdapter;
    TextView txt_note;

    List<String> notes = new ArrayList<String>();
    SharedPreferences sp;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);


        Toolbar toolbar = (Toolbar)findViewById(R.id.notes_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        sp = PreferenceManager.getDefaultSharedPreferences(this);
        editor = sp.edit();

        Set<String> notesSet = sp.getStringSet("notes", null);
        if(notesSet != null) {
            notes = new ArrayList<>(notesSet);
        }
        savetoSP();


        recycler = (RecyclerView)findViewById(R.id.recyclerView);

        recycler.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(NotesActivity.this);
        recycler.setLayoutManager(linearLayoutManager);
        cardAdapter = new CardAdapter(notes);
        recycler.setAdapter(cardAdapter);

        ItemClickSupport.addTo(recycler).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, final int position, View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(NotesActivity.this);
                builder.setTitle("Delete?");
                builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        notes.remove(position);
                        savetoSP();
                        cardAdapter.notifyItemRemoved(position);
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                builder.show();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbarmenu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public void savetoSP()
    {
        Set<String> set = new HashSet<String>();
        set.addAll(notes);
        editor.putStringSet("notes", set);
        editor.apply();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Pass the event to ActionBarDrawerToggle, if it returns
        // true, then it has handled the app icon touch event
        switch (item.getItemId())
        {
            case R.id.addNote:
                AlertDialog.Builder builder = new AlertDialog.Builder(NotesActivity.this);
                builder.setTitle("Add a note");
                final EditText input = new EditText(NotesActivity.this);
                builder.setView(input);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        newNote = input.getText().toString();
                        notes.add(0, newNote);
                        savetoSP();
                        cardAdapter.notifyItemInserted(0);
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });

                builder.show();
                return true;
        }
        // Handle your other action bar items...



        return super.onOptionsItemSelected(item);
    }


}
