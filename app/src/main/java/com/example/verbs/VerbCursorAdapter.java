package com.example.verbs;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.example.verbs.database.Verb;

public class VerbCursorAdapter extends CursorAdapter {

    private LayoutInflater cursorInflater;

    public VerbCursorAdapter(Context context, Cursor cursor, int flag){
        super(context, cursor, flag);
        cursorInflater = (LayoutInflater) context.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {

        return cursorInflater.inflate(R.layout.list_item, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        // Find individual views that we want to modify in the list item layout
        TextView infinitiveView = view.findViewById(R.id.infinitive_view);
        TextView simplePastView = view.findViewById(R.id.simple_past_view);
        TextView participleView = view.findViewById(R.id.past_participle_view);
        TextView definitionView = view.findViewById(R.id.definition_view);

        // Find the columns of Verb attributes
        int infinitiveColumnIndex = cursor.getColumnIndex(Verb.INFINITIVE);
        int simplePastColumnIndex = cursor.getColumnIndex(Verb.SIMPLE_PAST);
        int participleColumnIndex = cursor.getColumnIndex(Verb.PAST_PARTICIPLE);
        int definitionColumnIndex = cursor.getColumnIndex(Verb.DEFINITION);

        // Read the Verb attributes from the Cursor for the current Verb
        String infinitive = cursor.getString(infinitiveColumnIndex);
        String simplePast = cursor.getString(simplePastColumnIndex);
        String participle = cursor.getString(participleColumnIndex);
        String definition = cursor.getString(definitionColumnIndex);

        // Update the TextViews with the attributes for the current Verb
        infinitiveView.setText(infinitive);
        simplePastView.setText(simplePast);
        participleView.setText(participle);
        definitionView.setText("-> " + definition);

    }
}
