package com.deshmukh.hrishikesh.bookpoint;

import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import static android.R.attr.data;

public class SignUpActivity extends AppCompatActivity {

    private EditText mFirstName;
    private EditText mLastName;
    private EditText mPhoneNumber;
    private EditText mEmailID;
    private EditText mPassword;
    private EditText mConfirmPassword;
    private EditText mLocation;

    private Button mRegisterButton;
    private Button mResetButton;
    private ImageView mProfilePic;
    private ImageButton mCameraButton;

    static final int REQUEST_IMAGE_CAPTURE = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mFirstName = (EditText) findViewById(R.id.first_name_et);
        mLastName = (EditText) findViewById(R.id.last_name_et);
        mPhoneNumber = (EditText) findViewById(R.id.phone_number_et);
        mEmailID = (EditText) findViewById(R.id.email_et);
        mPassword = (EditText) findViewById(R.id.password_et);
        mConfirmPassword = (EditText) findViewById(R.id.confirm_password_et);
        mLocation = (EditText) findViewById(R.id.location_et);
        mRegisterButton = (Button) findViewById(R.id.register_button);
        mResetButton = (Button) findViewById(R.id.reset_button);
        mProfilePic = (ImageView) findViewById(R.id.profile_pic_iv);
        mCameraButton = (ImageButton) findViewById(R.id.camera_button);

        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(mFirstName.getText().toString().trim().equals("") || mLastName.getText().toString().trim().equals("")
                        ||mPhoneNumber.getText().toString().trim().equals("") || mEmailID.getText().toString().trim().equals("")
                        || mPassword.getText().toString().trim().equals("") || mConfirmPassword.getText().toString().trim().equals("")
                        || mLocation.getText().toString().trim().equals("")){
                    Toast.makeText(SignUpActivity.this, "User details can not be blank.",Toast.LENGTH_SHORT).show();
                }
                else if(! mPassword.getText().toString().equals(mConfirmPassword.getText().toString())){
                    Toast.makeText(SignUpActivity.this, "Passwords do not match! Please try again.",Toast.LENGTH_SHORT).show();
                }
            }
        });

        mResetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFirstName.setText("");
                mLastName.setText("");
                mPhoneNumber.setText("");
                mEmailID.setText("");
                mPassword.setText("");
                mConfirmPassword.setText("");
                mLocation.setText("");
            }
        });

        mCameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent takePhotoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if(takePhotoIntent.resolveActivity(getPackageManager())!=null){
                    startActivityForResult(takePhotoIntent, REQUEST_IMAGE_CAPTURE);
                }
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            mProfilePic.setImageBitmap(imageBitmap);
        }
    }
}
