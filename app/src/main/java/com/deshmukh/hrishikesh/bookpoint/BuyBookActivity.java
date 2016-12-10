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

public class BuyBookActivity extends AppCompatActivity {

    private TextView t1;
    private TextView t2;
    private TextView t3;
    private TextView t4;
    private TextView t5;
    private TextView t6;

    private Button b1;
    private Button b2;
    private Button b3;
    private Button b4;
    private Button b5;


    private String user_name;

    private FirebaseAuth mFirebaseAuth;
    private DatabaseReference mDatabaseReference;
    private DatabaseReference mDatabaseReference1;

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

        t1 = (TextView) findViewById(R.id.textView5);
        t2 = (TextView) findViewById(R.id.textView6);
        t3 = (TextView) findViewById(R.id.textView7);
        t4 = (TextView) findViewById(R.id.textView8);
        t5 = (TextView) findViewById(R.id.textView11);


        b1 = (Button)  findViewById(R.id.button);
        b2 = (Button)  findViewById(R.id.button5);
        b3 = (Button)  findViewById(R.id.button2);
        b4 = (Button)  findViewById(R.id.button4);
        b5 = (Button)  findViewById(R.id.button6);

        t5.setVisibility(View.INVISIBLE);
        b1.setVisibility(View.INVISIBLE);
        b2.setVisibility(View.INVISIBLE);
        b3.setVisibility(View.INVISIBLE);
        b4.setVisibility(View.INVISIBLE);

        t5.setText("Your Order");



        mFirebaseAuth = FirebaseAuth.getInstance();
        mDatabaseReference = FirebaseDatabase.getInstance().getReference();
        mDatabaseReference1 = FirebaseDatabase.getInstance().getReference();

        Bundle extras = getIntent().getExtras();
        String username = extras.getString("user_name");
        final String title = extras.getString("title");

        mDatabaseReference.child(title).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Books b = dataSnapshot.getValue(Books.class);

                if(b != null) {
                    t1.setText("Title: " + b.Title);
                    t2.setText("Price: " + b.Price);
                    t3.setText("Publisher: " + b.Publisher);
                    t4.setText("Condition: " + b.Condition);
                    user_name = b.UID;
                }

                else{

                    Toast.makeText(BuyBookActivity.this, "No Results found!",Toast.LENGTH_LONG).show();
                    t5.setText("No Results for '"+title+"'");
                    t5.setVisibility(VISIBLE);
                    b2.setVisibility(VISIBLE);
                    b3.setVisibility(VISIBLE);
                    b4.setVisibility(VISIBLE);
                    b5.setVisibility(View.INVISIBLE);

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mDatabaseReference.child(title) != null){

                    mDatabaseReference.child(title).removeValue();
                    Toast.makeText(BuyBookActivity.this, "Your order has been received and processed successfully.",Toast.LENGTH_LONG).show();
                    t1.setVisibility(View.INVISIBLE);
                    t2.setVisibility(View.INVISIBLE);
                    t3.setVisibility(View.INVISIBLE);
                    t4.setVisibility(View.INVISIBLE);
                    t5.setVisibility(View.INVISIBLE);
                    b1.setVisibility(View.INVISIBLE);
                    b2.setVisibility(VISIBLE);
                    b3.setVisibility(VISIBLE);
                    b4.setVisibility(VISIBLE);

                }
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(BuyBookActivity.this, BuyActivity.class);
                startActivity(i);
            }
        });

        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = SellActivity.newIntent(BuyBookActivity.this,mFirebaseAuth.getCurrentUser().getUid());
                startActivity(i);
            }
        });

        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFirebaseAuth.signOut();
                Intent i = new Intent(BuyBookActivity.this, LoginActivity.class);
                startActivity(i);

            }
        });

        b5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                t5.setVisibility(VISIBLE);
                b1.setVisibility(VISIBLE);
                b5.setVisibility(View.INVISIBLE);


            }
        });

    }
}
