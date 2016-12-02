package com.deshmukh.hrishikesh.bookpoint;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import javax.xml.datatype.Duration;

public class LoginActivity extends AppCompatActivity {

    private EditText mUserName;
    private EditText mPassWord;
    private Button mSignIn_button;
    private Button mSignUp_button;
    private CheckBox mRememberMe;
    private boolean saveLogin;
    private SharedPreferences loginPreferences;
    private SharedPreferences.Editor loginPrefEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mUserName = (EditText) findViewById(R.id.UserName_ET);
        mPassWord = (EditText) findViewById(R.id.Password_ET);
        mSignIn_button = (Button) findViewById(R.id.SignIn_Button);
        mSignUp_button = (Button) findViewById(R.id.SignUp_Button);
        mRememberMe = (CheckBox) findViewById(R.id.remember_me_checkbox);
        loginPreferences = getSharedPreferences("loginPrefs",MODE_PRIVATE);
        loginPrefEditor = loginPreferences.edit();

        saveLogin = loginPreferences.getBoolean("saveLogin",false);

        if(saveLogin){
            mUserName.setText(loginPreferences.getString("username",""));
            mPassWord.setText(loginPreferences.getString("password",""));
            mRememberMe.setChecked(true);
        }

        mSignIn_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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
