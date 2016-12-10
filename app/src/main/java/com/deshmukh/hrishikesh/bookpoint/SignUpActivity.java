package com.deshmukh.hrishikesh.bookpoint;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * This is a sign up activity class which takes user details from user and store them on fire base database.
 * User information:
 *      First Name
 *      Last Name
 *      Phone Number
 *      email
 *      Password
 *      location
 */

public class SignUpActivity extends AppCompatActivity {

    //Define edit texts to take user details
    private EditText mFirstName;
    private EditText mLastName;
    private EditText mPhoneNumber;
    private EditText mEmailID;
    private EditText mPassword;
    private EditText mConfirmPassword;
    private EditText mLocation;

    //Define ImageView to store profile picture
    private ImageView mProfilePic;
    //Define buttons to register and reset
    private Button mRegisterButton;
    private Button mResetButton;
    //Image button to start camera and to launch PlacePicker intent
    private ImageButton mCameraButton;
    private ImageButton mLocationButton;

    private ProgressDialog mProgressDialog;
    private FirebaseAuth mFirebaseAuth;
    private DatabaseReference mDatabaseReference;

    private Bitmap imageBitmap;

    String username;
    String password;

    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int PLACE_PICKER_REQUEST=2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        //Wire up all the resources
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
        mLocationButton = (ImageButton) findViewById(R.id.location_button);

        mProgressDialog = new ProgressDialog(this);
        mFirebaseAuth = FirebaseAuth.getInstance();
        mDatabaseReference = FirebaseDatabase.getInstance().getReference();

        //OnClickListener for mRegisterButton to store user's information to DB
        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Validate info entered by the user
                if(mFirstName.getText().toString().trim().equals("") || mLastName.getText().toString().trim().equals("")
                        ||mPhoneNumber.getText().toString().trim().equals("") || mEmailID.getText().toString().trim().equals("")
                        || mPassword.getText().toString().trim().equals("") || mConfirmPassword.getText().toString().trim().equals("")
                        || mLocation.getText().toString().trim().equals("")){
                    Toast.makeText(SignUpActivity.this, "User details can not be blank.",Toast.LENGTH_SHORT).show();
                }
                else if(! mPassword.getText().toString().equals(mConfirmPassword.getText().toString())){
                    Toast.makeText(SignUpActivity.this, "Passwords do not match! Please try again.",Toast.LENGTH_SHORT).show();
                }
                else if (mPhoneNumber.getText().toString().length()<6){
                    Toast.makeText(SignUpActivity.this, "Passwords be at least 6 letters. ",Toast.LENGTH_SHORT).show();
                }

                //if information found correct do:
                //1. Show progress dialog
                //2. store user data to firebase DB
                else{
                    mProgressDialog.setMessage("Registering User...");
                    mProgressDialog.show();
                     username = mEmailID.getText().toString();
                     password = mPassword.getText().toString();
                    mFirebaseAuth.createUserWithEmailAndPassword(username,password)
                            .addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    mProgressDialog.dismiss();
                                        if(task.isSuccessful()){
                                            mFirebaseAuth.signInWithEmailAndPassword(username,password).addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {
                                                @Override
                                                public void onComplete(@NonNull Task<AuthResult> task) {
                                                    if(task.isSuccessful()){
                                                        Toast.makeText(SignUpActivity.this,"contact saved", Toast.LENGTH_SHORT).show();
                                                        saveUserInfo();
                                                        Toast.makeText(SignUpActivity.this,"Registered successfully", Toast.LENGTH_SHORT).show();
                                                        FirebaseUser user = mFirebaseAuth.getCurrentUser();
                                                        String username = user.getUid();
                                                        Intent i = BuyAndSellActivity.newIntent(SignUpActivity.this,imageBitmap,username);
                                                        //Intent i = new Intent(SignUpActivity.this, LoginActivity.class);
                                                        startActivity(i);

                                                    }
                                                }
                                            });

                                        }
                                }
                            });


                }
            }
        });

        //OnClickListener for mResetButton  to reset all edit text fields
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

        //OnClickListener for mCameraButton which start takePhotoIntent to take picture from camera and set it to image view later
        mCameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent takePhotoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if(takePhotoIntent.resolveActivity(getPackageManager())!=null){
                    startActivityForResult(takePhotoIntent, REQUEST_IMAGE_CAPTURE);
                }
            }
        });

        //OnClickListener for mLocationButton which start PlacePicker to take user's current location from Google Maps and set it to edit text view later
        mLocationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
                Intent intent;
                try{
                    intent = builder.build(SignUpActivity.this);
                    startActivityForResult(intent, PLACE_PICKER_REQUEST);
                }

                catch (GooglePlayServicesNotAvailableException e){
                    e.printStackTrace();
                }
                catch (GooglePlayServicesRepairableException e){
                    e.printStackTrace();
                }
            }

        });


    }

    /**
     * saveUserInfo - this method store user's information to firebase user Authentication database
     */
    private void saveUserInfo(){

        String firstName = mFirstName.getText().toString().trim();
        String lastName = mLastName.getText().toString().trim();
        String phoneNumber = mPhoneNumber.getText().toString().trim();
        String location = mLocation.getText().toString().trim();
        String email = mEmailID.getText().toString().trim();
        String password = mPassword.getText().toString().trim();


        UserInformation user = new UserInformation(firstName, lastName, phoneNumber,email,password,location);
        FirebaseUser current_user = mFirebaseAuth.getCurrentUser();

        //Get uID of an user, set it as a parent of JSON tree and add user's info as its children.
        mDatabaseReference.child(current_user.getUid()).setValue(user);
    }

    /**
     *
     * @param requestCode - Request code sent in an intent
     * @param resultCode - Result code received from an intent
     * @param data - data received from an intent
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        //If request code is REQUEST_IMAGE_CAPTURE and result code is OK, set user's profile picture as image from received from camera
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            imageBitmap = (Bitmap) extras.get("data");
            mProfilePic.setImageBitmap(imageBitmap);
        }

        //If request code is REQUEST_IMAGE_CAPTURE and result code is OK, set user location as location received from PlacePicker
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace(data, SignUpActivity.this);
                mLocation.setText(place.getAddress());
            }
        }
    }
}
