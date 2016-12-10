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
 * this is a SellActivity in which user publishes a book for selling by specifying details about the book he wants to sell.
 * The book details get stored in central database.
 */

public class SellActivity extends AppCompatActivity {

    //Edit text to receive title, price, publisher of the user
    private EditText mTitle;
    private EditText mPrice;
    private EditText mPublisher;
    //Spinner to receive condition of book. spinner values: Like a new, In good condition, Used
    private Spinner mSpinner;

    //User authenticator and db reference
    private FirebaseAuth mFirebaseAuth;
    private DatabaseReference mDatabaseReference;

    //place holders of submit and reset buttons
    private Button mSubmit;
    private Button mReset;
    String username;

    //array to hold spinner values
    ArrayAdapter<CharSequence> adapter;

    //Method to put extra in the intent
    //This method puts user's uID  in intent which is passed by BuyAndSellActivity
    public static Intent newIntent(Context packageContext, String username) {
        Intent i = new Intent( packageContext, SellActivity.class);
        i.putExtra("user_name", username);
        return i;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sell);

        //wire up all the resources
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

                //validate the book information

                if(mTitle.getText().toString().trim() == "" || mPrice.getText().toString().trim() == "" || mPublisher.getText().toString().trim() == "" ){

                    Toast.makeText(SellActivity.this, "Book information fields cannot be empty",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    //if book information is valid, add book to firebase database by calling saveBookInfo method
                    saveBookInfo();
                    Toast.makeText(SellActivity.this, "Book added",Toast.LENGTH_SHORT).show();
                    mTitle.setText("");
                    mPrice.setText("");
                    mPublisher.setText("");
                }
            }
        });

        //OnClickListener for reset button - sets value of all editTexts to ""

        mReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTitle.setText("");
                mPrice.setText("");
                mPublisher.setText("");


            }
        });


    }

    /**
     * this is a saveBookInfo which create a object of Books class and initializes its member variables with the values provided by user
     * and saves the Books object on Firebase real-time database.
     */
    private void saveBookInfo(){

        String Title = mTitle.getText().toString().trim();
        String price = mPrice.getText().toString().trim();
        String Publisher = mPublisher.getText().toString().trim();
        String condition = mSpinner.getSelectedItem().toString();
        String uid = mFirebaseAuth.getCurrentUser().getUid();


        //Creates the object of Books class and initializes its member variables with the values provided by user
        Books book = new Books(Title, price, Publisher, condition,uid);

        //saves the Books object on firebase real-time database.
        mDatabaseReference.child(Title).setValue(book);
    }

}
