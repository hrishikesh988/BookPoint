package com.deshmukh.hrishikesh.bookpoint;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

/**
 * This is Login Activity which has
 *  - user name editText
 *  - Password editText
 *  - Sign in - button
 *  - Sign up - Button
 */
public class LoginActivity extends AppCompatActivity {

    //Define all widgets - text views, edit texts, buttons, check box

    private EditText mUserName;
    private EditText mPassWord;
    private Button mSignIn_button;
    private Button mSignUp_button;
    private CheckBox mRememberMe;

    //boolean to check if user has click on Remember me check box
    private boolean saveLogin;

    //share preference variable to handle user's remember me choice
    private SharedPreferences loginPreferences;
    private SharedPreferences.Editor loginPrefEditor;

    //Login/User authenticator
    private FirebaseAuth mFirebaseAuth;

    //Progressbar to display loading/Please wait screen
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        //wire up all the widgets
        mUserName = (EditText) findViewById(R.id.UserName_ET);
        mPassWord = (EditText) findViewById(R.id.Password_ET);
        mSignIn_button = (Button) findViewById(R.id.SignIn_Button);
        mSignUp_button = (Button) findViewById(R.id.SignUp_Button);
        mRememberMe = (CheckBox) findViewById(R.id.remember_me_checkbox);
        loginPreferences = getSharedPreferences("loginPrefs",MODE_PRIVATE);
        loginPrefEditor = loginPreferences.edit();

        mProgressDialog = new ProgressDialog(this);
        mFirebaseAuth = FirebaseAuth.getInstance();


        saveLogin = loginPreferences.getBoolean("saveLogin",false);

        //If "remember me" check box was checked last time restore user's login info
        if(saveLogin){
            mUserName.setText(loginPreferences.getString("username",""));
            mPassWord.setText(loginPreferences.getString("password",""));
            mRememberMe.setChecked(true);
        }

        mSignIn_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //If user checks "remember me" check box was checked last time restore user's login info
                if(mRememberMe.isChecked()){
                    loginPrefEditor.putBoolean("saveLogin", true);
                    loginPrefEditor.putString("username",mUserName.getText().toString());
                    loginPrefEditor.putString("password", mPassWord.getText().toString());
                    loginPrefEditor.commit();
                }
                else{
                    loginPrefEditor.clear();
                    loginPrefEditor.commit();
                }

                mProgressDialog.setMessage("Signing In...");
                mProgressDialog.show();

                mFirebaseAuth.signInWithEmailAndPassword(mUserName.getText().toString(), mPassWord.getText().toString())
                        .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                mProgressDialog.dismiss();
                                if(task.isSuccessful()){
                                    Toast.makeText(LoginActivity.this,"Login successful", Toast.LENGTH_SHORT).show();
                                    //Intent i = new Intent(LoginActivity.this, BuyAndSellActivity.class);
                                    Intent i = BuyAndSellActivity.newIntent(LoginActivity.this,null, mFirebaseAuth.getCurrentUser().getUid());
                                    startActivity(i);
                                }
                                else
                                {
                                    Toast.makeText(LoginActivity.this,"Login Failed", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }

        });

        mSignUp_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(LoginActivity.this, "Sign up code goes here", Toast.LENGTH_SHORT).show();

                Intent i = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(i);

            }
        });
    }




}
