package com.deshmukh.hrishikesh.bookpoint;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by dhawaldabholkar on 12/3/16.
 * This is a BuyActivity. when User chooses to buy a book from BuyAndSaleActivity, this Activity is triggered.
 * This activity prompts user to enter book name using either text or speech input.
 * Then it searches the book name in firebase database and transfers user to BuyBookActivity.
 */

public class BuyActivity extends AppCompatActivity {


    /**
     * mSearchButton - Button to search user input with Books database (firebase JSON tree)
     * mImageButton - Image Button to invoke SpeechToText Recognizer intent
     * mBookName - EditText to hold book name provided by the user.
     */

    private Button mSearchButton;
    private ImageButton mImageButton;
    private EditText mBookname;


    //Method to put extra in the intent
    //This method puts user's uID in intent which is passed by BuyAndSaleActivity
    public static Intent newIntent(Context packageContext, String username) {
        Intent i = new Intent( packageContext, BuyActivity.class);
        i.putExtra("user_name", username);
        return i;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy);

        mBookname = (EditText) findViewById(R.id.name);
        mSearchButton = (Button) findViewById(R.id.search);
        mImageButton = (ImageButton) findViewById(R.id.imageButton);

        /**
         * OnClickListener for SearchButton: Take user input validates against valid and invalid values.
         * Upon invalid values: Shows a toast to user that input is invalid.
         * Upon valid values: Start a a BuyBookActivity by passing book name as extra into intent.
         */
        mSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle extras = getIntent().getExtras();
                //String username = extras.getString("user_name");

                //Validate user input
                String book_name = mBookname.getText().toString().trim();
                if(book_name.toLowerCase().equals("null")){
                    Toast.makeText(BuyActivity.this,"Book name can not be 'null'.", Toast.LENGTH_LONG).show();
                }
                else if (book_name.equals("")){
                    Toast.makeText(BuyActivity.this,"Book name can not be empty.", Toast.LENGTH_LONG).show();
                }
                //Upon valid input, start a a BuyBookActivity by passing book name as extra into intent
                else {
                    Intent i = BuyBookActivity.newIntent(BuyActivity.this, book_name );
                    startActivity(i);
                }

                /**
                 * OnClickListener for mImageButton: Invokes textToSpeech Recognizer by calling promptSpeechInput() method.
                 */
                mImageButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                            promptSpeechInput();


                    }
                });

            }
        });
    }

    /**
     * promptSpeechInput() - this method triggers speech recognizer.
     *                     - for supporting speech recognition through starting an Intent
     *                     - ACTION_RECOGNIZE_SPEECH - Starts an activity that will prompt the user for speech and send it through a speech recognizer.
     *                     - EXTRA_LANGUAGE_MODEL - Informs the recognizer which speech model to prefer when performing ACTION_RECOGNIZE_SPEECH.
     *                     - LANGUAGE_MODEL_FREE_FORM - Use a language model based on free-form speech recognition
     */
    public void promptSpeechInput() {
        Intent i = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        i.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        i.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        i.putExtra(RecognizerIntent.EXTRA_PROMPT, "Which book do you want to buy? ");

        try {
            startActivityForResult(i, 100);
        } catch (ActivityNotFoundException a)
        {
            Toast.makeText(BuyActivity.this,"sorry your device doesn't support speech language", Toast.LENGTH_LONG).show();
        }

    }

    /**
     * onActivityResult for speech-to-text intent (RecognizerIntent)
     * @param requestcode - Request code sent while starting an RecognizerIntent
     * @param resultcode - Result code received while receiving the speech to text conversion.
     * @param i - RecognizerIntent
     */
    public void onActivityResult(int requestcode,int resultcode, Intent i)
    {
      super.onActivityResult(requestcode, resultcode,i);
        switch (requestcode)
        {
            case 100: if(resultcode == RESULT_OK && i !=null)
            {
                ArrayList<String> result = i.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                mBookname.setText(result.get(0));

            }
                break;
        }

    }

}
