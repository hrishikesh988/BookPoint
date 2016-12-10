package com.deshmukh.hrishikesh.bookpoint;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


/**
 * This is a BuyAndSellActivity which welcomes a user after sign in or sign up activity.
 * Displays welcome message
 * Displays user's first name and last name
 * Displays user's profile picture
 * Displays two buttons:
 *      Buy a book- opens BuyActivity
 *      Sell a book - opens SellActivity
 */
public class BuyAndSellActivity extends AppCompatActivity {

    /**
     * mUserName_TV - TextView to hold user's first + last name
     * mUserPic - imageview to hold user's profile pic
     * mbuybooksbutton - Buy a book- opens BuyActivity
     * msellbooksbutton - Sell a book - opens SellActivity
     */

    private TextView mUserName_TV;
    private ImageView mUserPic;
    private Button mbuybooksbutton;
    private Button msellbooksbutton;

    private FirebaseAuth mFirebaseAuth;
    private DatabaseReference mDatabaseReference;

    //Method to put extra in the intent
    //This method puts user's uID and profile picture in intent which is passed by signUp/LoginActivity

    public static Intent newIntent(Context packageContext, Bitmap image, String username) {
        Intent i = new Intent( packageContext, BuyAndSellActivity.class);
        i.putExtra("image", image);
        i.putExtra("user_name", username);
        return i;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_and_sell);

        //Wire up mUserName_TV and mUserPic to hold user's name and profile picture
        mUserName_TV = (TextView) findViewById(R.id.username_tv);
        mUserPic = (ImageView) findViewById(R.id.user_imageView);

        //Initiate Firebase user authenticator and Database reference
        mFirebaseAuth = FirebaseAuth.getInstance();
        mDatabaseReference = FirebaseDatabase.getInstance().getReference();

        Bundle extras = getIntent().getExtras();
        String username = extras.getString("user_name");

        //Add a addValueEventListener on database reference which search the user's records by user's username and
        // gets user's first name
        // and last name

        mDatabaseReference.child(username).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                UserInformation user = dataSnapshot.getValue(UserInformation.class);

                //Set user's first and last name to text view
                mUserName_TV.setText(user.mFirstName + " "+ user.LastName);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



        //Wire up buy book and sell book button
        mbuybooksbutton = (Button) findViewById(R.id.buy_button);
        msellbooksbutton = (Button) findViewById(R.id.sell_button);

       //Set user's profile pic
        if(getIntent() != null) {

            Bitmap imageBitmap = (Bitmap) extras.get("image");
          //  mUserName_TV.setText(username);
            if (imageBitmap != null)
                mUserPic.setImageBitmap(imageBitmap);
            else {

                mUserPic.setImageDrawable(getResources().getDrawable(R.drawable.profile_pic, null));
            }
        }



        /**
         * OnClickListener for msellbooksbutton which starts SellActivity
         */
        msellbooksbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(LoginActivity.this, "Sign up code goes here", Toast.LENGTH_SHORT).show();

                Intent i = SellActivity.newIntent(BuyAndSellActivity.this, mFirebaseAuth.getCurrentUser().getUid());
                startActivity(i);

            }
        });

        /**
         * OnClickListener for mbuybooksbutton which starts SellActivity
         */
        mbuybooksbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Toast.makeText(LoginActivity.this, "Sign up code goes here", Toast.LENGTH_SHORT).show();
                Intent i = BuyActivity.newIntent(BuyAndSellActivity.this, mFirebaseAuth.getCurrentUser().getUid());
                startActivity(i);

            }
        });


    }

}
