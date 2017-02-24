package com.dani.speechrecognition;

import android.app.Activity;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

// RECONOCIMIENTO CON ANDROID BASADO EN CLASE SpeechRecognizer
// CON RESTRICCIONES POR GRAMÁTICA

public class Main3Activity extends Main2Activity {

    private String TAG = "Main3";
    private Grammar grammar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SpeechRecognitionListener2 listener = new SpeechRecognitionListener2();
        speechRecognizer.setRecognitionListener(listener);
        grammar = initGrammar();
    }

    Grammar initGrammar(){
        Grammar gramar = new Grammar(100);
        //gramar.newRule();
        grammar.newRule("<INI>","<NUM>","uno","dos","tres","cuatro","cinco","seis","siete","ocho");
        grammar.newRule("<NUM>","<NUM>","uno","dos","tres","cuatro","cinco","seis","siete","ocho");
        grammar.newRule("<NUM>","<END>","uno","dos","tres","cuatro","cinco","seis","siete","ocho");

        return gramar;
    }

    class SpeechRecognitionListener2 extends SpeechRecognitionListener {
        @Override
        public void onResults(Bundle results) {
            Log.d(TAG, "onResults");
            ArrayList<String> outputs = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);

            txtSpeechInput.setText(outputs.get(0));
            textToSpeech.speak(outputs.get(0), TextToSpeech.QUEUE_FLUSH, null);
            for (String s: outputs){
                Log.e(TAG, s);
            }
        }
    }

}
