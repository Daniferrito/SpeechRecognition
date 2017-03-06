package com.dani.speechrecognition;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.util.Log;
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

    public void android2(View v) {
        Intent i = new Intent(this,Main2Activity.class);
        if(tB.isChecked())
            i.putExtra(RecognizerIntent.EXTRA_LANGUAGE,"es-ES");
        else
            i.putExtra(RecognizerIntent.EXTRA_LANGUAGE,"en-US");
        startActivity(i);
        //startActivity(new Intent(this, Main2Activity.class));
    }

    public void android3(View v) { startActivity(new Intent(this, Main3Activity.class)); }

    public void car     (View v) { startActivity(new Intent(this, CarActivity.class)); }

    public void sphinx(View v) {
        if(tB.isChecked())
            startActivity(new Intent(this, SphinxSpaActivity.class));
        else
            startActivity(new Intent(this, SphinxActivity.class));
    }
}