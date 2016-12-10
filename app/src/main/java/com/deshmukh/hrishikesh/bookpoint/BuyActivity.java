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
 */

public class BuyActivity extends AppCompatActivity {

    //private EditText mBookname;
    private Button mSearchButton;
    private ImageButton mImageButton;
    private EditText mBookname;


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

        mSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle extras = getIntent().getExtras();
                //String username = extras.getString("user_name");
                String book_name = mBookname.getText().toString().trim();
                if(book_name.toLowerCase().equals("null")){
                    Toast.makeText(BuyActivity.this,"Book name can not be 'null'.", Toast.LENGTH_LONG).show();
                }
                else if (! book_name.equals("")){
                    Intent i = BuyBookActivity.newIntent(BuyActivity.this, book_name );
                    startActivity(i);
                }
                else
                    Toast.makeText(BuyActivity.this,"Book name can not be empty.", Toast.LENGTH_LONG).show();


            }
        });
    }

    public void onButtonClick(View v)
    {
        if(v.getId()==R.id.imageButton)
        {
            promptSpeechInput();
        }
    }

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
