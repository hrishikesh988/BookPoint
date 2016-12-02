package com.deshmukh.hrishikesh.bookpoint;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

public class BuyAndSellActivity extends AppCompatActivity {

    private TextView mUserName_TV;
    private ImageView mUserPic;
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

        Bundle extras = getIntent().getExtras();
        Bitmap imageBitmap = (Bitmap) extras.get("image");
        String username = extras.getString("user_name");
        mUserName_TV.setText(username);
        mUserPic.setImageBitmap(imageBitmap);
    }
    @Override
    public void onBackPressed() {
    }
}