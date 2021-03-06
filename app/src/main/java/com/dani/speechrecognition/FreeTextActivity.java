package com.dani.speechrecognition;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;

// RECONOCIMIENTO CON ANDROID BASADO EN CLASE SpeechRecognizer

public class FreeTextActivity extends Activity {

    protected static final String TAG = "Main2";
    protected SpeechRecognizer speechRecognizer;
    protected TextToSpeech textToSpeech;
    protected Intent speechRecognizerIntent;
    protected ImageButton btnSpeak;
    protected TextView txtSpeechInput;
    protected String lang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main1);
        //((AudioManager)getSystemService(AUDIO_SERVICE)).setStreamVolume(AudioManager.STREAM_MUSIC,AudioManager.ADJUST_MUTE,0);
        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(this);
        speechRecognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        speechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        speechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE,
                this.getPackageName());
        //speechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_PREFER_OFFLINE,true);
        speechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS,100);
        lang = getIntent().getExtras().getString(RecognizerIntent.EXTRA_LANGUAGE,"es_ES");
        speechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE,lang);

        SpeechRecognitionListener listener = new SpeechRecognitionListener();
        speechRecognizer.setRecognitionListener(listener);

        btnSpeak = (ImageButton) findViewById(R.id.btnSpeak);
        txtSpeechInput = (TextView) findViewById(R.id.txtSpeechInput);
        btnSpeak.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                txtSpeechInput.setText("...");
                speechRecognizer.startListening(speechRecognizerIntent);
            }
        });
        textToSpeech = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                textToSpeech.setLanguage(new Locale(lang));
            }
        });

    }


    protected class SpeechRecognitionListener implements RecognitionListener
    {

        @Override
        public void onBeginningOfSpeech()
        {
            Log.d(TAG, "onBeginingOfSpeech");
        }

        @Override
        public void onBufferReceived(byte[] buffer) {}

        @Override
        public void onEndOfSpeech()
        {
            Log.d(TAG, "onEndOfSpeech");
        }

        @Override
        public void onError(int error) {

            //Log.e(TAG, "error = " + error);
        }

        @Override
        public void onEvent(int eventType, Bundle params)
        {
            Log.d(TAG, "onEvent");
        }

        @Override
        public void onPartialResults(Bundle partialResults) {
            Log.d(TAG, "onPartialResults");
            ArrayList<String> matches = partialResults.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
            txtSpeechInput.setText(matches.get(0));
        }

        @Override
        public void onReadyForSpeech(Bundle bundle) {
            Log.d(TAG, "onReadyForSpeech"); //$NON-NLS-1$
        }

        @Override
        public void onResults(Bundle results) {
            Log.d(TAG, "onResults"); //$NON-NLS-1$
            ArrayList<String> outputs = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
            txtSpeechInput.setText(outputs.get(0));
            textToSpeech.speak(outputs.get(0),TextToSpeech.QUEUE_FLUSH, null);
            for (String s: outputs){
                Log.e(TAG, s);
            }
        }

        @Override
        public void onRmsChanged(float rmsdB)
        {}
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case 1234: {
                if (resultCode == RESULT_OK && null != data) {

                    ArrayList<String> result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    txtSpeechInput.setText(result.get(0));
                }
                break;
            }

        }
    }
}
