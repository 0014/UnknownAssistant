package com.a0014.unknownassistant.Activities;

import java.util.ArrayList;
import java.util.Locale;

import android.Manifest;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.a0014.unknownassistant.Controllers.ControlCommand;
import com.a0014.unknownassistant.Models.RemoteControlModel;
import com.a0014.unknownassistant.R;

public class CommandActivity extends Activity {

    private TextView txtSpeechInput;
    private ImageButton btnRetry, btnSpeak;

    private final int REQ_CODE_SPEECH_INPUT = 100;
    private static final int MY_PERMISSIONS_REQUEST_IR_TRANSMIT = 200;

    ControlCommand controller;
    RemoteControlModel rcm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_command);

        txtSpeechInput = (TextView) findViewById(R.id.txtSpeechInput);
        btnSpeak = (ImageButton) findViewById(R.id.btnSpeak);
        btnRetry = (ImageButton) findViewById(R.id.btnRetry);

        // instantiate the controller that will evaluate the commands
        controller = new ControlCommand(getApplicationContext());

        // request permissions on load
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.TRANSMIT_IR}, MY_PERMISSIONS_REQUEST_IR_TRANSMIT);
        // set retry button to invisible on default
        btnRetry.setVisibility(View.INVISIBLE);

        btnSpeak.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                promptSpeechInput();
            }
        });

        btnRetry.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // pass the command to the controller to apply the command
                controller.ApplyCommand(controller.getCommand(), getApplicationContext());
            }
        });
    }

    /**
     * Showing google speech input dialog
     * */
    private void promptSpeechInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                getString(R.string.speech_prompt));
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(getApplicationContext(),
                    getString(R.string.speech_not_supported),
                    Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Receiving speech input
     * */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {

                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

                    String myCommand = result.get(0).toLowerCase();
                    // show the command on the screen
                    txtSpeechInput.setText(myCommand);
                    // pass the command to the controller to apply the command
                    if(controller.ApplyCommand(myCommand, getApplicationContext())){
                        btnRetry.setVisibility(View.VISIBLE);
                    }else{
                        btnRetry.setVisibility(View.INVISIBLE);
                    }
                }
                break;
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_IR_TRANSMIT: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    txtSpeechInput.setText("Permission Granted !");
                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    txtSpeechInput.setText("Permission NOT Granted !");
                }
                return;
            }
        }
    }

}