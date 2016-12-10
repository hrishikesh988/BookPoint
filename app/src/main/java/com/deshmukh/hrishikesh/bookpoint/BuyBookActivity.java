package com.deshmukh.hrishikesh.bookpoint;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static android.view.View.VISIBLE;

/**
 * this is buyBookActivity which appears after user select a book he wants to buy.
 * this activity displays following information about a book selected by user.
 * Heading : your order - textView
 * Book title - textView
 * Book price - textView
 * Book publisher - textView
 * Book condition - textView
 * Buy button (to buy a book) - Button
 * Logout button
 * Buy a book button - Button (to go back to Buy Activity)
 * Sell a book button - Button (to go back to sell Activity)
 */
public class BuyBookActivity extends AppCompatActivity {

    //Define text view to display information about book user buying
    private TextView mTitle_textView;
    private TextView mPrice_textView;
    private TextView mPublisher_textView;
    private TextView mCondition_Spinner;
    private TextView mHeading_textView;

    //Define button to buy a book and handle navigations between other activities
    private Button buyButton;
    private Button buyABookButton;
    private Button sellABookButton;
    private Button logOutButton;
    private Button selectButton;


    private FirebaseAuth mFirebaseAuth;
    private DatabaseReference mDatabaseReference;


    //Method to put extra in the intent
    //This method puts user's uID and book_name in intent which is passed by buyActivity
    public static Intent newIntent(Context packageContext, String title) {
        Intent i = new Intent( packageContext, BuyBookActivity.class);
        //i.putExtra("user_name", username);
        i.putExtra("title", title);
        return i;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_book);

        //wire up all text view and a spinner widget
        mTitle_textView = (TextView) findViewById(R.id.textView5);
        mPrice_textView = (TextView) findViewById(R.id.textView6);
        mPublisher_textView = (TextView) findViewById(R.id.textView7);
        mCondition_Spinner = (TextView) findViewById(R.id.textView8);
        mHeading_textView = (TextView) findViewById(R.id.textView11);


        //wire up all the buttons
        buyButton = (Button)  findViewById(R.id.button);
        buyABookButton = (Button)  findViewById(R.id.button5);
        sellABookButton = (Button)  findViewById(R.id.button2);
        logOutButton = (Button)  findViewById(R.id.button4);
        selectButton = (Button)  findViewById(R.id.button6);

        /**
         * Set visibility of the buttons:
         *      - mHeading_textView: should be invisible this time
         *      - buyButton: should be invisible this time
         *      - buyABookButton: should be invisible this time
         *      - sellABookButton: should be invisible this time
         *      - logOutButton: should be invisible this time
         * these buttons should be invisible this time as user is only making selection
         */


        mHeading_textView.setVisibility(View.INVISIBLE);
        buyButton.setVisibility(View.INVISIBLE);
        buyABookButton.setVisibility(View.INVISIBLE);
        sellABookButton.setVisibility(View.INVISIBLE);
        logOutButton.setVisibility(View.INVISIBLE);

        mHeading_textView.setText("Your Order");


        /**
         * instantiate fibase user authenticator and db reference
         * mFirebaseAuth: User Authenticator
         * mDatabaseReference: Database Reference
          */

        mFirebaseAuth = FirebaseAuth.getInstance();
        mDatabaseReference = FirebaseDatabase.getInstance().getReference();


        //Get intent extas passed by BuyActivity
        Bundle extras = getIntent().getExtras();
        String username = extras.getString("user_name");
        final String title = extras.getString("title");

        //add addListenerForSingleValueEvent on db reference to retrieve values for databse

        //search all text books by their titles
        mDatabaseReference.child(title).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //Search result is returned as Object. cast this object as an object of Books class
                Books b = dataSnapshot.getValue(Books.class);

                //if book found, it is received as object of Books class,
                // so retrieve the properties and set it on on the user interface

                if(b != null) {
                    mTitle_textView.setText("Title: " + b.Title);
                    mPrice_textView.setText("Price: " + b.Price);
                    mPublisher_textView.setText("Publisher: " + b.Publisher);
                    mCondition_Spinner.setText("Condition: " + b.Condition);

                }

                //if no book is found, give user an appropriate message.
                else{

                    Toast.makeText(BuyBookActivity.this, "No Results found!",Toast.LENGTH_LONG).show();
                    mHeading_textView.setText("No Results for '"+title+"'");
                    mHeading_textView.setVisibility(VISIBLE);
                    buyABookButton.setVisibility(VISIBLE);
                    sellABookButton.setVisibility(VISIBLE);
                    logOutButton.setVisibility(VISIBLE);
                    selectButton.setVisibility(View.INVISIBLE);

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



        buyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mDatabaseReference.child(title) != null){

                    //Remove the book from databse after it is sold and give user confirmation message.
                    mDatabaseReference.child(title).removeValue();
                    Toast.makeText(BuyBookActivity.this, "Your order has been received and processed successfully.",Toast.LENGTH_LONG).show();
                    mTitle_textView.setVisibility(View.INVISIBLE);
                    mPrice_textView.setVisibility(View.INVISIBLE);
                    mPublisher_textView.setVisibility(View.INVISIBLE);
                    mCondition_Spinner.setVisibility(View.INVISIBLE);
                    mHeading_textView.setVisibility(View.INVISIBLE);
                    buyButton.setVisibility(View.INVISIBLE);
                    buyABookButton.setVisibility(VISIBLE);
                    sellABookButton.setVisibility(VISIBLE);
                    logOutButton.setVisibility(VISIBLE);

                }
            }
        });

        //Onclick listener for buyABookButton to start BuyActivity
        buyABookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(BuyBookActivity.this, BuyActivity.class);
                startActivity(i);
            }
        });

        //Onclick listener for sellABookButton to start SellActivity by passing current user's uID
        sellABookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = SellActivity.newIntent(BuyBookActivity.this,mFirebaseAuth.getCurrentUser().getUid());
                startActivity(i);
            }
        });

        //Onclick listener for logOutButton to start LoginActivity activity and log out the current user

        logOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFirebaseAuth.signOut();
                Intent i = new Intent(BuyBookActivity.this, LoginActivity.class);
                startActivity(i);

            }
        });

        //Onclick listener for selectButton to select the book and set the proper visibility of text views and buttons.

        selectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHeading_textView.setVisibility(VISIBLE);
                buyButton.setVisibility(VISIBLE);
                selectButton.setVisibility(View.INVISIBLE);


            }
        });

    }
}
