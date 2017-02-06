package com.dani.speechrecognition;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

/**
 * Created by Jesús Tomás on 06/02/2017.
 */

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void android1(View v) {
        startActivity(new Intent(this, Main1Activity.class));
    }

    public void android2(View v) {
        startActivity(new Intent(this, Main2Activity.class));
    }

    public void android3(View v) {
        startActivity(new Intent(this, Main3Activity.class));
    }

    public void sphinx(View v) {
        startActivity(new Intent(this, SphinxActivity.class));
    }
}