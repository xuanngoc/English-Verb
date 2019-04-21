package com.example.verbs;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

public class DetailVerb extends AppCompatActivity {

    TextView infinitiveView;
    TextView simplePastView;
    TextView pastParticipleView;

    TextView phoneticInfinitiveView;
    TextView phoneticSimplePastView;
    TextView phoneticPastParticipleView;

    TextView definitionView;
    TextView sample1;
    TextView sample2;
    TextView sample3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_verb);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        infinitiveView = findViewById(R.id.infinitive_view);
        simplePastView = findViewById(R.id.simple_past_view);
        pastParticipleView = findViewById(R.id.past_participle_view);

        phoneticInfinitiveView = findViewById(R.id.phonetic_infinitive_view);
        phoneticSimplePastView = findViewById(R.id.phonetic_simple_past_view);
        phoneticPastParticipleView = findViewById(R.id.phonetic_past_participle_view);

        definitionView = findViewById(R.id.definition_view);
        sample1 = findViewById(R.id.sample1View);
        sample2 = findViewById(R.id.sample2View);
        sample3 = findViewById(R.id.sample3View);

        Intent i = getIntent();
        Bundle b = i.getExtras();


        infinitiveView.setText(b.get("infinitive").toString());
        simplePastView.setText(b.get("simplePast").toString());
        pastParticipleView.setText(b.get("participle").toString());

        phoneticInfinitiveView.setText(b.get("phonetic_infinitive").toString());
        phoneticSimplePastView.setText(b.get("phonetic_simplePast").toString());
        phoneticPastParticipleView.setText(b.get("phonetic_participle").toString());

        definitionView.setText(b.get("definition").toString());

        sample1.setText("- " + b.get("sample1").toString());
        sample2.setText("- " + b.get("sample2").toString());
        sample3.setText("- " + b.get("sample3").toString());

    }

}
