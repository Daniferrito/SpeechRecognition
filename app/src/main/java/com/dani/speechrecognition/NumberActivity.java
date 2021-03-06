package com.dani.speechrecognition;


// RECONOCIMIENTO CON ANDROID BASADO EN CLASE SpeechRecognizer
// CON RESTRICCIONES POR GRAMÁTICA

import android.os.Bundle;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.util.Pair;

import com.daferto.common.Grammar;

import java.util.List;

import static com.daferto.common.Preprocessing.preprocessingSentences;

public class NumberActivity extends CarActivity {

    private String TAG = "Main3";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //SpeechRecognitionListener2 listener = new SpeechRecognitionListener2();
        //speechRecognizer.setRecognitionListener(listener);
        grammar = initGrammar();
    }

    Grammar initGrammar(){
        Grammar grammar = new Grammar(8); //8 = tamaño máximo de vocabulario
        if(lang.equals("en_US")) {
            grammar.newRule("<INI>", "<NUM>", "one", "two", "three", "four", "five", "six", "seven", "eigth");
            grammar.newRule("<NUM>", "<NUM>", "one", "two", "three", "four", "five", "six", "seven", "eigth");
            grammar.newRule("<NUM>", "<END>", "one", "two", "three", "four", "five", "six", "seven", "eigth");
        } else {
            grammar.newRule("<INI>", "<NUM>", "uno", "dos", "tres", "cuatro", "cinco", "seis", "siete", "ocho");
            grammar.newRule("<NUM>", "<NUM>", "uno", "dos", "tres", "cuatro", "cinco", "seis", "siete", "ocho");
            grammar.newRule("<NUM>", "<END>", "uno", "dos", "tres", "cuatro", "cinco", "seis", "siete", "ocho");
        }
        return grammar;
    }

    /*class SpeechRecognitionListener2 extends SpeechRecognitionListener {
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
            txtSpeechInput.setText(output);
            textToSpeech.speak(output, TextToSpeech.QUEUE_FLUSH, null);
        }
    }*/
}
