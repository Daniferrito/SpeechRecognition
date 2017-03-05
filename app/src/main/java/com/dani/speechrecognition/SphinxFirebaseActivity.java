package com.dani.speechrecognition;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import edu.cmu.pocketsphinx.Assets;
import edu.cmu.pocketsphinx.Hypothesis;
import edu.cmu.pocketsphinx.RecognitionListener;
import edu.cmu.pocketsphinx.SpeechRecognizer;
import edu.cmu.pocketsphinx.SpeechRecognizerSetup;

public class SphinxFirebaseActivity extends Activity implements
        RecognitionListener {

    private SpeechRecognizer recognizer;

    private static final String WAKEUP_SEARCH = "wakeup";
    private static final String MAIN_SEARCH = "mainSearch";

    private static final String KEYPHRASE = "input command";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_sphinx_firebase);

        setUp();
        //MediaPlayer mp = MediaPlayer.create(getApplicationContext(), R.raw.fob);
        //mp.start();
    }

    void setUp(){
        new AsyncTask<Void, Void, Exception>() {
            @Override
            protected Exception doInBackground(Void... params) {
                try {
                    Assets assets = new Assets(SphinxFirebaseActivity.this);
                    File assetDir = assets.syncAssets();
                    /*recognizer = SpeechRecognizerSetup.defaultSetup()
                            .setAcousticModel(new File(assetDir, "es"))
                            .setDictionary(new File(assetDir, "es.dict"))
                            .setKeywordThreshold(1e-45f)
                            .setBoolean("-allphone_ci", true)
                            .getRecognizer();*/

                    recognizer = SpeechRecognizerSetup.defaultSetup()
                            .setAcousticModel(new File(assetDir, "en-us-ptm"))
                            .setDictionary(new File(assetDir, "cmudict-en-us.dict"))
                            .setKeywordThreshold(1e-45f)
                            .setBoolean("-allphone_ci", true)
                            .getRecognizer();

                    recognizer.addListener(SphinxFirebaseActivity.this);

                    recognizer.addKeyphraseSearch(WAKEUP_SEARCH, KEYPHRASE);

                    recognizer.addGrammarSearch(MAIN_SEARCH,new File(assetDir,"main.gram"));

                } catch (IOException e) {
                    return e;
                }
                return null;
            }

            @Override
            protected void onPostExecute(Exception result) {
                if (result != null) {
                    //tV.setText("Failed");
                    //Toast.makeText(SphinxActivity.this, "Failed to init recognizer " + result, Toast.LENGTH_LONG).show();
                    Log.e("Tag", "Failed to init recognizer " + result);
                } else {
                    //tV.setText("Ready");
                    Log.e("Tag", "onPostExecute");
                    recognizer.startListening(MAIN_SEARCH);
                }
            }
        }.execute();

    }

    @Override
    public void onBeginningOfSpeech() {
        Log.e("Tag", "onBeginningOfSpeech");
    }

    @Override
    public void onEndOfSpeech() {
        Log.e("Tag", "onEndOfSpeech");
        recognizer.stop();
        recognizer.startListening(MAIN_SEARCH);
    }

    @Override
    public void onPartialResult(Hypothesis hypothesis) {

        if (hypothesis!=null){
            Log.w("Tag", "onPartialResult " + hypothesis.getHypstr());
            //Log.e("Tag", "onPartialResult " + hypothesis.getHypstr());
            //tV.setText(hypothesis.getHypstr());
            //Toast.makeText(this,"partial",Toast.LENGTH_LONG);
        }
    }

    @Override
    public void onResult(Hypothesis hypothesis) {
        if (hypothesis!=null){
            Log.e("Tag", "onResult " + hypothesis.getHypstr());
            Log.e("Tag", ""+hypothesis.getBestScore());
            String[] strArr = hypothesis.getHypstr().split(" ");
            if(strArr[strArr.length-1].equals("fixed")){
                String[] nStrArr = Arrays.copyOfRange(strArr,0,strArr.length-1);
                String s = TextUtils.join(" ",nStrArr);
                FirebaseConection.getInstance().changeStatus(s,1);
            }else if (strArr[strArr.length-1].equals("incomplete")){
                String[] nStrArr = Arrays.copyOfRange(strArr,0,strArr.length-1);
                String s = TextUtils.join(" ",nStrArr);
                FirebaseConection.getInstance().changeStatus(s,2);
            }else if(strArr[strArr.length-2].equals("add")){
                String[] nStrArr = Arrays.copyOfRange(strArr,0,strArr.length-2);
                String s = TextUtils.join(" ",nStrArr);
                FirebaseConection.getInstance().addError(s,strArr[strArr.length-1], hypothesis.getBestScore());
            }

        }
    }

    @Override
    public void onError(Exception e) {
        Log.e("Tag", "onError " + e.toString());
    }

    @Override
    public void onTimeout() {
        Log.e("Tag", "onTimeout");
    }
}
