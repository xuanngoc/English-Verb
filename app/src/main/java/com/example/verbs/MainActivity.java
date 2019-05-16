package com.example.verbs;

/*
source search voice : https://www.androidhive.info/2014/07/android-speech-to-text-tutorial/

        */

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;
import android.support.v7.widget.SearchView;
import android.widget.Toast;

import com.example.verbs.database.Verb;
import com.example.verbs.database.VerbDb;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private VerbDb mDbHelper;
    private final int REQ_CODE_SPEECH_INPUT = 100;
    private Cursor mCursor;
    private ListView mListView;
    private VerbCursorAdapter mCursorAdapter;
    private SearchView mSearchView;
    private Cursor currentCursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mListView = findViewById(R.id.listView);

        mDbHelper = new VerbDb(this);
        mDbHelper.createDatabase();
        mDbHelper.open();

        mCursor = mDbHelper.getTestData();
        currentCursor = mCursor;
        mCursorAdapter = new VerbCursorAdapter(this, mCursor, 0);
        mListView.setAdapter(mCursorAdapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                currentCursor.moveToPosition(position);


                int infinitiveColumnIndex = currentCursor.getColumnIndexOrThrow(Verb.INFINITIVE);
                int simplePastColumnIndex = currentCursor.getColumnIndexOrThrow(Verb.SIMPLE_PAST);
                int participleColumnIndex = currentCursor.getColumnIndexOrThrow(Verb.PAST_PARTICIPLE);

                int phonetic_infinitiveColumnIndex = currentCursor.getColumnIndexOrThrow(Verb.PHONETIC_INFINITIVE);
                int phonetic_simplePastColumnIndex = currentCursor.getColumnIndexOrThrow(Verb.PHONETIC_SIMPLE_PAST);
                int phonetic_participleColumnIndex = currentCursor.getColumnIndexOrThrow(Verb.PHONETIC_PAST_PARTICIPLE);

                int definitionColumnIndex = currentCursor.getColumnIndexOrThrow(Verb.DEFINITION);
                int sample1ColumnIndex = currentCursor.getColumnIndexOrThrow(Verb.SAMPLE1);
                int sample2ColumnIndex = currentCursor.getColumnIndexOrThrow(Verb.SAMPLE2);
                int sample3ColumnIndex = currentCursor.getColumnIndexOrThrow(Verb.SAMPLE3);


                String infinitive = currentCursor.getString(infinitiveColumnIndex);
                String simplePast = currentCursor.getString(simplePastColumnIndex);
                String participle = currentCursor.getString(participleColumnIndex);

                String phonetic_infinitive = currentCursor.getString(phonetic_infinitiveColumnIndex);
                String phonetic_simplePast = currentCursor.getString(phonetic_simplePastColumnIndex);
                String phonetic_participle = currentCursor.getString(phonetic_participleColumnIndex);

                String definition = currentCursor.getString(definitionColumnIndex);
                String sample1 = currentCursor.getString(sample1ColumnIndex);
                String sample2 = currentCursor.getString(sample2ColumnIndex);
                String sample3 = currentCursor.getString(sample3ColumnIndex);

                Intent intent = new Intent(MainActivity.this, DetailVerb.class);

                // Put extra to DetailVerb Activity
                intent.putExtra("infinitive", infinitive);
                intent.putExtra("simplePast", simplePast);
                intent.putExtra("participle", participle);

                intent.putExtra("phonetic_infinitive", phonetic_infinitive);
                intent.putExtra("phonetic_simplePast", phonetic_simplePast);
                intent.putExtra("phonetic_participle", phonetic_participle);

                intent.putExtra("definition", definition);

                intent.putExtra("sample1", sample1);
                intent.putExtra("sample2", sample2);
                intent.putExtra("sample3", sample3);

                startActivity(intent);
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        mSearchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //Toast.makeText(MainActivity.this, "Submitted",Toast.LENGTH_LONG).show();
                Cursor mQuerySubmitCursor = mDbHelper.getResultSearch(query);
                mCursorAdapter.swapCursor(mQuerySubmitCursor);
                currentCursor = mQuerySubmitCursor;
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                if(newText.length() > 0) {
                    //Toast.makeText(MainActivity.this, "Text is changing", Toast.LENGTH_LONG).show();
                    Cursor queryTextChangeCursor = mDbHelper.getResultSearch(newText);
                    mCursorAdapter.swapCursor(queryTextChangeCursor);
                    currentCursor = queryTextChangeCursor;
                }
                if(newText.length() == 0){
                    currentCursor = mCursor;
                    mCursorAdapter.swapCursor(mCursor);
                }
                return true;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Toast.makeText(MainActivity.this, "Chưa xong", Toast.LENGTH_SHORT ).show();

            return true;
        }else if(id == R.id.action_about_me){
            Intent intent = new Intent(MainActivity.this, About_me.class);
            startActivity(intent);
            return true;
        }else if(id == R.id.speech_action){
            promptSpeechInput();
        }

        return super.onOptionsItemSelected(item);
    }


    private void promptSpeechInput(){
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                "Say something");
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(getApplicationContext(),
                    "Sorry! Your device doesn\\'t support speech input",
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {

                    ArrayList<String> result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    /*txtSpeechInput.setText(result.get(0));*/
                    Cursor mQuerySubmitCursor = mDbHelper.getResultSearch(result.get(0));
                    mCursorAdapter.swapCursor(mQuerySubmitCursor);
                    currentCursor = mQuerySubmitCursor;
                }
                break;
            }

        }
    }
}
