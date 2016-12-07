package com.deshmukh.hrishikesh.bookpoint;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

/**
 * Created by dhawaldabholkar on 12/3/16.
 */

public class SellActivity extends AppCompatActivity {
    private EditText mTitle;
    private EditText mPrice;
    private EditText mPublisher;
    private Spinner mSpinner;

    private Button mSubmit;
    private Button mReset;
    Spinner spinner;
    ArrayAdapter<CharSequence> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sell);
        mReset = (Button)findViewById(R.id.reset_button);
        mTitle = (EditText)findViewById(R.id.name);
        mPrice = (EditText)findViewById(R.id.price);
        mPublisher = (EditText)findViewById(R.id.publisher);
        spinner = (Spinner)findViewById(R.id.spinner);
        adapter = ArrayAdapter.createFromResource(this,R.array.Conditions,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                Toast.makeText(getBaseContext(),parent.getItemAtPosition(position)+" selected",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTitle.setText("");
                mPrice.setText("");
                mPublisher.setText("");
                //mSpinner.setAdapter(null);

            }
        });


    }





}
