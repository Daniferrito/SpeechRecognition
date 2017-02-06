package com.dani.speechrecognition;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

import edu.cmu.pocketsphinx.Assets;
import edu.cmu.pocketsphinx.Hypothesis;
import edu.cmu.pocketsphinx.RecognitionListener;
import edu.cmu.pocketsphinx.SpeechRecognizer;
import edu.cmu.pocketsphinx.SpeechRecognizerSetup;


public class SphinxSpaActivity extends Activity implements
        RecognitionListener {

    private SpeechRecognizer recognizer;

    private static final String WAKEUP_SEARCH = "wakeup";

    private static final String KEYPHRASE = "entrada comando";

    private TextView tV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sphynx);
        tV = (TextView) findViewById(R.id.txtSpeechInput);

        setUp();
        Log.e("Tag", "onCreate");
    }

    @Override
    public void onStop(){
        super.onStop();
        if(recognizer!=null){
            recognizer.stop();
        }
    }


    void setUp(){
        new AsyncTask<Void, Void, Exception>() {
            @Override
            protected Exception doInBackground(Void... params) {
                try {
                    Assets assets = new Assets(SphinxSpaActivity.this);
                    File assetDir = assets.syncAssets();
                    recognizer = SpeechRecognizerSetup.defaultSetup()
                            .setAcousticModel(new File(assetDir, "es"))
                            .setDictionary(new File(assetDir, "es.dict"))
                            .setKeywordThreshold(1e-45f)
                            .setBoolean("-allphone_ci", true)
                            .getRecognizer();

                    /*recognizer = SpeechRecognizerSetup.defaultSetup()
                            .setAcousticModel(new File(assetDir, "en-us-ptm"))
                            .setDictionary(new File(assetDir, "cmudict-en-us.dict"))
                            .setKeywordThreshold(1e-45f)
                            .setBoolean("-allphone_ci", true)
                            .getRecognizer();*/

                    recognizer.addListener(SphinxSpaActivity.this);

                    recognizer.addKeyphraseSearch(WAKEUP_SEARCH, KEYPHRASE);
                } catch (IOException e) {
                    return e;
                }
                return null;
            }

            @Override
            protected void onPostExecute(Exception result) {
                if (result != null) {
                    Toast.makeText(SphinxSpaActivity.this, "Failed to init recognizer " + result, Toast.LENGTH_LONG);
                } else {
                    tV.setText("Di: "+KEYPHRASE);
                    Toast.makeText(SphinxSpaActivity.this, "Starting", Toast.LENGTH_LONG);
                    Log.e("Tag", "onPostExecute");
                    recognizer.startListening(WAKEUP_SEARCH);
                }
            }
        }.execute();

    }

    @Override
    public void onBeginningOfSpeech() {

    }

    @Override
    public void onEndOfSpeech() {

    }

    @Override
    public void onPartialResult(Hypothesis hypothesis) {

        if (hypothesis!=null&&hypothesis.getHypstr().equals(KEYPHRASE)){
            tV.setText("Encontrado");
            Log.e("Tag", "onPartialResult " + hypothesis.getHypstr());
            Toast.makeText(this,"partial",Toast.LENGTH_LONG);
        }
    }

    @Override
    public void onResult(Hypothesis hypothesis) {
        if (hypothesis!=null){
            Log.e("Tag", "onResult " + hypothesis.getHypstr());
            Toast.makeText(this,"encontrado",Toast.LENGTH_LONG);
        }
    }

    @Override
    public void onError(Exception e) {

    }

    @Override
    public void onTimeout() {

    }
}
