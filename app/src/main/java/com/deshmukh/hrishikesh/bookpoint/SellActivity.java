package com.deshmukh.hrishikesh.bookpoint;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by dhawaldabholkar on 12/3/16.
 */

public class SellActivity extends AppCompatActivity {
    private EditText mTitle;
    private EditText mPrice;
    private EditText mPublisher;
    private Spinner mSpinner;

    private FirebaseAuth mFirebaseAuth;
    private DatabaseReference mDatabaseReference;

    private Button mSubmit;
    private Button mReset;
    String username;
    ArrayAdapter<CharSequence> adapter;

    public static Intent newIntent(Context packageContext, String username) {
        Intent i = new Intent( packageContext, SellActivity.class);
        i.putExtra("user_name", username);
        return i;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sell);
        mReset = (Button)findViewById(R.id.reset_button);
        mTitle = (EditText)findViewById(R.id.name);
        mPrice = (EditText)findViewById(R.id.price);
        mPublisher = (EditText)findViewById(R.id.publisher);
        mSpinner = (Spinner)findViewById(R.id.spinner);
        adapter = ArrayAdapter.createFromResource(this,R.array.Conditions,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner.setAdapter(adapter);
        mSubmit = (Button) findViewById(R.id.button3);

        mTitle.requestFocus();

        mFirebaseAuth = FirebaseAuth.getInstance();
        mDatabaseReference = FirebaseDatabase.getInstance().getReference();

        Bundle extras = getIntent().getExtras();
        username = extras.getString("user_name");

        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                //Toast.makeText(getBaseContext(),parent.getItemAtPosition(position)+" selected",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(mTitle.getText().toString().trim() == "" || mPrice.getText().toString().trim() == "" || mPublisher.getText().toString().trim() == "" ){

                    Toast.makeText(SellActivity.this, "Book information fields cannot be empty",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    saveBookInfo();
                    Toast.makeText(SellActivity.this, "Book added",Toast.LENGTH_SHORT).show();
                    mTitle.setText("");
                    mPrice.setText("");
                    mPublisher.setText("");
                }
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




    private void saveBookInfo(){

        String Title = mTitle.getText().toString();
        String price = mPrice.getText().toString();
        String Publisher = mPublisher.getText().toString();
        String condition = mSpinner.getSelectedItem().toString();
        String uid = mFirebaseAuth.getCurrentUser().getUid();


        Books book = new Books(Title, price, Publisher, condition,uid);

        //mDatabaseReference.child("Books").child(Title).setValue(book);
        mDatabaseReference.child(Title).setValue(book);
    }

}
