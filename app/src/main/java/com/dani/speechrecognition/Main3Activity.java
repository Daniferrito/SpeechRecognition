package com.dani.speechrecognition;


// RECONOCIMIENTO CON ANDROID BASADO EN CLASE SpeechRecognizer
// CON RESTRICCIONES POR GRAMÁTICA

public class Main3Activity extends Main2Activity {
/*
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
        Grammar grammar = new Grammar(8); //8 = tamaño máximo de vocabulario
        grammar.newRule("<INI>","<NUM>","uno","dos","tres","cuatro","cinco","seis","siete","ocho");
        grammar.newRule("<NUM>","<NUM>","uno","dos","tres","cuatro","cinco","seis","siete","ocho");
        grammar.newRule("<NUM>","<END>","uno","dos","tres","cuatro","cinco","seis","siete","ocho");
        return grammar;
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
            txtSpeechInput.setText(output);
            textToSpeech.speak(output, TextToSpeech.QUEUE_FLUSH, null);
        }
    }
*/
}
