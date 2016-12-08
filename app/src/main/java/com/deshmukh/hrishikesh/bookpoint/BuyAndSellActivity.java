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

public class BuyAndSellActivity extends AppCompatActivity {

    private TextView mUserName_TV;
    private ImageView mUserPic;
    private Button mbuybooksbutton;
    private Button msellbooksbutton;

    private FirebaseAuth mFirebaseAuth;
    private DatabaseReference mDatabaseReference;


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

        mUserName_TV = (TextView) findViewById(R.id.username_tv);
        mUserPic = (ImageView) findViewById(R.id.user_imageView);

        mFirebaseAuth = FirebaseAuth.getInstance();
        mDatabaseReference = FirebaseDatabase.getInstance().getReference();

        Bundle extras = getIntent().getExtras();
        String username = extras.getString("user_name");
        mDatabaseReference.child(username).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                UserInformation user = dataSnapshot.getValue(UserInformation.class);

                mUserName_TV.setText(user.mFirstName + " "+ user.LastName);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });




        mbuybooksbutton = (Button) findViewById(R.id.buy_button);
        msellbooksbutton = (Button) findViewById(R.id.sell_button);
        if(getIntent() != null) {

            Bitmap imageBitmap = (Bitmap) extras.get("image");
          //  mUserName_TV.setText(username);
            if (imageBitmap != null)
                mUserPic.setImageBitmap(imageBitmap);
            else {

                mUserPic.setImageDrawable(getResources().getDrawable(R.drawable.profile_pic, null));
            }
        }




        msellbooksbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(LoginActivity.this, "Sign up code goes here", Toast.LENGTH_SHORT).show();

                Intent i = SellActivity.newIntent(BuyAndSellActivity.this, mFirebaseAuth.getCurrentUser().getUid());
                startActivity(i);

            }
        });

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
