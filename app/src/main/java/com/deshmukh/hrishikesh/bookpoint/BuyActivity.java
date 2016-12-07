package com.deshmukh.hrishikesh.bookpoint;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy);

        mBookname = (EditText) findViewById(R.id.name);
        mSearchButton = (Button) findViewById(R.id.search);
        mImageButton = (ImageButton) findViewById(R.id.imageButton);
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
        i.putExtra(RecognizerIntent.EXTRA_PROMPT, "say something");

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
