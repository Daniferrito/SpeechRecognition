package com.daferto.speechrecognition;

import android.os.Bundle;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.util.Pair;

import com.daferto.common.CarGrammar;
import com.daferto.common.Grammar;

import java.util.List;

import static com.daferto.common.Preprocessing.preprocessingSentences;


// RECONOCIMIENTO CON ANDROID BASADO EN CLASE SpeechRecognizer
// CON RESTRICCIONES POR GRAMÁTICA

public class CarActivity extends Main2Activity {

    private String TAG = "AutoActivity";
    private Grammar grammar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SpeechRecognitionListener2 listener = new SpeechRecognitionListener2();
        speechRecognizer.setRecognitionListener(listener);
        grammar = new CarGrammar();
    }

    class SpeechRecognitionListener2 extends SpeechRecognitionListener {
        @Override
        public void onResults(Bundle results) {
            Log.d(TAG, "onResults");
            List<String> outputs = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
            outputs = preprocessingSentences(outputs);
            //String output = grammar.bestSecuence(outputs);
            Pair<String,Integer> p = grammar.bestSecuenceAndScore(outputs);
            String output;
            if (p.second<1000) {
                output = p.first;
            } else {
                output = "Frase incorrecta";
            }
            //txtSpeechInput.setText(output);
            textToSpeech.speak(output, TextToSpeech.QUEUE_FLUSH, null);
        }
    }

}
