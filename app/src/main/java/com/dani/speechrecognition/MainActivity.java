package com.dani.speechrecognition;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.View;
import android.widget.ToggleButton;

/**
 * Created by Jesús Tomás on 06/02/2017.
 */

public class MainActivity extends Activity {

    ToggleButton tB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tB = (ToggleButton) findViewById(R.id.toggleButton);
        // TODO: 24/02/2017 Hacer que este botón funcione con MainActivity 2


    }

    public void android1(View v) {
        startActivity(new Intent(this, Main1Activity.class));
    }

    public void freeText(View v) {
        Intent i = new Intent(this,FreeTextActivity.class);
        if(tB.isChecked())
            i.putExtra(RecognizerIntent.EXTRA_LANGUAGE,"es_ES");
        else
            i.putExtra(RecognizerIntent.EXTRA_LANGUAGE,"en_US");
        startActivity(i);
        //startActivity(new Intent(this, FreeTextActivity.class));
    }

    public void number(View v) {
        Intent i = new Intent(this,NumberActivity.class);
        if(tB.isChecked())
            i.putExtra(RecognizerIntent.EXTRA_LANGUAGE,"es_ES");
        else
            i.putExtra(RecognizerIntent.EXTRA_LANGUAGE,"en_US");
        startActivity(i); }

    public void car     (View v) {
        Intent i = new Intent(this,CarActivity.class);
        if(tB.isChecked())
            i.putExtra(RecognizerIntent.EXTRA_LANGUAGE,"es_ES");
        else
            i.putExtra(RecognizerIntent.EXTRA_LANGUAGE,"en_US");
        startActivity(i);
        }

    public void sphinx(View v) {
        if(tB.isChecked())
            startActivity(new Intent(this, SphinxSpaActivity.class));
        else
            startActivity(new Intent(this, SphinxActivity.class));
    }
}