package com.cp3407.wildernessweather;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cp3407.wildernessweather.database.ConnectToDatabase;
import com.cp3407.wildernessweather.database.Word;
import com.cp3407.wildernessweather.database.WordListAdapter;
import com.cp3407.wildernessweather.database.WordViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class DbActivity extends AppCompatActivity {

    private WordViewModel mWordViewModel;
    public static final int NEW_WORD_ACTIVITY_REQUEST_CODE = 1;
    TextView databaseTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_db);

        databaseTextView = findViewById(R.id.connectionStatus);

        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        final WordListAdapter adapter = new WordListAdapter(new WordListAdapter.WordDiff());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mWordViewModel = new ViewModelProvider(this).get(WordViewModel.class);

        mWordViewModel.getAllWords().observe(this, words -> {
            // Update the cached copy of the words in the adapter.
            adapter.submitList(words);
        });

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> {
            Intent intent = new Intent(DbActivity.this, NewWordActivity.class);
            startActivityForResult(intent, NEW_WORD_ACTIVITY_REQUEST_CODE);
        });

        // Begin ASync task to collect data from database.
        ConnectToDatabase asyncTask = new ConnectToDatabase(new ConnectToDatabase.AsyncResponse() {
            @Override
            public void processFinish(String output) {
                databaseTextView.setText( output);
            }
        });
        asyncTask.execute();


    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == NEW_WORD_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            Word word = new Word(data.getStringExtra(NewWordActivity.EXTRA_REPLY));
            mWordViewModel.insert(word);
        } else {
            Toast.makeText(
                    getApplicationContext(),
                    R.string.empty_not_saved,
                    Toast.LENGTH_LONG).show();
        }
    }

}