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

public class BuyAndSellActivity extends AppCompatActivity {

    private TextView mUserName_TV;
    private ImageView mUserPic;
    private Button mbuybooksbutton;
    private Button msellbooksbutton;
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

        mbuybooksbutton = (Button) findViewById(R.id.buy_button);
        msellbooksbutton = (Button) findViewById(R.id.sell_button);

        Bundle extras = getIntent().getExtras();
        Bitmap imageBitmap = (Bitmap) extras.get("image");
        String username = extras.getString("user_name");
        mUserName_TV.setText(username);
        if(imageBitmap != null)
            mUserPic.setImageBitmap(imageBitmap);
        else {

            mUserPic.setImageDrawable(getResources().getDrawable(R.drawable.profile_pic, null));
        }



        msellbooksbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(LoginActivity.this, "Sign up code goes here", Toast.LENGTH_SHORT).show();

                Intent i = new Intent(BuyAndSellActivity.this, SellActivity.class);
                startActivity(i);

            }
        });

        mbuybooksbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(LoginActivity.this, "Sign up code goes here", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(BuyAndSellActivity.this, BuyActivity.class);
                startActivity(i);

            }
        });


    }
    @Override
    public void onBackPressed() {
    }
}
