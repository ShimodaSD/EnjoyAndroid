package com.example.a11810745.demomaps;

import android.app.ActionBar;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class Filter extends AppCompatActivity {

    private static final String TAG = "Filter";
    private ArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);

        ListView list  = (ListView) findViewById(R.id.Filter);
        EditText search = (EditText) findViewById(R.id.FilterSearch);

        ArrayList<String> categories = new ArrayList<>();
        categories.add("Festa");
        categories.add("Teatro");
        categories.add("Stand-up");
        categories.add("Show");

        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, categories);
        list.setAdapter(adapter);

        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Filter.this.adapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    public boolean onOptionsItemSelected(MenuItem item){
        Intent myIntent = new Intent(getApplicationContext(), Filter.class);
        finish();
        return true;
    }
}
