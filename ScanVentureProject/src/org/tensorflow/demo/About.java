package org.tensorflow.demo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.app.Activity;

public class About extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
    }
    @Override
    public void onBackPressed(){
        this.finish();
    }
}
